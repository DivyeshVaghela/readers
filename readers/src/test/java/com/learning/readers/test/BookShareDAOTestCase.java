package com.learning.readers.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.learning.readers.dao.IBookShareDAO;
import com.learning.readers.entity.BookShare;
import com.learning.readers.model.BookOverviewModel;
import com.learning.readers.util.SortOrder;

public class BookShareDAOTestCase {

	private static AnnotationConfigApplicationContext context;
	private static IBookShareDAO bookShareDAO;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("com.learning.readers");
		context.refresh();
		
		bookShareDAO = context.getBean("bookShareDAO", IBookShareDAO.class);
	}
	
	//@Test
	public void shareBookTest() {
		
		Integer shareId = bookShareDAO.share(2, 14, 5, null);
		assertEquals("Shared Successfully", (Integer)1, shareId);
	}
	
	//@Test
	public void sharedToMe() {
		
		List<BookOverviewModel> sharedToMeList = bookShareDAO.sharedToMe(14, "creationTime", SortOrder.ASC, 0, 5);
		sharedToMeList.forEach(System.out::println);
		assertEquals("List got successfully", 2, sharedToMeList.size());
	}
	
	//@Test
	public void findById() {
		
		BookShare bookShare = bookShareDAO.findById(1, 14);
		System.out.println("Sender : " + bookShare.getSender());
		System.out.println("Receiver : " + bookShare.getReceiver());
		System.out.println("Book : " + bookShare.getBook());
		System.out.println("Status : " + bookShare.getAction());
		
		assertEquals("Got successfully", (Integer)1, bookShare.getId());
	}
	
	@Test
	public void shareHistory() {
		
		List<BookOverviewModel> shareHistory = bookShareDAO.shareHistory(2, "creationTime", SortOrder.ASC, null, null);
		shareHistory.forEach(System.out::println);
		assertEquals("Share history got successfully", 7, shareHistory.size()); 
	}
}
