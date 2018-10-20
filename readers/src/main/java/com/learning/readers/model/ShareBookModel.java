package com.learning.readers.model;

import java.util.List;

public class ShareBookModel {

	private int bookId;
	private int[] selectedUsers;
	private List<UserNameEmailModel> userNameEmailList;
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int[] getSelectedUsers() {
		return selectedUsers;
	}
	public void setSelectedUsers(int[] selectedUsers) {
		this.selectedUsers = selectedUsers;
	}
	public List<UserNameEmailModel> getUserNameEmailList() {
		return userNameEmailList;
	}
	public void setUserNameEmailList(List<UserNameEmailModel> userNameEmailList) {
		this.userNameEmailList = userNameEmailList;
	}
	
	
}
