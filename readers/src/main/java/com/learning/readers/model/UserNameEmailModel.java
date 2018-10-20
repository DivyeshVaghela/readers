package com.learning.readers.model;

public class UserNameEmailModel {

	private Integer userId;
	private String username;
	private String email;
	
	public UserNameEmailModel() {}
	
	public UserNameEmailModel(Integer userId, String username, String email) {
		this.userId = userId;
		this.username = username;
		this.email = email;
	}

	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserNameEmailModel [userId=" + userId + ", username=" + username + ", email=" + email + "]";
	}
	
	
}
