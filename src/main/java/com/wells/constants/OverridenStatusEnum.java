package com.wells.constants;

public enum OverridenStatusEnum {

	PENDING("PENDING"),
	APPROVED("APPROVED"),
	REJECTED("REJECTED"),
	NEW("NEW");
	
	private String value;
	
	
	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	private OverridenStatusEnum(String value) {
		this.setValue(value);
	}
}
