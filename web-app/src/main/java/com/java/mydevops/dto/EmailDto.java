package com.java.mydevops.dto;

public class EmailDto {
	
	private String email;
	private String response;

	public EmailDto() {
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "EmailDto [response=" + response + ", email=" + email + "]";
	}
	
}