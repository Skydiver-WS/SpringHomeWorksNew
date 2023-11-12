package com.example.liststudents;

import com.example.liststudents.beans.StudentsList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;
import org.springframework.shell.command.annotation.EnableCommand;

@SpringBootApplication
@CommandScan
public class ListStudentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListStudentsApplication.class, args);
	}

}
