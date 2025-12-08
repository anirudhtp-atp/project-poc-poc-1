package com.tcs.poc.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.tcs.poc.consumer.model.VehicleTelemetry;
import com.tcs.poc.consumer.model.VehicleTelemetryRepository;

import tools.jackson.databind.ObjectMapper;

@Service
public class ConsumerService {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	VehicleTelemetryRepository telemetryRepository;
	
	@KafkaListener(topics = "vehicle-events", groupId = "consumer-group" )
	public void consumeVehicleEvents(String message) {
		
		VehicleTelemetry telemetry = objectMapper.readValue(message, VehicleTelemetry.class);
		
		logger.info("Received vehicle telemetry : " + telemetry.toString());
		
		
	}
	
}
