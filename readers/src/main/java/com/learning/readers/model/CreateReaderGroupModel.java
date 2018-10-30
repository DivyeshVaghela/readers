package com.learning.readers.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class CreateReaderGroupModel {

	private Integer id;
	
	@NotBlank(message="Name is required field")
	private String name;
	private List<Integer> selectedMemberdId = new ArrayList<>();
	private List<UserNameEmailModel> userList = new ArrayList<>();
	
	private List<Integer> selectedBookId = new ArrayList<>();
	private List<BookNameEditionModel> bookList = new ArrayList<>();
	
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
	public List<Integer> getSelectedMemberdId() {
		return selectedMemberdId;
	}
	public void setSelectedMemberdId(List<Integer> selectedMemberdId) {
		this.selectedMemberdId = selectedMemberdId;
	}
	public List<UserNameEmailModel> getUserList() {
		return userList;
	}
	public void setUserList(List<UserNameEmailModel> userList) {
		this.userList = userList;
	}
	public List<BookNameEditionModel> getBookList() {
		return bookList;
	}
	public void setBookList(List<BookNameEditionModel> bookList) {
		this.bookList = bookList;
	}
	public List<Integer> getSelectedBookId() {
		return selectedBookId;
	}
	public void setSelectedBookId(List<Integer> selectedBookId) {
		this.selectedBookId = selectedBookId;
	}
	
	
}
