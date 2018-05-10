package com.n26.project.controllers;


import com.n26.project.domains.Transaction;
import com.n26.project.services.ComplexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/transactions",consumes = "application/json")
public class TransactionsController {

    @Autowired
    private ComplexService complexService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTransaction(@RequestBody Transaction transaction) {
        complexService.save(transaction);
    }
}
