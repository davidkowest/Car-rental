package com.epam.carrental.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@ContextConfiguration(classes = ServerConfig.class)
public class ServerConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    ApplicationContext context;

    @Test
    public void notNullAppliactionContextTest() throws Exception {
        Assert.assertNotNull(context);
    }

}

