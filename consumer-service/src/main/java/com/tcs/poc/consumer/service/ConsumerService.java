package com.tcs.poc.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.tcs.poc.consumer.model.VehicleTelemetry;
import com.tcs.poc.consumer.model.VehicleTelemetryRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import tools.jackson.databind.ObjectMapper;

@Service
public class ConsumerService {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

	private final Double SPEED_THRESHOLD = 100.0;
	private final Double ACCELERATION_THRESHOLD = 10.0;
	private final Double IMPACT_G_THRESHOLD = 5.0;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	VehicleTelemetryRepository telemetryRepository;

	@Autowired
	WebClient.Builder webClientBuilder;

	@KafkaListener(topics = "vehicle-events", groupId = "consumer-group")
	public void consumeVehicleEvents(String message) {

		VehicleTelemetry telemetry = objectMapper.readValue(message, VehicleTelemetry.class);

		logger.info("Received vehicle telemetry : " + telemetry.toString());

		telemetry = telemetryRepository.save(telemetry);

		logger.info("Saved : " + telemetry.toString());

		detectAccident(telemetry);

	}

	private void detectAccident(VehicleTelemetry telemetry) {

		if (telemetry.getSpeedKph() > SPEED_THRESHOLD || telemetry.getAccelMps2() > ACCELERATION_THRESHOLD
				|| telemetry.getImpactG() > IMPACT_G_THRESHOLD) {

			logger.warn("Accident detected for vehicle : " + telemetry.getVehicleNo());

			callAnalyticsService(telemetry.getVehicleNo());
			callIncidentService(telemetry.getVehicleNo());

			logger.warn("Incident reported for vehicle : " + telemetry.getVehicleNo());
		}

	}

	@CircuitBreaker(name = "actionServiceCB", fallbackMethod = "callAnalyticsServiceFallback")
	@Retryable(maxRetries = 3, delay = 5000, excludes = { java.net.UnknownHostException.class })
	private void callAnalyticsService(String vehicleNo) {

		try {
			webClientBuilder.build().post().uri("http://localhost:1010/action/analytics").bodyValue(vehicleNo)
					.retrieve().bodyToMono(String.class).doOnNext(asyncResponse -> {
						logger.info("Analytics service(Async) completed : " + asyncResponse);
					}).subscribe();
		} catch (Exception e) {
			logger.error("Error calling analytics service for vehicle: " + vehicleNo, e);
			throw new RuntimeException("Analytics service call failed");
		}

	}

	@CircuitBreaker(name = "actionServiceCB", fallbackMethod = "callIncidentServiceFallback")
	@Retryable(maxRetries = 3, delay = 5000, excludes = { java.net.UnknownHostException.class })
	private void callIncidentService(String vehicleNo) {

		try {
			String syncResponse = webClientBuilder.build().post().uri("http://localhost:1010/action/incident")
					.bodyValue(vehicleNo).retrieve().bodyToMono(String.class).block();
			logger.info("Incident service completed : " + syncResponse);
		} catch (Exception e) {
			logger.error("Error calling incident service for vehicle: " + vehicleNo, e);
			throw new RuntimeException("Incident service call failed", e);
		}

	}

	public void callAnalyticsServiceFallback(String vehicleNumber, Exception ex) {
		logger.warn("Fallback triggered for analytics service for vehicle: " + vehicleNumber);
		logger.warn("Reason: " + ex.getMessage());
	}

	public void callIncidentServiceFallback(String vehicleNumber, Exception ex) {
		logger.warn("Fallback triggered for incident service for vehicle: " + vehicleNumber);
		logger.warn("Reason: " + ex.getMessage());
	}

}
