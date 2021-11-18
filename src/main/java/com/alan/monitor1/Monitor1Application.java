package com.alan.monitor1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Monitor1Application {

	public static void main(String[] args) {
		SpringApplication.run(Monitor1Application.class, args);
	}
}
