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
@Table(name="group_member")
@DynamicInsert
@DynamicUpdate
public class GroupMember {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="group_id", insertable=false, updatable=false)
	private ReaderGroup group;
	
	@Column(name="group_id")
	private Integer groupId;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private User member;
	
	@Column(name="user_id")
	private Integer memberId;
	
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

	public ReaderGroup getGroup() {
		return group;
	}

	public void setGroup(ReaderGroup group) {
		this.group = group;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public User getMember() {
		return member;
	}

	public void setMember(User member) {
		this.member = member;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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

	@Override
	public String toString() {
		return "GroupMember [id=" + id + ", groupId=" + groupId + ", memberId=" + memberId + ", enabled=" + enabled
				+ ", creationTime=" + creationTime + ", modificationTime=" + modificationTime + "]";
	}

	
}
