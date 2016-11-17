package com.epam.carrental.aspects;


import com.epam.carrental.utils.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TenantHeaderAspect {

    @Before("execution(* com.epam.carrental.services.ServerInfo.*(..))")
    public void afterThrowingInExporter(JoinPoint jp) {
        if(Tenant.getId()==null){
            throw new IllegalArgumentException("Tenant not specified for this transaction");
        }
    }
}

