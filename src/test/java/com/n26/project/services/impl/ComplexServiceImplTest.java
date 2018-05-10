package com.n26.project.services.impl;

import com.n26.project.BaseTest;
import com.n26.project.dao.InMemoryDatabase;
import com.n26.project.domains.Statistic;
import com.n26.project.domains.Transaction;
import net.minidev.json.writer.UpdaterMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class ComplexServiceImplTest extends BaseTest {

    @Autowired
    @InjectMocks
    private ComplexServiceImpl complexService;

    @Mock
    private InMemoryDatabase database;

    @Mock
    private Transaction transaction = new Transaction(BigDecimal.TEN,System.currentTimeMillis()+120);


    @Test
    public void validateForSaveTransaction(){

        doNothing().when(database).update(anyLong(),any());

        verify(database);
    }

}
