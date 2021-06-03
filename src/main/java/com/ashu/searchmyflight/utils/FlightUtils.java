package com.ashu.searchmyflight.utils;

import com.ashu.searchmyflight.entities.Flight;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class FlightUtils {
    private static final int DAY_IN_MINUTES = 2400;

    public static int getFlightDuration(Flight f){
        return getTimeDifferenceInMinutes(f.getArrivalTime(),f.getDepartureTime());
    }

    public static int getTimeDifferenceInMinutes(String t1, String t2){
        String s1 = formatTime(t1);
        String s2 = formatTime(t2);
        LocalTime time1 = LocalTime.parse(s1);
        LocalTime time2 = LocalTime.parse(s2);
        Long minutesBetween= ChronoUnit.MINUTES.between(time2,time1);
        if(minutesBetween.intValue() < 0)
            return minutesBetween.intValue()+DAY_IN_MINUTES;
        return minutesBetween.intValue();
    }

    public static String formatTime(String s1){
        String s2=s1;
        if(s1.length() < 4){
            int x = 4-s1.length();
            for(int i=0;i<x;i++){
                s2="0"+s2;
            }
        }
        return s2.substring(0,2)+":"+s2.substring(2,4);
    }
}
