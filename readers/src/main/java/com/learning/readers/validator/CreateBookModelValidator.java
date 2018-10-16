package com.learning.readers.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.learning.readers.model.CreateBookModel;
import com.learning.readers.model.CreateBookSourceModel;

@Component
public class CreateBookModelValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CreateBookModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		CreateBookModel model = (CreateBookModel)target;
		
		if (model.getId() == null) { //If it is used for creating new book
		
			CreateBookSourceModel bookSource = model.getBookSource();
			if (bookSource != null) {
				Integer bookTypeId = model.getBookSource().getBookTypeId();
				if (bookTypeId != null) {
					if (bookTypeId == 2) { //If its an eBook
						MultipartFile bookFile = model.getBookSource().getBookFile();
						if (bookFile == null || bookFile.getOriginalFilename().isEmpty()) {
							errors.rejectValue("bookSource.bookFile", null, "Please select book file to upload, if it's an eBook");
						}
						
					} else if (bookTypeId == 3) { //If its and eBook URL
						String bookURL = model.getBookSource().getValue();
						if (bookURL == null || bookURL.isEmpty()) {
							errors.rejectValue("bookSource.value", null, "eBook URL is mandatory if it's an eBook");
						}
						//TODO: Check whether its a proper URL or not.
					}
				}
			}
			
			Integer readStatus = model.getReadStatus();
			if (readStatus == null) {
				errors.rejectValue("readStatus", null, "Read status is mandatory field");
			}
		}
		else { //If it is used for updating a book
			
		}
		
	}

}
