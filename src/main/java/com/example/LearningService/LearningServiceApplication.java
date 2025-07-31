package com.example.LearningService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LearningServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(LearningServiceApplication.class, args);
	}

}
