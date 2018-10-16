package com.learning.readers.model;

public class BookOverviewModel {

	private Integer id;
	private String name;
	private String edition;
	private String coverPhoto;
	private String details;

	public BookOverviewModel() {}
	
	public BookOverviewModel(Integer id, String name, String edition, String coverPhoto, String details) {
		this.id = id;
		this.name = name;
		this.edition = edition;
		this.coverPhoto = coverPhoto;
		this.details = details;
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
	public String getCoverPhoto() {
		return coverPhoto;
	}
	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "BookOverviewModel [id=" + id + ", name=" + name + ", edition=" + edition + ", coverPhoto=" + coverPhoto
				+ ", details=" + details + "]";
	}
	
	
}
