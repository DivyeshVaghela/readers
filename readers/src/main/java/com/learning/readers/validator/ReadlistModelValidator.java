package com.learning.readers.validator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.learning.readers.dao.IReadlistDAO;
import com.learning.readers.model.CreateReadlistModel;
import com.learning.readers.model.UserModel;

@Component
public class ReadlistModelValidator implements Validator {

	@Autowired
	private IReadlistDAO readlistDAO;
	@Autowired
	private HttpSession httpSession;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CreateReadlistModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors error) {

		CreateReadlistModel createReadlistModel = (CreateReadlistModel)target;
		
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		
		if (createReadlistModel.getId() == null) {
			if (createReadlistModel.getName() != null && readlistDAO.exists(userModel.getUserId(), createReadlistModel.getName())){
				error.rejectValue("name", null, "A readlist already exists with this name, choose different name");
			}
		} else {
			if (createReadlistModel.getName() != null && readlistDAO.existsAnother(userModel.getUserId(), createReadlistModel.getName(), createReadlistModel.getId())){
				error.rejectValue("name", null, "A readlist already exists with this name, choose different name");
			}
		}
	}

}
