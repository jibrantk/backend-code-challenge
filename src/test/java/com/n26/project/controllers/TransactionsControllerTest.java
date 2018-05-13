package com.n26.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.project.domains.Transaction;
import com.n26.project.services.ComplexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionsController.class)
public class TransactionsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ComplexService complexService;


    @Test
    public void shouldReturn200Response() throws Exception {
        Transaction transaction = new Transaction(10D, System.currentTimeMillis());
        doNothing().when(complexService).save(transaction);

        mvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isCreated());

    }

    @Test
    public void shouldReturn204Response() throws Exception {
        Transaction transaction = new Transaction(10D, 0L);
        doNothing().when(complexService).save(transaction);

        mvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(status().isNoContent());

    }
}