package com.n26.project.services;

import com.n26.project.domains.Transaction;
import com.n26.project.domains.Statistic;


public interface ComplexService {

    Statistic getStatistics();

    void save(Transaction transaction);
}
