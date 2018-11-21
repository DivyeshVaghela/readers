package com.learning.readers.model;

import java.util.ArrayList;
import java.util.List;

public class ReaderHomeModel {

	private List<BookOverviewModel> resentlyAdded = new ArrayList<>();
	private List<BookOverviewModel> wishList = new ArrayList<>();
	private List<BookOverviewModel> sharedToMeBooks = new ArrayList<>();
	private List<ReadlistOverviewModel> readlists = new ArrayList<>();
	
	public List<BookOverviewModel> getResentlyAdded() {
		return resentlyAdded;
	}

	public void setResentlyAdded(List<BookOverviewModel> resentlyAdded) {
		this.resentlyAdded = resentlyAdded;
	}

	public List<BookOverviewModel> getWishList() {
		return wishList;
	}

	public void setWishList(List<BookOverviewModel> wishList) {
		this.wishList = wishList;
	}

	public List<BookOverviewModel> getSharedToMeBooks() {
		return sharedToMeBooks;
	}

	public void setSharedToMeBooks(List<BookOverviewModel> sharedToMeBooks) {
		this.sharedToMeBooks = sharedToMeBooks;
	}

	public List<ReadlistOverviewModel> getReadlists() {
		return readlists;
	}

	public void setReadlists(List<ReadlistOverviewModel> readlists) {
		this.readlists = readlists;
	}
	
	
	
}
