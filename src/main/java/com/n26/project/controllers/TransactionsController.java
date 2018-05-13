package com.n26.project.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.project.domains.Transaction;
import com.n26.project.services.ComplexService;
import com.n26.project.util.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/transactions", consumes = "application/json")
public class TransactionsController {

    @Autowired
    private ComplexService complexService;

    @Autowired
    private TransactionValidator transactionValidator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTransaction(@RequestBody Transaction transaction) throws IOException {
        if (transactionValidator.isValid(transaction.getTimestamp()))
        complexService.save(transaction);
    }
}
