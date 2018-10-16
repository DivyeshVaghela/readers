package com.learning.readers.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="book")
@DynamicInsert
@DynamicUpdate
public class Book {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;

	@Column(name="cover_photo")
	private String coverPhoto;
	
	@Column(name="edition")
	private String edition;
	
	@Column(name="details")
	private String details;
	
	@Column(name="isbn")
	private String ISBN;
	
	@Column(name="published_year")
	private Integer publishedYear;
	
	@Column(name="published_month")
	private Integer publishedMonth;
	
	@Column(name="published_date")
	private Integer publishedDate;
	
	@Column(name="enabled")
	private Boolean enabled;
	
	@Column(name="creation_time", insertable=false, updatable=false)
	private Date creationTime;
	
	@Column(name="modification_time", insertable=false, updatable=false)
	private Date modificationTime;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="publication_id")
	private Publication publication;
	
	@Column(name="publication_id", insertable=false, updatable=false)
	private Integer publicationId;

	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User reader;
	
	@Column(name="user_id", insertable=false, updatable=false)
	private Integer readerID;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="status_id")
	private ReadStatus status;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name="book_author", 
			joinColumns = {@JoinColumn(name="book_id", referencedColumnName="id")},
			inverseJoinColumns = {@JoinColumn(name="author_id", referencedColumnName="id")})
	private List<Author> authors = new ArrayList<>();
	
	@OneToOne(mappedBy="book", cascade=CascadeType.ALL)
	private BookSource source;
	
	@OneToOne(mappedBy="book", cascade=CascadeType.ALL)
	private ReadDetail readDetails;
	
	public Book() {}
	
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

	public String getEdition() {
		if (edition.isEmpty())
			return null;
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

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public User getReader() {
		return reader;
	}

	public void setReader(User reader) {
		this.reader = reader;
	}

	public ReadStatus getStatus() {
		return status;
	}

	public void setStatus(ReadStatus status) {
		this.status = status;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public Integer getReaderID() {
		return readerID;
	}

	public void setReaderID(Integer readerID) {
		this.readerID = readerID;
	}

	public Integer getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Integer publicationId) {
		this.publicationId = publicationId;
	}
	
	public BookSource getSource() {
		return source;
	}

	public void setSource(BookSource source) {
		this.source = source;
	}
	
	public ReadDetail getReadDetails() {
		return readDetails;
	}

	public void setReadDetails(ReadDetail readDetails) {
		this.readDetails = readDetails;
	}

	public String getCoverPhoto() {
		return coverPhoto;
	}

	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}
	
	public String getFullName() {
		return this.getName() + (this.getEdition() == null ? "" : " (" + this.getEdition() + ")");
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", edition=" + edition + ", details=" + details + ", ISBN=" + ISBN
				+ ", publishedYear=" + publishedYear + ", publishedMonth=" + publishedMonth + ", publishedDate="
				+ publishedDate + ", enabled=" + enabled + ", creationTime=" + creationTime + ", modificationTime="
				+ modificationTime + "]";
	}
	
}
