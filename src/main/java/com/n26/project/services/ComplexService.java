package com.n26.project.services;

import com.n26.project.domains.Statistic;
import com.n26.project.domains.Transaction;


public interface ComplexService {

    Statistic getStatistics();

    void save(Transaction transaction);

}
