package com.learning.readers.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.learning.readers.util.GeneralUtil;

public class EqualFieldsValidator implements ConstraintValidator<EqualFields, Object> {

	private String baseField;
	private String matchField;

	@Override
	public void initialize(EqualFields constraintAnnotation) {
		baseField = constraintAnnotation.baseField();
		matchField = constraintAnnotation.matchField();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {

		boolean isValid = true;

		try {
			Object baseFieldValue = GeneralUtil.<Object>getFieldValue(object, baseField);
			Object matchFieldValue = GeneralUtil.<Object>getFieldValue(object, matchField);

			isValid = (baseFieldValue != null && baseFieldValue.equals(matchFieldValue));

		} catch (Exception exp) {
			isValid = false;
		}

		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode(matchField).addConstraintViolation();
		}

		return isValid;
	}

	/*
	 * public Object getFieldValue(Object object, String fieldName) throws
	 * Exception{
	 * 
	 * Class<?> clazz = object.getClass(); Field field =
	 * clazz.getDeclaredField(fieldName); field.setAccessible(true); return
	 * field.get(object); }
	 */
}
