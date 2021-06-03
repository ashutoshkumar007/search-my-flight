package com.ashu.searchmyflight.services;

import com.ashu.searchmyflight.entities.CsvLoader;
import com.ashu.searchmyflight.entities.Flight;
import com.ashu.searchmyflight.entities.SearchResponse;
import com.ashu.searchmyflight.exceptions.FlightNotFoundException;
import com.ashu.searchmyflight.utils.comparator.FlightDestinationNameComparator;
import com.ashu.searchmyflight.utils.comparator.FlightSourceNameComparator;
import com.ashu.searchmyflight.utils.comparator.SearchResponseDurationComparator;
import lombok.Data;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ashu.searchmyflight.utils.FlightUtils.*;

@Service
@Data
public class SearchFlightService {
    private final static String DELIMITER = "_";
    private final static int MINIMUM_TIME_DIFFERENCE = 120;
    private final List<Flight> flightList;

    @Autowired
    SearchFlightService() throws Exception {
        flightList = new CsvLoader().loadCsvFile();
    }

    public List<SearchResponse> searchFlight(String source, String dest, Integer length){
        source = source.toUpperCase();
        dest = dest.toUpperCase();
        List<SearchResponse> result = new ArrayList<>();
        result.addAll(sortFlightWithDuration(findDirectFlights(source,dest)));
        result.addAll(sortFlightWithDuration(findOneStopFlights(source,dest)));
        if(result.size() < 1){
            throw new FlightNotFoundException();
        }
        if(length != null && result.size() > length) {
            return result.stream().limit(length).collect(Collectors.toList());
        }else{
            return result;
        }
    }

    public List<SearchResponse> findDirectFlights(String source, String dest){
        List<Flight> list =  getDirectFlightFromSourceToDestination(source,dest);
        List<SearchResponse> response = new ArrayList<>();
        for(Flight f : list){
            response.add(convertFlightToSearchResponse(f));
        }
        return response;
    }

    public List<SearchResponse> findOneStopFlights(String source, String dest){
        List<Flight> sourceFlights = getAllFlightFromSource(source,dest);
        List<String> sourceFlightDest = getAllDestinationFromSource(sourceFlights);
        List<Flight> destFlights = getAllFlightToDestination(sourceFlightDest,dest);
        return mergePossibleFlight(sourceFlights,destFlights);
    }


    public List<Flight> getDirectFlightFromSourceToDestination(String source, String dest){
        return flightList.stream()
                .filter(f -> f.getSource().equalsIgnoreCase(source) && f.getDestination().equalsIgnoreCase(dest))
                .collect(Collectors.toList());
    }


    public List<Flight>  getAllFlightFromSource(String source, String dest){
        return flightList.stream()
                .filter(f -> f.getSource().equalsIgnoreCase(source) && !f.getDestination().equalsIgnoreCase(dest))
                .collect(Collectors.toList());
    }

    public List<String> getAllDestinationFromSource(List<Flight> sourceFlights){
        return sourceFlights.stream()
                .map(f -> f.getDestination())
                .collect(Collectors.toList());
    }

    public List<Flight> getAllFlightToDestination(List<String> sourceFlightDest,String dest){
        return flightList.stream()
                .filter(f-> f.getDestination().equals(dest) && sourceFlightDest.contains(f.getSource()))
                .collect(Collectors.toList());
    }



    public List<SearchResponse> mergePossibleFlight(List<Flight> sourceFlights, List<Flight> destFlights){
        Flight first,second;
        Map<String, SearchResponse> map = new HashMap<>();

        sourceFlights.sort(new FlightDestinationNameComparator());
        destFlights.sort(new FlightSourceNameComparator());

        for(int i=0; i < sourceFlights.size(); i++){
            first = sourceFlights.get(i);
            for(int j=0; j < destFlights.size();j++){
                second = destFlights.get(j);
                if(connectingFlightPossible(first,second)){
                    SearchResponse validFlight = convertFlightToSearchResponse(first,second);
                    if(map.containsKey(validFlight.getFlightName())){
                        SearchResponse value = map.get(validFlight.getFlightName());
                        if(value.getFlightDuration() > validFlight.getFlightDuration()){
                            map.put(validFlight.getFlightName() , validFlight);
                        }
                    }else{
                        map.put(validFlight.getFlightName(),validFlight);
                    }
                }
            }
        }
        return  new ArrayList<>(map.values());
    }

    public boolean connectingFlightPossible(Flight first, Flight second){
        if(first.getDestination().equals(second.getSource())){
            if(getTimeDifferenceInMinutes(second.getDepartureTime(),first.getArrivalTime()) > MINIMUM_TIME_DIFFERENCE){
                return true;
            }
        }
        return false;
    }

    public SearchResponse convertFlightToSearchResponse(Flight first){
        SearchResponse response = new SearchResponse();
        response.setFlightName(first.getSource()+ DELIMITER +first.getDestination());
        response.setFlightNum(first.getFlightNum());
        response.setFlightDuration(getFlightDuration(first));
        return response;
    }

    public SearchResponse convertFlightToSearchResponse(Flight first, Flight second){
        SearchResponse response = new SearchResponse();
        response.setFlightName(first.getSource()+ DELIMITER +first.getDestination()+ DELIMITER +second.getDestination());
        response.setFlightNum(first.getFlightNum()+ DELIMITER +second.getFlightNum());
        int gap = getTimeDifferenceInMinutes(second.getDepartureTime(),first.getArrivalTime());
        response.setFlightDuration(getFlightDuration(first)+getFlightDuration(second)+gap);
        return response;
    }


    public List<SearchResponse> sortFlightWithDuration(List<SearchResponse> flights){
        flights.sort(new SearchResponseDurationComparator());
        return flights;
    }


}
