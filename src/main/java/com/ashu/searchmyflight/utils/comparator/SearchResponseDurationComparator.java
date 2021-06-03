package com.ashu.searchmyflight.utils.comparator;

import com.ashu.searchmyflight.entities.Flight;
import com.ashu.searchmyflight.entities.SearchResponse;

import java.util.Comparator;

public class SearchResponseDurationComparator implements Comparator<SearchResponse> {

    @Override
    public int compare(SearchResponse s1, SearchResponse s2) {
        return s1.getFlightDuration()-s2.getFlightDuration();
    }
}
