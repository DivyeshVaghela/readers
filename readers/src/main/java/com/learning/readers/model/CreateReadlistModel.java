package com.learning.readers.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class CreateReadlistModel {

	private Integer id;
	
	@NotBlank(message="Name is required field")
	private String name;
	private String details;
	
	private List<BookNameEditionModel> bookList = new ArrayList<>();
	private int[] selectedBookIds;
	
	
	public CreateReadlistModel(Integer id, String name, String details, List<BookNameEditionModel> bookList,
			int[] selectedBookIds) {
		this.id = id;
		this.name = name;
		this.details = details;
		this.bookList = bookList;
		this.selectedBookIds = selectedBookIds;
	}
	public CreateReadlistModel() { }

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
	public List<BookNameEditionModel> getBookList() {
		return bookList;
	}
	public void setBookList(List<BookNameEditionModel> bookList) {
		this.bookList = bookList;
	}
	public int[] getSelectedBookIds() {
		return selectedBookIds;
	}
	public void setSelectedBookIds(int[] selectedBookIds) {
		this.selectedBookIds = selectedBookIds;
	}
	
	
}
