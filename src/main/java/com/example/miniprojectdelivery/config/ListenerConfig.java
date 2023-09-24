package com.example.miniprojectdelivery.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class ListenerConfig {
    private final ApplicationContext contextFromAutowired;

    @Getter
    private static ApplicationContext context;

    @PostConstruct
    public void setContext() {
        context = contextFromAutowired;
    }
}
