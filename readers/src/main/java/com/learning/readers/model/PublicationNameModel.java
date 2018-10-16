package com.learning.readers.model;

public class PublicationNameModel {

	private Integer id;
	private String name;
	
	public PublicationNameModel() {}
	
	public PublicationNameModel(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

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

	@Override
	public String toString() {
		return "PublicationNameModel [id=" + id + ", name=" + name + "]";
	}
	
	
}
