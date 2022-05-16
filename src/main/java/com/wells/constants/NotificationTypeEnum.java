package com.wells.constants;

public enum NotificationTypeEnum {

	ADMIN_APPROVAL("ADMIN_APPROVED"),
	ADMIN_REJECTION("ADMIN_REJECTION"),
	ADMIN_PENDING("ADMIN_PENDING");
	
private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private NotificationTypeEnum(String value) {
		this.value = value;
	}
}
