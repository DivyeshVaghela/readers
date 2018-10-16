package com.learning.readers.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class CreateBookBasicDetailsModel {

	@NotBlank(message="Book name is mandatory field")
	private String name;

	private MultipartFile bookCover;
	
	private String edition;
	private String details;
	private String ISBN;
	
	private Integer publishedYear;
	private Integer publishedMonth;
	private Integer publishedDate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MultipartFile getBookCover() {
		return bookCover;
	}
	public void setBookCover(MultipartFile bookCover) {
		this.bookCover = bookCover;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public Integer getPublishedYear() {
		return publishedYear;
	}
	public void setPublishedYear(Integer publishedYear) {
		this.publishedYear = publishedYear;
	}
	public Integer getPublishedMonth() {
		return publishedMonth;
	}
	public void setPublishedMonth(Integer publishedMonth) {
		this.publishedMonth = publishedMonth;
	}
	public Integer getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(Integer publishedDate) {
		this.publishedDate = publishedDate;
	}
	
	
}
