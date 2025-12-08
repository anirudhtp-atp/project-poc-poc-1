package com.tcs.poc.producer.model;

import java.time.Instant;

public class VehicleTelemetry {

	
	private String vehicleNo;
	private Instant timestamp;
	private Double speedKph;
	private Double accelMps2;
	private Double impactG;
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public Instant getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
	public Double getSpeedKph() {
		return speedKph;
	}
	public void setSpeedKph(Double speedKph) {
		this.speedKph = speedKph;
	}
	public Double getAccelMps2() {
		return accelMps2;
	}
	public void setAccelMps2(Double accelMps2) {
		this.accelMps2 = accelMps2;
	}
	public Double getImpactG() {
		return impactG;
	}
	public void setImpactG(Double impactG) {
		this.impactG = impactG;
	}
	@Override
	public String toString() {
		return "VehicleTelemetry [vehicleNo=" + vehicleNo + ", timestamp=" + timestamp + ", speedKph=" + speedKph
				+ ", accelMps2=" + accelMps2 + ", impactG=" + impactG + "]";
	}
	
	
}
