package com.wells.constants;

public enum OptedFormatEnum {

	NOT_OPTED("NOT_OPTED"),
	STANDARD("STANDARD"),
	CUSTOM("CUSTOM");
	
    private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private OptedFormatEnum(String value) {
		this.setValue(value);
	}
}
