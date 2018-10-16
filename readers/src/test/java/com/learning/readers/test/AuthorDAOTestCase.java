package com.learning.readers.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.learning.readers.dao.IAuthorDAO;
import com.learning.readers.model.AuthorNameModel;

public class AuthorDAOTestCase {

	private static AnnotationConfigApplicationContext context;
	private static IAuthorDAO authorDAO;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("com.learning.readers");
		context.refresh();
		
		authorDAO = context.getBean("authorDAO", IAuthorDAO.class);
	}
	
	@Test
	public void listAuthorNameModel() {
		
		List<AuthorNameModel> list = authorDAO.listAutherNames();
		for (AuthorNameModel author : list) {
			System.out.println(author.getFullName());
		}
		assertEquals("Done", 4, list.size());
	}
}
