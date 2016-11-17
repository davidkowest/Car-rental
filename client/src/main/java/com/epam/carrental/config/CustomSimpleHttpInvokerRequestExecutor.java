package com.epam.carrental.config;


import com.epam.carrental.settings.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;

@Component
public class CustomSimpleHttpInvokerRequestExecutor extends SimpleHttpInvokerRequestExecutor {

    @Autowired
    Environment environment;

    @Override
    protected void prepareConnection(HttpURLConnection connection, int contentLength) throws IOException {
        if(environment.getProperty("nonOptionArgs")!=null){
            connection.addRequestProperty(Headers.TENANT_ID_HEADER, environment.getProperty("nonOptionArgs").split(",")[0]);
        }
        super.prepareConnection(connection, contentLength);
    }
}