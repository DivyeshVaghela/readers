package com.learning.readers.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.learning.readers.dao.IUserDAO;
import com.learning.readers.model.SignUpReaderModel;

@Component
public class SignUpReaderModelValidator implements Validator {
	
	@Autowired
	IUserDAO userDAO;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return SignUpReaderModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		SignUpReaderModel signUpReaderModel = (SignUpReaderModel)target;
		
		//check for email already registered or not
		if (signUpReaderModel.getUsername() != null && !signUpReaderModel.getUsername().isEmpty()) {
			if (userDAO.exists("email", signUpReaderModel.getEmail())) {
				errors.rejectValue("email", null, "This email address is alredy registered");
			}
		}
		
		//check for username already taken or not.
		if (signUpReaderModel.getUsername() != null && !signUpReaderModel.getUsername().isEmpty()) {
			if (userDAO.exists("username", signUpReaderModel.getUsername())) {
				errors.rejectValue("username", null, "Username already taken, please choose any other username");
			}
		}
		
	}

}
