package com.wells.constants;

public enum NotificationsStatusEnum {

	READ("READ"),
	UNREAD("UNREAD");
	
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private NotificationsStatusEnum(String value) {
		this.value = value;
	}
}
