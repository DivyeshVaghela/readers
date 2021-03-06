package com.learning.readers.entity;

import java.util.ArrayList;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="user")
@DynamicInsert
@DynamicUpdate
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="email")
	private String email;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="secret_question_id", insertable=false, updatable=false)
	private SecretQuestion secretQuestion;
	
	@Column(name="secret_question_id")
	private Integer secretQuestionId;
	
	@Column(name="secret_answer")
	private String secretAnswer;
	
	@Column(name="password")
	private String password;
	
	@Column(name="enabled", nullable=true)
	private Boolean enabled;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="user_role",
			joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
			inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")})
	private Set<Role> roles = new HashSet<>();;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<Book> books = new ArrayList<>();
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<BookUser> bookUsers = new ArrayList<>();
	
	@OneToMany(mappedBy="sender", fetch=FetchType.LAZY)
	private List<BookShare> sharedByMeBooks = new ArrayList<>();
	
	@OneToMany(mappedBy="receiver", fetch=FetchType.LAZY)
	private List<BookShare> sharedToMeBooks = new ArrayList<>();
	
	public User() { }

	public User(Integer id, String username, String email, String password, Boolean enabled, Set<Role> roles) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}

	public User(User user) {
		this(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getEnabled(), user.getRoles());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		this.getRoles().add(role);
		role.getUsers().add(this);
	}
	
	public SecretQuestion getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(SecretQuestion secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public List<BookUser> getBookUsers() {
		return bookUsers;
	}

	public void setBookUsers(List<BookUser> bookUsers) {
		this.bookUsers = bookUsers;
	}

	public List<BookShare> getSharedByMeBooks() {
		return sharedByMeBooks;
	}

	public void setSharedByMeBooks(List<BookShare> sharedByMeBooks) {
		this.sharedByMeBooks = sharedByMeBooks;
	}

	public List<BookShare> getSharedToMeBooks() {
		return sharedToMeBooks;
	}

	public void setSharedToMeBooks(List<BookShare> sharedToMeBooks) {
		this.sharedToMeBooks = sharedToMeBooks;
	}

	public Integer getSecretQuestionId() {
		return secretQuestionId;
	}

	public void setSecretQuestionId(Integer secretQuestionId) {
		this.secretQuestionId = secretQuestionId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", enabled=" + enabled + "]";
	}
	
}
