package com.learning.readers.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { NotBlankIfValidator.class })
public @interface NotBlankIf {

	String message() default "{com.learning.readers.NotBlankIf.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	String baseField();
	String matchField();
	
	String value();
}
