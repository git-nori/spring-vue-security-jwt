package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@PropertySource("classpath:message.properties")
@ConfigurationProperties(prefix = "success")
@Data
public class SuccessContext {
    private String registered;
}
