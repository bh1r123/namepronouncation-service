package com.wellsfargo.response;

public class SuccessResponse {

	private String message;

	public SuccessResponse(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
