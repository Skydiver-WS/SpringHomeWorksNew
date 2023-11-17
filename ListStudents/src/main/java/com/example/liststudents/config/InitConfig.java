package com.example.liststudents.config;

import com.example.liststudents.beans.Initializer;
import com.example.liststudents.beans.StudentsInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("init")
public class InitConfig {
    @Bean
    public Initializer initializer(){
        return new StudentsInitializer();
    }
}
