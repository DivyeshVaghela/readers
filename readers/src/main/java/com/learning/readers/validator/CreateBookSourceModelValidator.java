package com.learning.readers.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.learning.readers.model.CreateBookSourceModel;

@Component
public class CreateBookSourceModelValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CreateBookSourceModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		CreateBookSourceModel model = (CreateBookSourceModel)target;
		
		if (target == null) {
			return;
		}
		
		Integer bookTypeId = model.getBookTypeId();
		if (bookTypeId != null) {
			if (bookTypeId == 2) { //If its an eBook
				MultipartFile bookFile = model.getBookFile();
				if (bookFile == null || bookFile.getOriginalFilename().isEmpty()) {
					errors.rejectValue("bookFile", null, "Please select book file to upload, if it's an eBook");
				}
				
			} else if (bookTypeId == 3) { //If its and eBook URL
				String bookURL = model.getValue();
				if (bookURL == null || bookURL.isEmpty()) {
					errors.rejectValue("value", null, "eBook URL is mandatory if it's an eBook");
				}
				//TODO: Check whether its a proper URL or not.
			}
		}
	}

}
