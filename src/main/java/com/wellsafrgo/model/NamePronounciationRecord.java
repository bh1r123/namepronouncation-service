package com.wellsafrgo.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="NAME_PRONOUNCATION_TABLE")
@JsonIgnoreProperties(value = {"created_at", "modified_at"}, 
allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class NamePronounciationRecord {

	@Id
	@Column(name= "EMPID")
    private String empid;
	
	@Column(name= "FIRST_NAME")
	private String first_name;
	
	@Column(name= "LAST_NAME")
	private String last_name;
	
	@Column(name= "PREFERED_NAME")
	private String preferred_name;
	
	@Column(name= "COUNTRY_CODE")
	private String country_code;
	
	@Lob
	@Column(name= "AUDIO_FILE")
	private byte[] audio_file;
	
	@Column(name= "CREATED_BY")
	private String created_by;
	
	@Column(name= "MODIFIED_BY")
	private String modified_by;
	
	@CreatedDate
	@Column(name= "CREATED_AT", nullable = false, updatable = false)
	private Date created_at;
	
	@LastModifiedDate
	@Column(name= "MODIFIED_AT")
	private Date modified_at;
	
	@Column(name= "OPTED_FORMAT")
	private String	opted_format;
	
	@Lob
	@Column(name= "OVERRIDEN_FILE")
	private byte[] overriden_file;
	
	@Column(name= "STATUS")
	private String status;
	
	@Column(name= "OVERRIDEN_STATUS")
	private String overriden_Status;

	@Column(name= "CHANNEL")
	private String channel;

	@Column(name= "AUDIO_FILE_URL")
	private String audio_url;

	@Column(name= "OPTED")
	private String opted;


	public String getOpted() {
		return opted;
	}

	public void setOpted(String opted) {
		this.opted = opted;
	}

	public NamePronounciationRecord() {
		
	}
	 
    public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPreferred_name() {
		return preferred_name;
	}

	public void setPreferred_name(String preferred_name) {
		this.preferred_name = preferred_name;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public byte[] getAudio_file() {
		return audio_file;
	}

	public void setAudio_file(byte[] audio_file) {
		this.audio_file = audio_file;
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

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getModified_at() {
		return modified_at;
	}

	public void setModified_at(Date modified_at) {
		this.modified_at = modified_at;
	}

	public String getOpted_format() {
		return opted_format;
	}

	public void setOpted_format(String opted_format) {
		this.opted_format = opted_format;
	}

	public byte[] getOverriden_file() {
		return overriden_file;
	}

	public void setOverriden_file(byte[] overriden_file) {
		this.overriden_file = overriden_file;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOverriden_Status() {
		return overriden_Status;
	}

	public void setOverriden_Status(String overriden_Status) {
		this.overriden_Status = overriden_Status;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String getAudio_url() {
		return audio_url;
	}

	public void setAudio_url(String audio_url) {
		this.audio_url = audio_url;
	}
	
	public NamePronounciationRecord(String empId, String fname, String lname, String pname,String countryCode,
    		byte[] audioFile, String createdby, String modifiedby,
    		                           String optedformat, byte[] overridenFile, String status, String overridenStatus , String channel, String audio_url, String opted) {
		this.empid = empId;
		this.first_name=fname;
		this.last_name=lname;
		this.preferred_name=pname;
		this.country_code=countryCode;
		this.created_by=createdby;
		this.modified_by=modifiedby;
		this.opted_format=optedformat;
		this.overriden_file=overridenFile;
		this.audio_file=audioFile;
		this.status=status;
		this.overriden_Status=overridenStatus;
		this.channel = channel;
		this.audio_url = audio_url;
		this.opted = opted;
;	}

}
