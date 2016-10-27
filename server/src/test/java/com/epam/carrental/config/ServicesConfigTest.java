package com.epam.carrental.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@ContextConfiguration(classes = ServicesConfig.class)
public class ServicesConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ApplicationContext context;

    @Test
    public void notNullApplicationContextTest() throws Exception {
        Assert.assertNotNull(context);
    }

}

