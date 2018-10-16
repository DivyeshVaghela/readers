package com.learning.readers.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.learning.readers.dao.IBookTypeDAO;
import com.learning.readers.model.BookTypeValueModel;

public class BookTypeDAOTestCase {

	private static AnnotationConfigApplicationContext context;
	private static IBookTypeDAO bookTypeDAO;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("com.learning.readers");
		context.refresh();
	
		bookTypeDAO = context.getBean("bookTypeDAO", IBookTypeDAO.class);
	}
	
	@Test
	public void listValues() {
		
		List<BookTypeValueModel> list = bookTypeDAO.listValues();
		list.forEach(System.out::println);
		assertEquals("Successfully got", 2, list.size());
	}
}
