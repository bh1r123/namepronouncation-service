package com.wells.constants;

public enum OptedEnum {

	YES("YES"),
	NO("NO");
	
    private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private OptedEnum(String value) {
		this.setValue(value);
	}
}
