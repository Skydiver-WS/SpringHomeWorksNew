package com.example.liststudents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.shell.command.annotation.CommandScan;

import java.io.IOException;

@SpringBootApplication
@CommandScan
@EnableScheduling
public class ListStudentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ListStudentsApplication.class, args);
    }
}
