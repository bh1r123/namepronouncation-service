package com.wellsafrgo.model;

import org.springframework.web.multipart.MultipartFile;

public class NPSDomain {

	private String empId;
	private String fName;
	private String lName;
	private String pName;
	private String country_code;
	private String created_by;
	private String modified_by;
	private String optedformat;
	private String status;
	private String overridenStatus;
	private String channel;
	private boolean hasOverridenFile = false;
	

	public boolean isHasOverridenFile() {
		return hasOverridenFile;
	}

	public void setHasOverridenFile(boolean hasOverridenFile) {
		this.hasOverridenFile = hasOverridenFile;
	}

	private MultipartFile multipartFile;
 
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getOptedformat() {
		return optedformat;
	}

	public void setOptedformat(String optedformat) {
		this.optedformat = optedformat;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOverridenStatus() {
		return overridenStatus;
	}

	public void setOverridenStatus(String overridenStatus) {
		this.overridenStatus = overridenStatus;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getModified_by() {
		return modified_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
