package com.orderprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class OrderProcessingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderProcessingSystemApplication.class, args);
	}

}
