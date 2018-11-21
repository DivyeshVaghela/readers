package com.learning.readers.model;

import org.hibernate.validator.constraints.NotBlank;

import com.learning.readers.validator.EqualFields;

@EqualFields(
		baseField = "newPassword", 
		matchField = "confirmNewPassword",
		message="New Password and Confirm Password doesn't match")
public class ChangePasswordModel {

	@NotBlank(message="Please enter your current password")
	private String currentPassword;
	
	@NotBlank(message="Please enter your new password")
	private String newPassword;
	
	@NotBlank(message="Please confirm your new password")
	private String confirmNewPassword;
	
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
	
	
}
