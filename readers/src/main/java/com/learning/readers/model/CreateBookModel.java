package com.learning.readers.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class CreateBookModel {

	private Integer id;

	@Valid
	private CreateBookBasicDetailsModel basicDetails;
	
	/*@NotBlank(message="Book name is mandatory field")
	private String name;

	private MultipartFile bookCover;
	
	private String edition;
	private String details;
	private String ISBN;
	
	private Integer publishedYear;
	private Integer publishedMonth;
	private Integer publishedDate;*/
	
	private CreatePublicationModel publication;
	private Integer publicationId;
	private List<PublicationNameModel> publicationList = new ArrayList<>();

	//private List<BookTypeValueModel> bookTypeList = new ArrayList<>();
	
	@Valid
	private CreateBookSourceModel bookSource;
	//private MultipartFile bookFile;
	
	//@NotNull(message="Read status is mandatory field")
	private Integer readStatus;
	private List<ReadStatusValueModel> readStatusList = new ArrayList<>();
	
	private CreateReadDetailsModel readDetails;
	
	private List<CreateAuthorModel> authors = new ArrayList<>();
	private List<Integer> authorIds = new ArrayList<>();
	private List<AuthorNameModel> authorList = new ArrayList<>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public CreateBookBasicDetailsModel getBasicDetails() {
		return basicDetails;
	}
	public void setBasicDetails(CreateBookBasicDetailsModel basicDetails) {
		this.basicDetails = basicDetails;
	}
	/*public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	}*/
	public CreatePublicationModel getPublication() {
		return publication;
	}
	public void setPublication(CreatePublicationModel publication) {
		this.publication = publication;
	}
	public Integer getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}
	public List<CreateAuthorModel> getAuthors() {
		return authors;
	}
	public void setAuthors(List<CreateAuthorModel> authors) {
		this.authors = authors;
	}
	public List<AuthorNameModel> getAuthorList() {
		return authorList;
	}
	public void setAuthorList(List<AuthorNameModel> authorList) {
		this.authorList = authorList;
	}
	public List<PublicationNameModel> getPublicationList() {
		return publicationList;
	}
	public void setPublicationList(List<PublicationNameModel> publicationList) {
		this.publicationList = publicationList;
	}
	public Integer getPublicationId() {
		return publicationId;
	}
	public void setPublicationId(Integer publicationId) {
		this.publicationId = publicationId;
	}
	public List<Integer> getAuthorIds() {
		return authorIds;
	}
	public void setAuthorIds(List<Integer> authorIds) {
		this.authorIds = authorIds;
	}
	public List<ReadStatusValueModel> getReadStatusList() {
		return readStatusList;
	}
	public void setReadStatusList(List<ReadStatusValueModel> readStatusList) {
		this.readStatusList = readStatusList;
	}
	/*public List<BookTypeValueModel> getBookTypeList() {
		return bookTypeList;
	}
	public void setBookTypeList(List<BookTypeValueModel> bookTypeList) {
		this.bookTypeList = bookTypeList;
	}*/
	public CreateBookSourceModel getBookSource() {
		return bookSource;
	}
	public void setBookSource(CreateBookSourceModel bookSource) {
		this.bookSource = bookSource;
	}
	
	public CreateReadDetailsModel getReadDetails() {
		return readDetails;
	}
	public void setReadDetails(CreateReadDetailsModel readDetails) {
		this.readDetails = readDetails;
	}
	/*public MultipartFile getBookCover() {
		return bookCover;
	}
	public void setBookCover(MultipartFile bookCover) {
		this.bookCover = bookCover;
	}*/
	
	

}
