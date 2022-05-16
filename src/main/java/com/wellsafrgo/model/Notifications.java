package com.wellsafrgo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "notifications")
public class Notifications {

	private long id;
	private String empId;
	private String status;
	private String message;
	private String typeofNotification;
	
	@Column(name = "ACTION")
	private String action;
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long  getId() {
		return id;
	}
	public void setId(long  id) {
		this.id = id;
	}
	
	@Column(name = "EMPID", nullable = false)
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	@Column(name = "STATUS", nullable = false)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "MESSAGE", nullable = false)
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Column(name = "TYPE_OF_NOTIFICATION", nullable = false)
	public String getTypeofNotification() {
		return typeofNotification;
	}
	public void setTypeofNotification(String typeofNotification) {
		this.typeofNotification = typeofNotification;
	}

}
