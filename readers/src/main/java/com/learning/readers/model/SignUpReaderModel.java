package com.learning.readers.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.learning.readers.validator.EqualFields;

@EqualFields(
		baseField = "password", 
		matchField = "confirmPassword",
		message="Password and Confirm Password doesn't match")
public class SignUpReaderModel {

	@NotBlank(message="Username is mandatory field")
	private String username;
	
	@NotBlank(message="Email is mandatory field")
	@Email(message="Please enter a valid email address")
	private String email;
	
	@NotNull(message="Please select secret question")
	private Integer selectedSecretQuestion;
	
	private List<SecretQuestionModel> secretQuestionList = new ArrayList<>();

	@NotBlank(message="Please mention the answer of the secret question")
	private String secretQuestionAnswer;
	
	@NotBlank(message="Password is mandatory field")
	private String password;
	
	@NotBlank(message="Confirm password is mandatory field")
	private String confirmPassword;
	
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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	/*@Autowired
	IUserDAO userDAO;

	private boolean usernameExists;

	@AssertFalse(message="Username already taken")
	public boolean isUsernameExists() {
		usernameExists = false;
		if (username != null && !username.isEmpty()) {
			usernameExists = userDAO.exists("username", username);
		}
		return usernameExists;
	}
	public void setUsernameExists(boolean usernameExists) {
		this.usernameExists = usernameExists;
	}*/

	/*private boolean passwordMatching;
	
	@AssertTrue(message="Password and Confirm Password doesn't match")
	public boolean isPasswordMatching() {
		if (password==null) {
			passwordMatching = confirmPassword==null;
		} else {
			passwordMatching = password.equals(confirmPassword);
		}
		return passwordMatching;
	}

	public void setPasswordMatching(boolean passwordMatching) {
		this.passwordMatching = passwordMatching;
	}*/
	
	public Integer getSelectedSecretQuestion() {
		return selectedSecretQuestion;
	}
	public void setSelectedSecretQuestion(Integer selectedSecretQuestion) {
		this.selectedSecretQuestion = selectedSecretQuestion;
	}
	public List<SecretQuestionModel> getSecretQuestionList() {
		return secretQuestionList;
	}
	public void setSecretQuestionList(List<SecretQuestionModel> secretQuestionList) {
		this.secretQuestionList = secretQuestionList;
	}
	
	public String getSecretQuestionAnswer() {
		return secretQuestionAnswer;
	}
	public void setSecretQuestionAnswer(String secretQuestionAnswer) {
		this.secretQuestionAnswer = secretQuestionAnswer;
	}

	@Override
	public String toString() {
		return "SignUpReaderModel [username=" + username + ", email=" + email + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + "]";
	}
	
}
