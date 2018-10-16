package com.learning.readers.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import com.learning.readers.util.GeneralUtil;

public class FileRequiredIfValidator implements ConstraintValidator<FileRequiredIf, Object> {

	private String baseField;
	private String matchField;
	private String value;
	
	@Override
	public void initialize(FileRequiredIf constraintAnnotation) {
		baseField = constraintAnnotation.baseField();
		matchField = constraintAnnotation.matchField();
		value = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		
		boolean isValid = true;
		
		try {
		
			Object baseFieldValue = GeneralUtil.<Object>getFieldValue(object, baseField);
			MultipartFile matchFieldFile = GeneralUtil.<MultipartFile>getFieldValue(object, matchField);
			
			if (baseFieldValue instanceof Integer) {
				baseFieldValue = Integer.toString((Integer)baseFieldValue);
			}
			
			if (value.equals(baseFieldValue)) {
				System.out.println("FileOriginal Name = " +matchFieldFile.getOriginalFilename());
				isValid = (matchFieldFile!=null && matchFieldFile.getOriginalFilename() != null && !matchFieldFile.getOriginalFilename().isEmpty());
			}
			
		} catch (Exception exp) {
			isValid = false;
		}
		
		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context
				.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
				.addPropertyNode(matchField)
				.addConstraintViolation();
		}
		
		return isValid;
	}

}
