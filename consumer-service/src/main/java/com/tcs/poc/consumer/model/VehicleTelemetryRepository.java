package com.tcs.poc.consumer.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTelemetryRepository extends JpaRepository<VehicleTelemetry, Long> {

}
