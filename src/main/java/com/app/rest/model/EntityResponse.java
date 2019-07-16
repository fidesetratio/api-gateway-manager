package com.app.rest.model;

public class EntityResponse {
	
	private int response;
	private String message;
	
	public EntityResponse() {
		this.response = 0;
		this.message = "sucess";
	}

	public int getResponse() {
		return response;
	}

	public void setResponse(int response) {
		this.response = response;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
