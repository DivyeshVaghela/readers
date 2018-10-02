package com.learning.readers.model;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import com.learning.readers.dao.IUserDAO;

public class SignUpReaderModel {

	@NotBlank(message="Username is mandatory field")
	private String username;
	
	@NotBlank(message="Email is required")
	@Email(message="Please enter a valid email address")
	private String email;
	
	@NotBlank(message="Password is required")
	private String password;
	
	@NotBlank(message="Confirm password is required")
	private String confirmPassword;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	/*@Autowired
	IUserDAO userDAO;

	private boolean usernameExists;

	@AssertFalse(message="Username already taken")
	public boolean isUsernameExists() {
		usernameExists = false;
		if (username != null && !username.isEmpty()) {
			usernameExists = userDAO.exists("username", username);
		}
		return usernameExists;
	}
	public void setUsernameExists(boolean usernameExists) {
		this.usernameExists = usernameExists;
	}*/

	private boolean passwordMatching;
	
	@AssertTrue(message="Password and Confirm Password doesn't match")
	public boolean isPasswordMatching() {
		if (password==null) {
			passwordMatching = confirmPassword==null;
		} else {
			passwordMatching = password.equals(confirmPassword);
		}
		return passwordMatching;
	}

	public void setPasswordMatching(boolean passwordMatching) {
		this.passwordMatching = passwordMatching;
	}
	
	@Override
	public String toString() {
		return "SignUpReaderModel [username=" + username + ", email=" + email + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + "]";
	}
	
}
