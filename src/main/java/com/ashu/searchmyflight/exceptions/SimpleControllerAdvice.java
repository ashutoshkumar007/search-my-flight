package com.ashu.searchmyflight.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class SimpleControllerAdvice {
    public static String FLIGHT_NOT_FOUND_MESSAGE = "No flight found for this route";

    @ExceptionHandler(FlightNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmployeeNotFound(FlightNotFoundException exception, HttpServletRequest request){
        log.warn("Cab not found for request {} ",request.getRequestURI(),exception);
        return FLIGHT_NOT_FOUND_MESSAGE;
    }
}
