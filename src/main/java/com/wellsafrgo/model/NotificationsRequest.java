package com.wellsafrgo.model;

public class NotificationsRequest {

	private String empId;
	private String status;
	private String message;
	private String typeofNotification;
	private String action;

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTypeofNotification() {
		return typeofNotification;
	}
	public void setTypeofNotification(String typeofNotification) {
		this.typeofNotification = typeofNotification;
	}


}
