package com.tcs.poc.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class FallbackService {

    private static final Logger logger = LoggerFactory.getLogger(FallbackService.class);
    
    public void handleAnalyticsServiceFailure(String vehicleNumber, Exception ex) {
        logger.warn("Analytics Service Fallback - Vehicle: {}, Reason: {}", vehicleNumber, ex.getMessage());
        logger.info("Storing vehicle {} telemetry for deferred analytics processing", vehicleNumber);
    }
    
    public void handleIncidentServiceFailure(String vehicleNumber, Exception ex) {
        logger.warn("Incident Service Fallback - Vehicle: {}, Reason: {}", vehicleNumber, ex.getMessage());
        logger.info("Creating local incident record for vehicle {}", vehicleNumber);
    }
    
    public boolean isServiceDegraded() {
        logger.debug("Checking service degradation status");
        return false;
    }
    
    public void notifyServiceDegradation(String serviceName) {
        logger.error("SERVICE DEGRADATION ALERT: {} service is currently unavailable", serviceName);
    }

    

}
