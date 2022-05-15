package com.wellsafrgo.model;

import org.springframework.web.multipart.MultipartFile;

public class empUpdateRecord {

	private String empId;
	private String channel;
	private String audio_file_url;
	
	private MultipartFile multipartFile;
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String getAudio_file_url() {
		return audio_file_url;
	}
	public void setAudio_file_url(String audio_file_url) {
		this.audio_file_url = audio_file_url;
	}
}
