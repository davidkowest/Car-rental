package com.epam.carrental.data_generator;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class GenerateCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
            Environment env = conditionContext.getEnvironment();
            return null != env
                    && "true".equals(env.getProperty("need.generate.data"));
    }
}
