package com.tcs.poc.action.controller;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.poc.action.service.ActionService;

@RestController
@RequestMapping("/action")
public class ActionController {

	private static final Logger logger = LoggerFactory.getLogger(ActionController.class);

	@Autowired
	ActionService actionService;

	@PostMapping("/incident")
	public ResponseEntity<String> incidentReport(@RequestBody String vehicleNumber) {
		logger.info("Incident reported for vehicle : " + vehicleNumber);
		String response = actionService.syncIncidentReport();
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

	}

	@PostMapping("/analytics")
	public ResponseEntity<String> analytics(@RequestBody String vehicleNumber) {
		logger.info("Analytics trigger received for vehicle : " + vehicleNumber);
		int delay = ThreadLocalRandom.current().nextInt(1000, 5000 + 1);

	    try {
	        Thread.sleep(delay);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	        logger.error("Thread interrupted during simulated delay", e);
	    }


	    
		String response = actionService.asyncAnalyticsDashboard();
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

}
