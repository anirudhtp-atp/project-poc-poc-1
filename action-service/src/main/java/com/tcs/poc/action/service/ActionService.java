package com.tcs.poc.action.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ActionService {

	private static final Logger logger = LoggerFactory.getLogger(ActionService.class);
	
	
	public String syncIncidentReport() {
		logger.info("Synchronus process for incident report");
		
		return "INCIDENT REPORTED";
	}
	
	public String asyncAnalyticsDashboard() {
		logger.info("Synchronus process for incident report");
		
		return "ANALYTICS COMPLETED";
	}
	
}
