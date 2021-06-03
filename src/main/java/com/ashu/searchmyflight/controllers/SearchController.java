package com.ashu.searchmyflight.controllers;

import com.ashu.searchmyflight.entities.Flight;
import com.ashu.searchmyflight.entities.SearchResponse;
import com.ashu.searchmyflight.services.SearchFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    @Autowired
    SearchFlightService searchFlightService;

    @GetMapping(value = "/flights")
    public ResponseEntity<List<SearchResponse>> searchFlight
            (@RequestParam String source, @RequestParam String dest, @RequestParam(required = false) Integer size){
        return new ResponseEntity(searchFlightService.searchFlight(source,dest,size), HttpStatus.OK);
    }

}
