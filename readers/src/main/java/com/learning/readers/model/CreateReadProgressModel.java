package com.learning.readers.model;

import java.util.ArrayList;
import java.util.List;

public class CreateReadProgressModel {

	private Integer readStatus;
	private List<ReadStatusValueModel> readStatusList = new ArrayList<>();
	
	private CreateReadDetailsModel readDeatils;

	public Integer getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}

	public List<ReadStatusValueModel> getReadStatusList() {
		return readStatusList;
	}

	public void setReadStatusList(List<ReadStatusValueModel> readStatusList) {
		this.readStatusList = readStatusList;
	}

	public CreateReadDetailsModel getReadDeatils() {
		return readDeatils;
	}

	public void setReadDeatils(CreateReadDetailsModel readDeatils) {
		this.readDeatils = readDeatils;
	}
	
	
}
