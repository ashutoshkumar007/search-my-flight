package com.ashu.searchmyflight.entities;

import com.ashu.searchmyflight.services.SearchFlightService;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvLoader {

    @Autowired
    SearchFlightService searchFlightService;

    public  List<Flight> loadCsvFile() throws Exception {
        Path path = Paths.get(
                ClassLoader.getSystemResource("csv/ivtest-sched.csv").toURI());

        List<Flight> flightList;
        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
        ms.setType(Flight.class);

        Reader reader = Files.newBufferedReader(path);
        CsvToBean cb = new CsvToBeanBuilder(reader)
                .withType(Flight.class)
                .withMappingStrategy(ms)
                .build();

        flightList = cb.parse();
        reader.close();
        return flightList;
    }
}
