package com.learning.readers.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="readlist_book")
@DynamicInsert
@DynamicUpdate
public class ReadlistBook {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="readlist_id")
	private Integer readlistId;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="readlist_id", insertable=false, updatable=false)
	private Readlist readlist;
	
	@Column(name="book_id")
	private Integer bookId;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="book_id", insertable=false, updatable=false)
	private Book book;
	
	@Column(name="enabled")
	private Boolean enabled;
	
	@Column(name="creation_time", insertable=false, updatable=false)
	private Date creationTime;
	
	@Column(name="modification_time", insertable=false, updatable=false)
	private Date modificationTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReadlistId() {
		return readlistId;
	}

	public void setReadlistId(Integer readlistId) {
		this.readlistId = readlistId;
	}

	public Readlist getReadlist() {
		return readlist;
	}

	public void setReadlist(Readlist readlist) {
		this.readlist = readlist;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
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

}
