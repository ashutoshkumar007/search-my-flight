package com.ashu.searchmyflight.controllers;

import com.ashu.searchmyflight.entities.CsvLoader;
import com.ashu.searchmyflight.entities.Flight;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/file")
public class FileController {

    @GetMapping("/")
    public List<Flight> loadCsvFile() throws Exception {
        return new CsvLoader().loadCsvFile();
    }
}
