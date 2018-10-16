package com.learning.readers.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.learning.readers.dao.IBookSourceDAO;
import com.learning.readers.model.BookSourceValueModel;

public class BookSourceDAOTestCase {

	private static AnnotationConfigApplicationContext context;
	private static IBookSourceDAO bookSourceDAO;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("com.learning.readers");
		context.refresh();
		
		bookSourceDAO = context.getBean("bookSourceDAO", IBookSourceDAO.class);
	}
	
	@Test
	public void getAll() {
		
		List<BookSourceValueModel> list = bookSourceDAO.listNames();
		list.forEach(System.out::println);
		assertEquals("Got successfully", 2, list.size());
	}
}
