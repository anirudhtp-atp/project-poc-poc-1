package com.tcs.poc.action.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Configuration
@EnableRetry
public class Resilience4jConfig {

	@Bean
	public CircuitBreakerRegistry circuitBreakerRegistry() {
		return CircuitBreakerRegistry.ofDefaults();
	}
}
