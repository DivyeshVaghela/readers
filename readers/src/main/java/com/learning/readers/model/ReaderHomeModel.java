package com.learning.readers.model;

import java.util.ArrayList;
import java.util.List;

public class ReaderHomeModel {

	private List<BookOverviewModel> resentlyAdded = new ArrayList<>();

	public List<BookOverviewModel> getResentlyAdded() {
		return resentlyAdded;
	}

	public void setResentlyAdded(List<BookOverviewModel> resentlyAdded) {
		this.resentlyAdded = resentlyAdded;
	}
	
	
}
