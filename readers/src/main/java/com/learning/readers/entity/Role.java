package com.learning.readers.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="details")
	private String details;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy="roles", fetch=FetchType.EAGER)
	/*@JoinTable(name="user_role", 
			joinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")},
			inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")})*/
	private Set<User> users = new HashSet<>();

	public Role() {}
	
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		this.getUsers().add(user);
		user.getRoles().add(this);
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", details=" + details + "]";
	}
	
	
}
