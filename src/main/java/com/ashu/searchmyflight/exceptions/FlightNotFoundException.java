package com.ashu.searchmyflight.exceptions;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException() {
        super();
    }
    public FlightNotFoundException(Exception ex) {
        super(ex);
    }
}
