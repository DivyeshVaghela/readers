package com.learning.readers.model;

import com.learning.readers.util.GeneralUtil;

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
		return GeneralUtil.nullOrString(name);
		//return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEdition() {
		return GeneralUtil.nullOrString(edition);
		//return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getFullName() {
		return this.getName() + (this.getEdition() == null ? "" : " (" + this.getEdition() + ")");
	}
	
	
}
