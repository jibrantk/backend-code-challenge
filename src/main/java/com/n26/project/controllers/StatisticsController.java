package com.n26.project.controllers;


import com.n26.project.domains.Statistic;
import com.n26.project.services.ComplexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/statistics",produces = "application/json")
public class StatisticsController {

    @Autowired
    private ComplexService complexService;

    @GetMapping
    public Statistic getStatistics() {
        return complexService.getStatistics();
    }
}
