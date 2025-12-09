package com.tcs.poc.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ConsumerServiceApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ConsumerServiceApplication.class, args);

	}
	
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}
	
	

}
