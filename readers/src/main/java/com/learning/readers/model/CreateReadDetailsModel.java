package com.learning.readers.model;

public class CreateReadDetailsModel {

	private Integer id;
	
	private Integer startDate;
	private Integer startMonth;
	private Integer startYear;
	
	private Integer endDate;
	private Integer endMonth;
	private Integer endYear;
	
	private Integer rating;
	private String review;

	public CreateReadDetailsModel() {}
	
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
	@Override
	public String toString() {
		return "CreateReadDetailsModel [id=" + id + ", startDate=" + startDate + ", startMonth=" + startMonth
				+ ", startYear=" + startYear + ", endDate=" + endDate + ", endMonth=" + endMonth + ", endYear="
				+ endYear + ", rating=" + rating + ", review=" + review + "]";
	}
	
}
