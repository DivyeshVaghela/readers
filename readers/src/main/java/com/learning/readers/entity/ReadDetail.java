package com.learning.readers.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="read_detail")
public class ReadDetail {

	@Id
	@GeneratedValue(generator="gen")
	@GenericGenerator(name="gen", strategy="foreign", parameters=@Parameter(name = "property", value = "bookUser"))
	@Column(name="id")
	private Integer id;
	
	@Column(name="start_date")
	private Integer startDate;
	
	@Column(name="start_month")
	private Integer startMonth;
	
	@Column(name="start_year")
	private Integer startYear;
	
	@Column(name="end_date")
	private Integer endDate;
	
	@Column(name="end_month")
	private Integer endMonth;
	
	@Column(name="end_year")
	private Integer endYear;
	
	@Column(name="place")
	private String place;
	
	@Column(name="rating")
	private Integer rating;
	
	@Column(name="review")
	private String review;
	
	@Column(name="creation_time", insertable=false, updatable=false)
	private Date creationTime;
	
	@Column(name="modification_time", insertable=false, updatable=false)
	private Date modificationTime;

	/*@OneToOne
	@PrimaryKeyJoinColumn
	private Book book;*/
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private BookUser bookUser;
	
	
	public ReadDetail() {}

	public ReadDetail(Integer id, Integer startDate, Integer startMonth, Integer startYear, Integer endDate,
			Integer endMonth, Integer endYear, String place, Integer rating, String review, Date creationTime,
			Date modificationTime) {
		this.id = id;
		this.startDate = startDate;
		this.startMonth = startMonth;
		this.startYear = startYear;
		this.endDate = endDate;
		this.endMonth = endMonth;
		this.endYear = endYear;
		this.place = place;
		this.rating = rating;
		this.review = review;
		this.creationTime = creationTime;
		this.modificationTime = modificationTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStartDate() {
		return startDate;
	}

	public void setStartDate(Integer startDate) {
		this.startDate = startDate;
	}

	public Integer getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}

	public Integer getStartYear() {
		return startYear;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getEndDate() {
		return endDate;
	}

	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}

	public Integer getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(Integer endMonth) {
		this.endMonth = endMonth;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
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

	/*public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}*/

	public BookUser getBookUser() {
		return bookUser;
	}

	public void setBookUser(BookUser bookUser) {
		this.bookUser = bookUser;
	}
	
	
	@Override
	public String toString() {
		return "ReadDetail [id=" + id + ", startDate=" + startDate + ", startMonth=" + startMonth + ", startYear="
				+ startYear + ", endDate=" + endDate + ", endMonth=" + endMonth + ", endYear=" + endYear + ", place="
				+ place + ", rating=" + rating + ", review=" + review + ", creationTime=" + creationTime
				+ ", modificationTime=" + modificationTime + "]";
	}

	
	
}
