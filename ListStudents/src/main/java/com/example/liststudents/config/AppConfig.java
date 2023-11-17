package com.example.liststudents.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("com.example")
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
}
