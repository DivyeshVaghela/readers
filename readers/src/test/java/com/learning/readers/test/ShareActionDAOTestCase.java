package com.learning.readers.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.learning.readers.dao.IShareActionDAO;
import com.learning.readers.model.ShareActionValueModel;

public class ShareActionDAOTestCase {

	private static AnnotationConfigApplicationContext context;
	private static IShareActionDAO shareActionDAO;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("com.learning.readers");
		context.refresh();
		
		shareActionDAO = context.getBean("shareActionDAO", IShareActionDAO.class);
	}
	
	@Test
	public void listValue() {
		
		List<ShareActionValueModel> listValues = shareActionDAO.listValues();
		listValues.forEach(System.out::println);
		assertEquals("Successfully got", 3, listValues.size());
		
	}
}
