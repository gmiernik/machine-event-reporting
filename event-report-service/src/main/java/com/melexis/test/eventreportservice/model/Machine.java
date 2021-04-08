package com.melexis.test.eventreportservice.model;

public class Machine {

	private String id;
	private MachineType type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public MachineType getType() {
		return type;
	}
	public void setType(MachineType type) {
		this.type = type;
	}
}
