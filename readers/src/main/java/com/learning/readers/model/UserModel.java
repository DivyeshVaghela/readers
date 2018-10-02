package com.learning.readers.model;

import java.io.Serializable;
import java.util.Set;

import com.learning.readers.entity.Role;

public class UserModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer userId;
	private String username;
	private String email;
	private Set<Role> roles;
	
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
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return "UserModel [userId=" + userId + ", username=" + username + ", email=" + email + "]";
	}
}
