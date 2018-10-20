package com.learning.readers.model;

import java.util.ArrayList;
import java.util.List;

public class CreateReadProgressModel {

	private Integer readStatus;
	private List<ReadStatusValueModel> readStatusList = new ArrayList<>();
	
	private CreateReadDetailsModel readDetails;

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

	public CreateReadDetailsModel getReadDetails() {
		return readDetails;
	}

	public void setReadDetails(CreateReadDetailsModel readDetails) {
		this.readDetails = readDetails;
	}
	
	
}
