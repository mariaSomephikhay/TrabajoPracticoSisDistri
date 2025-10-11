package com.grupoK.connector.database.configuration.annotations;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ComponentScan(
        basePackages = "com.grupoK.connector.database.serviceImp",
        includeFilters = @ComponentScan.Filter(ConsumerServerAnnotation.class),
        useDefaultFilters = false
)
public @interface ConsumerServerAnnotation {
}
