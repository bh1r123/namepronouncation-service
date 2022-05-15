package com.wells.constants;

/*
 * test commit
 */
public enum Channel {

	IVR("IVR"),
	WEB("WEB"),
	WHATSAPP("WHATSAPP");
	
    private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private Channel(String value) {
		this.setValue(value);
	}
}
