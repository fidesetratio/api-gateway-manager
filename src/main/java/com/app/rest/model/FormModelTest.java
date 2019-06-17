package com.app.rest.model;

import javax.validation.constraints.NotEmpty;

public class FormModelTest {
	@NotEmpty(message="Please Enter your First Name")
	private String firstName;

	@NotEmpty(message="Please Enter your Description")
	private String description;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
