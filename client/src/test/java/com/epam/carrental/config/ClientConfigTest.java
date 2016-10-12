package com.epam.carrental.config;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(classes = HttpInvokerConfig.class)
public class ClientConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ApplicationContext context;

    @Test
    public void notNullApplicationContextTest() throws Exception {
        Assert.assertNotNull(context);
    }

}