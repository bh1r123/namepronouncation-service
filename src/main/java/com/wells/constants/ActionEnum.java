package com.wells.constants;

public enum ActionEnum {

	ADMIN("ADMIN"),
	EMPID("EMPID");
	
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private ActionEnum(String value) {
		this.value = value;
	}
}
