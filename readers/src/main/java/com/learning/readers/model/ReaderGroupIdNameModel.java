package com.learning.readers.model;

public class ReaderGroupIdNameModel {

	public Integer id;
	public String name;

	public ReaderGroupIdNameModel() { }
	public ReaderGroupIdNameModel(Integer id, String name) {
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
	
	
}
