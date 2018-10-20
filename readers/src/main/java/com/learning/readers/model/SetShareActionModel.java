package com.learning.readers.model;

import java.util.List;

public class SetShareActionModel {

	private int bookShareId;
	private Integer shareActionId;
	
	private List<ShareActionValueModel> shareActionList;

	public int getBookShareId() {
		return bookShareId;
	}

	public void setBookShareId(int bookShareId) {
		this.bookShareId = bookShareId;
	}

	public Integer getShareActionId() {
		return shareActionId;
	}

	public void setShareActionId(Integer shareActionId) {
		this.shareActionId = shareActionId;
	}

	public List<ShareActionValueModel> getShareActionList() {
		return shareActionList;
	}

	public void setShareActionList(List<ShareActionValueModel> shareActionList) {
		this.shareActionList = shareActionList;
	}
	
	
}
