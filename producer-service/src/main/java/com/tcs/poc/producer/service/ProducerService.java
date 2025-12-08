package com.tcs.poc.producer.service;

import java.time.Instant;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.tcs.poc.producer.model.VehicleTelemetry;

import tools.jackson.databind.ObjectMapper;

@Service
public class ProducerService {

	private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	private final  Random random = new Random();
	
	private final ObjectMapper objectMapper;
	
	public ProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
		vehicleTelemetryProduction();
	}

	private void vehicleTelemetryProduction() {

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				
				
				VehicleTelemetry telemetry = new VehicleTelemetry();
				
				telemetry.setVehicleNo(String.format("vehicle-id-%d", random.nextInt(20)));
				telemetry.setTimestamp(Instant.now());
				telemetry.setSpeedKph(random.nextDouble() * 200);
				telemetry.setAccelMps2(random.nextDouble() * 10);
				telemetry.setImpactG(random.nextDouble() * 5);
				
				kafkaTemplate.send("vehicle-events", telemetry.getVehicleNo(), objectMapper.writeValueAsString(telemetry));
				logger.info("Produced event : " + telemetry.toString());
				
			}
		}, 0, 5000);
	}
}
