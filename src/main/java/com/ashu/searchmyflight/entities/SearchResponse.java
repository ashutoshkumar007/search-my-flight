package com.ashu.searchmyflight.entities;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SearchResponse {
    String flightName;
    String flightNum;
    int flightDuration;
}
