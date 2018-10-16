package com.learning.readers.model;

public class AuthorNameModel {

	private Integer id;
	private String firstName;
	private String middleName;
	private String lastName;

	public AuthorNameModel() {}
	
	public AuthorNameModel(Integer id, String firstName, String middleName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return this.firstName + (this.middleName == null ? "" : " " + this.middleName) + (this.lastName == null ? "" : " " + this.lastName);
	}
	
	@Override
	public String toString() {
		return "AuthorNameModel [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + "]";
	}
}
