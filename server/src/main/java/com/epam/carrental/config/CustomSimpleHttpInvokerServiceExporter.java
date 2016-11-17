package com.epam.carrental.config;

import com.epam.carrental.settings.Headers;
import com.epam.carrental.utils.Tenant;
import com.sun.net.httpserver.HttpExchange;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerServiceExporter;
import org.springframework.remoting.support.RemoteInvocation;

import java.io.IOException;

public class CustomSimpleHttpInvokerServiceExporter extends SimpleHttpInvokerServiceExporter {

    @Override
    protected RemoteInvocation readRemoteInvocation(HttpExchange exchange) throws IOException, ClassNotFoundException {
        try {
            Tenant.setId(exchange.getRequestHeaders().get(Headers.TENANT_ID_HEADER).get(0));
        } catch (NullPointerException ex) {
            Tenant.setId(null);
        } finally {
            return super.readRemoteInvocation(exchange);
        }
    }
}

