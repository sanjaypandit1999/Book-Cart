package com.bidgelabz.cartservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookCartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookCartServiceApplication.class, args);
	}

}
