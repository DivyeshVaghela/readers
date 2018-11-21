package com.learning.readers.model;

import org.hibernate.validator.constraints.NotBlank;

public class ForgotPasswordModel {

	private Integer userId;
	
	@NotBlank(message="Please enter your email address")
	private String email;
	private String secretQuestion;
	private String secretAnswer;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSecretQuestion() {
		return secretQuestion;
	}
	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}
	public String getSecretAnswer() {
		return secretAnswer;
	}
	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
