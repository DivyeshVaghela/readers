package com.learning.readers.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.constraintvalidators.hv.NotBlankValidator;

import com.learning.readers.util.GeneralUtil;

public class NotBlankIfValidator implements ConstraintValidator<NotBlankIf, Object> {

	private String baseField;
	private String matchField;
	private String value;
	
	@Override
	public void initialize(NotBlankIf constraintAnnotation) {
		
		baseField = constraintAnnotation.baseField();
		matchField = constraintAnnotation.matchField();
		value = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		
		boolean isValid = true;
		
		try {
			Object baseFieldValue = GeneralUtil.<Object>getFieldValue(object, baseField);
			String matchFieldValue = GeneralUtil.<String>getFieldValue(object, matchField);
			
			if (baseFieldValue instanceof Integer) {
				baseFieldValue = Integer.toString((Integer)baseFieldValue);
			}
			
			if (value.equals(baseFieldValue)) {
				isValid = new NotBlankValidator().isValid(matchFieldValue, context);
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
