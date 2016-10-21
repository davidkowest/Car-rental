package com.epam.carrental;

import com.epam.carrental.config.HttpInvokerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientMain {

    public static void main(String[] args) {
       try(AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(HttpInvokerConfig.class)){};
    }
}
