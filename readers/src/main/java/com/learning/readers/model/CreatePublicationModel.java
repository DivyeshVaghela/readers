package com.learning.readers.model;

public class CreatePublicationModel {

	private Integer id;
	private String name;
	private String details;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	public boolean isAnyNotNull() {
		return (!isNullOrEmpty(name) || isNullOrEmpty(details));
	}
	
	public boolean isNullOrEmpty(String field) {
		return (field==null || field.isEmpty());
	}
	
	@Override
	public String toString() {
		return "CreatePublicationModel [name=" + name + ", details=" + details + "]";
	}
	
	
}
