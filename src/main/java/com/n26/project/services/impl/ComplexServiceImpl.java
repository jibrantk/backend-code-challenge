package com.n26.project.services.impl;

import com.n26.project.domains.Statistic;
import com.n26.project.domains.Transaction;
import com.n26.project.services.ComplexService;
import com.n26.project.util.Constants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ComplexServiceImpl implements ComplexService {

    private ConcurrentHashMap<Long, List<Transaction>> transactions;

    public ComplexServiceImpl() {
        this(new ConcurrentHashMap());
    }

    public ComplexServiceImpl(ConcurrentHashMap<Long, List<Transaction>> concurrentHashMap) {
        transactions = concurrentHashMap;
     }

    @Override
    public void save(Transaction transaction) {
        Long minutes = transaction.toMin();
        List<Transaction> list = this.transactions.get(minutes);
        list = list == null ? new ArrayList<>() : list;
        list.add(transaction);
        transactions.putIfAbsent(minutes, list);
    }

    @Override
    public Statistic getStatistics() {
        List<Transaction> orDefault = transactions.getOrDefault(Constants.currentTimeInMins(), new ArrayList<>());
        Iterator<Transaction> iterator = orDefault.iterator();
        Statistic statistics = new Statistic();
        while (iterator.hasNext()) {
            statistics.setAmount(iterator.next().getAmount());
        }
        return statistics;
    }
}