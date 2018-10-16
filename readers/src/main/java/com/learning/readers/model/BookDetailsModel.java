package com.learning.readers.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.learning.readers.entity.ReadDetail;

public class BookDetailsModel {

	private Integer id;
	private String name;
	private String coverPhoto;
	private String edition;
	private String details;
	private String ISBN;
	
	private Integer publishedYear;
	private Integer publishedMonth;
	private Integer publishedDate;
	
	private Boolean enabled;
	
	private Date creationTime;
	private Date modificationTime;
	
	private PublicationNameModel publication;
	private List<AuthorNameModel> authors = new ArrayList<>();
	private ReadStatusValueModel status;
	
	private BookTypeValueModel bookType;
	private BookSourceValueModel source;
	private ReadDetail readDetails;
	
	public BookDetailsModel() {}
	
	public BookDetailsModel(Integer id, String name, String coverPhoto, String edition, String details, String iSBN,
			Integer publishedYear, Integer publishedMonth, Integer publishedDate, Boolean enabled, Date creationTime,
			Date modificationTime, Integer publicationId, String publicationName, Integer readStatusId, String readStatusVaue,
			Integer bookTypeId, String bookTypeValue, Integer bookSourceId, String bookSourceValue, ReadDetail readDetails) {
		this.id = id;
		this.name = name;
		this.coverPhoto = coverPhoto;
		this.edition = edition;
		this.details = details;
		ISBN = iSBN;
		this.publishedYear = publishedYear;
		this.publishedMonth = publishedMonth;
		this.publishedDate = publishedDate;
		this.enabled = enabled;
		this.creationTime = creationTime;
		this.modificationTime = modificationTime;
		
		this.publication = new PublicationNameModel(publicationId, publicationName);
		
		this.status = new ReadStatusValueModel(readStatusId, readStatusVaue);
		
		this.bookType = new BookTypeValueModel(bookTypeId, bookTypeValue);
		
		this.source = new BookSourceValueModel(bookSourceId, bookSourceValue);
		
		this.readDetails = readDetails;
	}
	
	public BookDetailsModel(Integer id, String name, String coverPhoto, String edition, String details, String iSBN,
			Integer publishedYear, Integer publishedMonth, Integer publishedDate, Boolean enabled, Date creationTime,
			Date modificationTime, Integer publicationId, String publicationName) {
		this.id = id;
		this.name = name;
		this.coverPhoto = coverPhoto;
		this.edition = edition;
		this.details = details;
		ISBN = iSBN;
		this.publishedYear = publishedYear;
		this.publishedMonth = publishedMonth;
		this.publishedDate = publishedDate;
		this.enabled = enabled;
		this.creationTime = creationTime;
		this.modificationTime = modificationTime;
		this.publication = new PublicationNameModel(publicationId, publicationName);
	}

	
	public BookDetailsModel(Integer id, String name, String coverPhoto, String edition, String details, String iSBN,
			Integer publishedYear, Integer publishedMonth, Integer publishedDate, Boolean enabled, Date creationTime,
			Date modificationTime, PublicationNameModel publication) {
		this.id = id;
		this.name = name;
		this.coverPhoto = coverPhoto;
		this.edition = edition;
		this.details = details;
		ISBN = iSBN;
		this.publishedYear = publishedYear;
		this.publishedMonth = publishedMonth;
		this.publishedDate = publishedDate;
		this.enabled = enabled;
		this.creationTime = creationTime;
		this.modificationTime = modificationTime;
		this.publication = publication;
	}



	public BookDetailsModel(Integer id, String name, String coverPhoto, String edition, String details, String iSBN,
			Integer publishedYear, Integer publishedMonth, Integer publishedDate, Boolean enabled, Date creationTime,
			Date modificationTime) {
		this.id = id;
		this.name = name;
		this.coverPhoto = coverPhoto;
		this.edition = edition;
		this.details = details;
		ISBN = iSBN;
		this.publishedYear = publishedYear;
		this.publishedMonth = publishedMonth;
		this.publishedDate = publishedDate;
		this.enabled = enabled;
		this.creationTime = creationTime;
		this.modificationTime = modificationTime;
	}

	public BookDetailsModel(Integer id, String name, String coverPhoto, String edition, String details, String iSBN,
			Integer publishedYear, Integer publishedMonth, Integer publishedDate, Boolean enabled, Date creationTime,
			Date modificationTime, PublicationNameModel publication, 
			ReadStatusValueModel status, BookTypeValueModel bookType, BookSourceValueModel source,
			ReadDetail readDetails) {
		this.id = id;
		this.name = name;
		this.coverPhoto = coverPhoto;
		this.edition = edition;
		this.details = details;
		ISBN = iSBN;
		this.publishedYear = publishedYear;
		this.publishedMonth = publishedMonth;
		this.publishedDate = publishedDate;
		this.enabled = enabled;
		this.creationTime = creationTime;
		this.modificationTime = modificationTime;
		this.publication = publication;
		this.status = status;
		this.bookType = bookType;
		this.source = source;
		this.readDetails = readDetails;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCoverPhoto() {
		return coverPhoto;
	}
	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
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
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getModificationTime() {
		return modificationTime;
	}
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}
	public PublicationNameModel getPublication() {
		return publication;
	}
	public void setPublication(PublicationNameModel publication) {
		this.publication = publication;
	}
	public List<AuthorNameModel> getAuthors() {
		return authors;
	}
	public void setAuthors(List<AuthorNameModel> authors) {
		this.authors = authors;
	}
	public ReadStatusValueModel getStatus() {
		return status;
	}
	public void setStatus(ReadStatusValueModel status) {
		this.status = status;
	}
	public BookTypeValueModel getBookType() {
		return bookType;
	}
	public void setBookType(BookTypeValueModel bookType) {
		this.bookType = bookType;
	}
	public BookSourceValueModel getSource() {
		return source;
	}
	public void setSource(BookSourceValueModel source) {
		this.source = source;
	}
	public ReadDetail getReadDetails() {
		return readDetails;
	}
	public void setReadDetails(ReadDetail readDetails) {
		this.readDetails = readDetails;
	}
	@Override
	public String toString() {
		return "BookFullyLoadedModel [id=" + id + ", name=" + name + ", coverPhoto=" + coverPhoto + ", edition="
				+ edition + ", details=" + details + ", ISBN=" + ISBN + ", publishedYear=" + publishedYear
				+ ", publishedMonth=" + publishedMonth + ", publishedDate=" + publishedDate + ", enabled=" + enabled
				+ ", creationTime=" + creationTime + ", modificationTime=" + modificationTime + ", publication="
				+ publication + ", authors=" + authors + ", status=" + status + ", bookType=" + bookType + ", source="
				+ source + ", readDetails=" + readDetails + "]";
	}
	
	
}
