package com.learning.readers.model;

public class ReadlistOverviewModel {

	private Integer id;
	private String name;
	private Integer creatorId;
	private Long totalBooks;
	
	public ReadlistOverviewModel() {}
	public ReadlistOverviewModel(Integer id, String name, Integer creatorId, Long totalBooks) {
		this.id = id;
		this.name = name;
		this.creatorId = creatorId;
		this.totalBooks = totalBooks;
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
	public Long getTotalBooks() {
		return totalBooks;
	}
	public void setTotalBooks(Long totalBooks) {
		this.totalBooks = totalBooks;
	}
	public Integer getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	
	
}
