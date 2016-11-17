package com.epam.carrental;

import com.epam.carrental.config.HttpInvokerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;

public class ClientMain {

    public static void main(String[] args) {
       try(AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext()){
           context.register(HttpInvokerConfig.class);
           context.getEnvironment().getPropertySources().addFirst(new SimpleCommandLinePropertySource(args));
           context.refresh();
           context.start();
       }
    }
}
