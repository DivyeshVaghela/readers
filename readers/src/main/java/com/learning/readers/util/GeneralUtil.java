package com.learning.readers.util;

import java.lang.reflect.Field;

public class GeneralUtil {

	/*public static Object getFieldValue(Object object, String fieldName) throws Exception {

		Class<?> clazz = object.getClass();
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(object);
	}*/
	
	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Object object, String fieldName) throws Exception {
		
		Class<?> clazz = object.getClass();
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		Object fieldValue = field.get(object);
		return (T)fieldValue;
	}
	
	public static boolean isNullOrEmpty(String testString) {
		return testString == null || testString.isEmpty();
	}
	
	public static String nullOrString(String testString) {
		if (isNullOrEmpty(testString))
			return null;
		return testString;
	}
}
