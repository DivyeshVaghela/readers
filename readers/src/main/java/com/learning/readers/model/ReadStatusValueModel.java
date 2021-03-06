package com.learning.readers.model;

public class ReadStatusValueModel {

	private Integer id;
	private String value;
	
	public ReadStatusValueModel() {}
	
	public ReadStatusValueModel(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ReadStatusValueModel [id=" + id + ", value=" + value + "]";
	}
}
