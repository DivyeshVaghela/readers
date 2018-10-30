package com.learning.readers.validator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.learning.readers.dao.IReaderGroupDAO;
import com.learning.readers.model.CreateReaderGroupModel;
import com.learning.readers.model.UserModel;

@Component
public class ReaderGroupModelValidator implements Validator {

	@Autowired
	IReaderGroupDAO readerGroupName;
	@Autowired
	HttpSession httpSession;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CreateReaderGroupModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		CreateReaderGroupModel model = (CreateReaderGroupModel)target;

		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		
		if (model.getSelectedMemberdId().size() == 0) {
			errors.rejectValue("selectedMemberdId", null, "Please select at least one member");
		}
		
		if (readerGroupName.exists(userModel.getUserId(), model.getName())) {
			errors.rejectValue("name", null, "You have already created a group with this name.");
		}
	}

	
}
