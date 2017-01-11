package com.epam.carrental.config;

import com.epam.carrental.settings.Headers;
import com.epam.carrental.tenant.Tenant;
import com.sun.net.httpserver.HttpExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerServiceExporter;

import java.io.IOException;
import java.util.List;

public class CustomSimpleHttpInvokerServiceExporter extends SimpleHttpInvokerServiceExporter {

    @Autowired
    private Tenant tenant;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        List<String> headers = exchange.getRequestHeaders().get(Headers.TENANT_ID_HEADER);
        if (headers != null && !headers.isEmpty()) {
            tenant.id.set(headers.get(0));
        }
        try {
            super.handle(exchange);
        } finally {
            tenant.id.remove();
        }
    }
}

