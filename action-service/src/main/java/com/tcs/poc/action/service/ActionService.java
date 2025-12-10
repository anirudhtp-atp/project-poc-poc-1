package com.tcs.poc.action.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class ActionService {

	private static final Logger logger = LoggerFactory.getLogger(ActionService.class);
	
    @CircuitBreaker(name = "incidentServiceCB", fallbackMethod = "syncIncidentReportFallback")
	public String syncIncidentReport() {
		logger.info("Synchronus process for incident report");
		
		
		return "INCIDENT REPORTED";
	}
	
    @CircuitBreaker(name = "incidentServiceCB", fallbackMethod = "asyncAnalyticsDashboardFallback")
	public String asyncAnalyticsDashboard() {
		logger.info("Synchronus process for incident report");
		
		return "ANALYTICS COMPLETED";
	}
    
    public String asyncAnalyticsDashboardFallback(Exception ex) {
        logger.warn("Fallback triggered for analytics dashboard: " + ex.getMessage());
        return "ANALYTICS FALLBACK - Service temporarily unavailable";
    }
    

    
	
}
