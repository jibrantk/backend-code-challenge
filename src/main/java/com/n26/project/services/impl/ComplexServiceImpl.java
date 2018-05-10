package com.n26.project.services.impl;

import com.n26.project.domains.Transaction;
import com.n26.project.domains.Statistic;
import com.n26.project.services.ComplexService;
import com.n26.project.dao.InMemoryDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.n26.project.domains.Statistic.INIT;

@Service
public class ComplexServiceImpl implements ComplexService {

    private InMemoryDatabase inMemoryDatabase;

    @Autowired
    public ComplexServiceImpl() {
        this(InMemoryDatabase.lastMinute(() -> INIT));
    }

    ComplexServiceImpl(InMemoryDatabase inMemoryDatabase){
        this.inMemoryDatabase = inMemoryDatabase;
    }

    @Override
    public void save(Transaction transaction) {
        inMemoryDatabase.update(transaction.getTimestamp(), statistic -> statistic.assign(transaction.getAmount()));
    }

    @Override
    public Statistic getStatistics() {
        return inMemoryDatabase.process(Statistic::merge);
    }
}
