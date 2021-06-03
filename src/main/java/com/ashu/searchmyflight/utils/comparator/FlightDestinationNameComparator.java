package com.ashu.searchmyflight.utils.comparator;

import com.ashu.searchmyflight.entities.Flight;

import java.util.Comparator;

public class FlightDestinationNameComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight f1, Flight f2) {
        return f1.getDestination().compareToIgnoreCase(f2.getDestination());
    }
}
