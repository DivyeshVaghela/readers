package com.learning.readers.model;

public class BookNameEditionModel {

	private Integer id;
	private String name;
	private String edition;
	
	
	
	public BookNameEditionModel() {}
	public BookNameEditionModel(Integer id, String name, String edition) {
		this.id = id;
		this.name = name;
		this.edition = edition;
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
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	
	
}
