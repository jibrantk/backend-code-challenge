package com.n26.project.controllers;


import com.n26.project.domains.Transaction;
import com.n26.project.services.ComplexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionsController.class)
public class TransactionsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ComplexService complexService;

    @Test
    public void validateValidRequest() throws Exception {
        long currenet = System.currentTimeMillis() + 100;
        mvc.perform(post("/api/transactions")
                .contentType("application/json")
                .content("{\"amount\": 1000000254.3,\"timestamp\": "+currenet+"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));

        verify(complexService).save(new Transaction(BigDecimal.valueOf(1000000254.3),currenet));
    }

    @Test
    public void shouldValidateRequest() throws Exception {
        mvc.perform(post("/api/transactions")
                .contentType("application/json")
                .content("{\"timestamp\": 0}"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

    }
}
