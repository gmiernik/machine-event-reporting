package com.melexis.test.eventconsumer;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melexis.test.MachineType;

public class MachineEvent implements Serializable {

	private static final long serialVersionUID = -2104220153622042523L;

	private String machineType;
	
	private String machineID;
	
	private LocalDateTime dateTime;
	
	private String errorType;

	public String getMachineType() {
		return machineType;
	}
	
	public MachineType getMachineTypeEnum() {
		return MachineType.valueOf(getMachineType());
	}

	public String getMachineID() {
		return machineID;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}

	public void setMachineID(String machineID) {
		this.machineID = machineID;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	
}
