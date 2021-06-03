package com.ashu.searchmyflight.entities;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Flight {

    @CsvBindByPosition(position = 0)
    private String flightNum;

    @CsvBindByPosition(position = 1)
    private String source;

    @CsvBindByPosition(position = 2)
    private String destination;

    @CsvBindByPosition(position = 3)
    private String departureTime;

    @CsvBindByPosition(position = 4)
    private String arrivalTime;

}
