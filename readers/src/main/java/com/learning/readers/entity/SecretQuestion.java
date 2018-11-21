package com.learning.readers.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="secret_question")
@DynamicInsert
@DynamicUpdate
public class SecretQuestion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="question")
	private String question;
	
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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
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

	@Override
	public String toString() {
		return "SecretQuestion [id=" + id + ", question=" + question + ", creationTime=" + creationTime
				+ ", modificationTime=" + modificationTime + "]";
	}
	
}
