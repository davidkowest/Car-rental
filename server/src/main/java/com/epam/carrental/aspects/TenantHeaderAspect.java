package com.epam.carrental.aspects;


import com.epam.carrental.utils.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TenantHeaderAspect {

    @Autowired
    private Tenant tenant;

    @Before("@annotation(RequiresTenant)")
    public void afterThrowingInExporter() {
        if(null == tenant.id.get()){
            throw new IllegalArgumentException("Tenant not specified for this transaction");
        }
    }
}

