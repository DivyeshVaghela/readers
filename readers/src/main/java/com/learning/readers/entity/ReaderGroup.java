package com.learning.readers.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="reader_group")
@DynamicInsert
@DynamicUpdate
public class ReaderGroup {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name; 
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private User creator;
	
	@Column(name="user_id")
	private Integer creatorId;
	
	@OneToMany(mappedBy="group", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<GroupMember> members = new HashSet<>();
	
	@OneToMany(mappedBy="group", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<GroupBook> books = new HashSet<>();
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
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

	/*public List<GroupMember> getMembers() {
		return members;
	}

	public void setMembers(List<GroupMember> members) {
		this.members = members;
	}*/

	
	public Set<GroupBook> getBooks() {
		return books;
	}

	public Set<GroupMember> getMembers() {
		return members;
	}

	public void setMembers(Set<GroupMember> members) {
		this.members = members;
	}

	public void setBooks(Set<GroupBook> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "ReaderGroup [id=" + id + ", name=" + name + ", creatorId=" + creatorId + ", enabled=" + enabled
				+ ", creationTime=" + creationTime + ", modificationTime=" + modificationTime + "]";
	}

	/*public List<GroupBook> getBooks() {
		return books;
	}

	public void setBooks(List<GroupBook> books) {
		this.books = books;
	}*/
	
	
	
}
