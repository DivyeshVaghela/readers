package com.learning.readers.model;

public class ReaderGroupOverviewModel {

	private Integer id;
	private String name;
	private Integer creatorId;
	private Long totalMembers;
	private Long totalBooks;

	public ReaderGroupOverviewModel() {}
	
	public ReaderGroupOverviewModel(Integer id, String name, Integer creatorId, Long totalMembers, Long totalBooks) {
		this.id = id;
		this.name = name;
		this.creatorId = creatorId;
		this.totalMembers = totalMembers;
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
	public Long getTotalMembers() {
		return totalMembers;
	}
	public void setTotalMembers(Long totalMembers) {
		this.totalMembers = totalMembers;
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

	@Override
	public String toString() {
		return "ReaderGroupOverviewModel [id=" + id + ", name=" + name + ", totalMembers=" + totalMembers
				+ ", totalBooks=" + totalBooks + "]";
	}
	
	
}
