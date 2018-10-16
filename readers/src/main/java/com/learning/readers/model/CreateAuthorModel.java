package com.learning.readers.model;

public class CreateAuthorModel {

	private Integer id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String details;
	
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
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	public boolean isAnyNotNull() {
		
		return (!isNullOrEmpty(firstName) || !isNullOrEmpty(lastName) || !isNullOrEmpty(details));
	}
	
	public boolean isNullOrEmpty(String field) {
		return (field==null || field.isEmpty());
	}
	
	@Override
	public String toString() {
		return "CreateAuthorModel [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", details=" + details + "]";
	}
	
	
}
