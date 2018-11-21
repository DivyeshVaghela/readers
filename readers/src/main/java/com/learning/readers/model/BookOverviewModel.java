package com.learning.readers.model;

import java.util.Date;

import com.learning.readers.util.GeneralUtil;

public class BookOverviewModel {

	private Integer id;
	private String name;
	private String edition;
	private String coverPhoto;
	private String details;

	private BookShareOverviewModel bookShare;
	
	public BookOverviewModel() {}
	
	public BookOverviewModel(Integer id, String name, String edition, String coverPhoto, String details) {
		this.id = id;
		this.name = name;
		this.edition = edition;
		this.coverPhoto = coverPhoto;
		this.details = details;
	}

	public BookOverviewModel(Integer id, Integer bookShareId, String name, String edition, String coverPhoto,
			String details) {
		this.id = id;
		this.name = name;
		this.edition = edition;
		this.coverPhoto = coverPhoto;
		this.details = details;
		
		bookShare = new BookShareOverviewModel();
		bookShare.setId(bookShareId);
	}
	
	public BookOverviewModel(Integer id, Integer bookShareId, String name, String edition, String coverPhoto,
			String details, Integer senderId, Integer receiverId) {
		this.id = id;
		this.name = name;
		this.edition = edition;
		this.coverPhoto = coverPhoto;
		this.details = details;
		
		bookShare = new BookShareOverviewModel();
		bookShare.setId(bookShareId);
		bookShare.setSenderId(senderId);
		bookShare.setReceiverId(receiverId);
	}
	
	public BookOverviewModel(Integer id, Integer bookShareId, String name, String edition, String coverPhoto,
			String details, Integer senderId, Integer receiverId, String sender, String receiver) {
		this.id = id;
		this.name = name;
		this.edition = edition;
		this.coverPhoto = coverPhoto;
		this.details = details;
		
		bookShare = new BookShareOverviewModel();
		bookShare.setId(bookShareId);
		bookShare.setSenderId(senderId);
		bookShare.setReceiverId(receiverId);
		bookShare.setSender(sender);
		bookShare.setReceiver(receiver);
	}

	public BookOverviewModel(Integer id, Integer bookShareId, String name, String edition, String coverPhoto,
			String details, Integer senderId, Integer receiverId, String sender, String receiver, Date shareDateTime) {
		this.id = id;
		this.name = name;
		this.edition = edition;
		this.coverPhoto = coverPhoto;
		this.details = details;
		
		bookShare = new BookShareOverviewModel();
		bookShare.setId(bookShareId);
		bookShare.setSenderId(senderId);
		bookShare.setReceiverId(receiverId);
		bookShare.setSender(sender);
		bookShare.setReceiver(receiver);
		bookShare.setShareDateTime(shareDateTime);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return GeneralUtil.nullOrString(name);
		//return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEdition() {
		return GeneralUtil.nullOrString(edition);
		//return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getCoverPhoto() {
		return coverPhoto;
	}
	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	public String getFullName() {
		return this.getName() + (this.getEdition() == null || this.getEdition().isEmpty() ? "" : " (" + this.getEdition() + ")");
	}

	public BookShareOverviewModel getBookShare() {
		return bookShare;
	}

	public void setBookShare(BookShareOverviewModel bookShare) {
		this.bookShare = bookShare;
	}

	@Override
	public String toString() {
		return "BookOverviewModel [id=" + id + ", name=" + name + ", edition=" + edition + ", coverPhoto=" + coverPhoto
				+ ", details=" + details + ", bookShare=" + bookShare + "]";
	}
	
	
}
