package com.example.liststudents.config;


import com.example.liststudents.beans.Initializer;
import com.example.liststudents.beans.NotInitStudents;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class DefaultInitConfig {
    @Bean
    public Initializer initializer(){
        return new NotInitStudents();
    }
}
