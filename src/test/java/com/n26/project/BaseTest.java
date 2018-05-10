package com.n26.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public abstract class BaseTest {

    @Autowired
    private ApplicationContext context;

}