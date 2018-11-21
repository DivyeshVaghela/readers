package com.learning.readers.model;

public class SecretQuestionModel {

	private Integer id;
	private String question;
	
	public SecretQuestionModel() {}
	
	public SecretQuestionModel(Integer id, String question) {
		this.id = id;
		this.question = question;
	}

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
	@Override
	public String toString() {
		return "SecretQuestionModel [id=" + id + ", question=" + question + "]";
	}
	
	
}
