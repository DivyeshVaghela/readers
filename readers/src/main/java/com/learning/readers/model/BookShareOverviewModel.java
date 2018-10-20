package com.learning.readers.model;

import java.util.Date;

public class BookShareOverviewModel {

	private Integer id;
	private Integer senderId;
	private String sender;
	private Integer receiverId;
	private String receiver;
	private Date shareDateTime;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSenderId() {
		return senderId;
	}
	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}
	public Integer getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	
	public Date getShareDateTime() {
		return shareDateTime;
	}
	public void setShareDateTime(Date shareDateTime) {
		this.shareDateTime = shareDateTime;
	}
	@Override
	public String toString() {
		return "BookShareOverviewModel [id=" + id + ", senderId=" + senderId + ", sender=" + sender + ", receiverId="
				+ receiverId + ", receiver=" + receiver + "]";
	}
	
	
}
