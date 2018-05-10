package com.n26.project.controllers;


import com.n26.project.domains.Statistic;
import com.n26.project.services.ComplexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsController.class)
public class StatisticsControllerTest {

    @Autowired

    private MockMvc mvc;

    @MockBean
    private ComplexService complexService;


    @Test
    public void shouldReturnSampleStatistics() throws Exception {
        Statistic statistic = new Statistic(223, BigDecimal.valueOf(50.32), 40, 10);

        when(complexService.getStatistics()).thenReturn(statistic);

        mvc.perform(get("/api/statistics").accept("application/json"))
                .andExpect(content().contentTypeCompatibleWith("application/json"));

    }

}
