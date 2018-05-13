package com.n26.project.services.impl;


import com.n26.project.domains.Statistic;
import com.n26.project.domains.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
@RunWith(SpringRunner.class)
public class ComplexServiceImplTest {


    @InjectMocks
    private ComplexServiceImpl complexService;

    @Mock
    private ConcurrentHashMap<Long, List<Transaction>> transactions;

    @Mock
    private Transaction transaction;


    @Test
    public void validateForSaveTransaction() {
        doReturn(null).when(transactions).get(anyLong());
        doReturn(mockList()).when(transactions).putIfAbsent(anyLong(), anyList());
        doReturn(10L).when(transaction).toMin();
        complexService = new ComplexServiceImpl(transactions);
        complexService.save(transaction);
    }

    @Test
    public void getStatistics() {
        doReturn(mockList()).when(transactions).getOrDefault(anyLong(),anyList());
        complexService = new ComplexServiceImpl(transactions);
        Statistic statistics = complexService.getStatistics();
        Assert.assertNotNull(statistics);
        Assert.assertTrue(statistics.getSum() == 10D);
    }

    private List<Transaction> mockList() {
        List<Transaction> list = new ArrayList<>();
        list.add(new Transaction(10D, 60L));
        return list;
    }

}
