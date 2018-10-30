package com.learning.readers.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.learning.readers.dao.IReaderGroupDAO;
import com.learning.readers.entity.GroupBook;
import com.learning.readers.entity.ReaderGroup;
import com.learning.readers.entity.User;
import com.learning.readers.model.ReaderGroupOverviewModel;

public class ReaderGroupDAOTestCase {

	private static AnnotationConfigApplicationContext context;
	private static IReaderGroupDAO readerGroupDAO;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("com.learning.readers");
		context.refresh();

		readerGroupDAO = context.getBean("readerGroupDAO", IReaderGroupDAO.class);
	}
	
	//@Test
	public void createNewReaderGroup() {
		
		Integer newId = readerGroupDAO.create("Friends", 2);
		assertEquals("Created Successfully", (Integer)1, newId);
	}
	
	//@Test
	public void addMembersToExistingGroup() {
		
		List<Integer> groupMemberIds = new ArrayList<>();
		groupMemberIds.add(13);
		groupMemberIds.add(14);
		groupMemberIds.add(15);
		
		readerGroupDAO.addMembers(1, groupMemberIds);
	}
	
	//@Test
	public void createGroupWithMembers() {
		
		List<Integer> groupMemberIds = new ArrayList<>();
		groupMemberIds.add(13);
		groupMemberIds.add(14);
		groupMemberIds.add(15);
		
		assertEquals("Created Successfully.", (Integer)2, readerGroupDAO.create("Family", 2, groupMemberIds));
	}
	
	//@Test
	public void getMembers() {
	
		List<User> members = readerGroupDAO.getMembers(1, null);
		members.forEach(System.out::println);
		
		assertEquals("Done", 3, members.size());
	}
	
	//@Test
	public void exist() {
		
		boolean exists = readerGroupDAO.exists(2, "Friend");
		assertEquals("Done", false, exists);
	}
	
	//@Test
	public void listCreatedByMe() {
		
		List<ReaderGroupOverviewModel> createdByMeList = readerGroupDAO.createdByMe(2, null, null);
		createdByMeList.forEach(System.out::println);
		assertEquals("Done", 3, createdByMeList.size());
	}
	
	//@Test
	public void fullyLoadedGroupDetails() {
		
		ReaderGroup readerGroup = readerGroupDAO.groupDetailsFullLoaded(5, 2, true);
		System.out.println(readerGroup);
		
		System.out.println("Members : ");
		readerGroup.getMembers().forEach(System.out::println);
		System.out.println("Books : ");
		readerGroup.getBooks().forEach(System.out::println);
		
		assertEquals("Fetched successfully", (Integer)5, readerGroup.getId());
	}
	
	//@Test
	public void getGroupBook() {
		
		GroupBook groupBook = readerGroupDAO.getGroupBook(4, 1, true);
		System.out.println(groupBook);
		System.out.println(groupBook.getBook());
		System.out.println(groupBook.getBook().getPublication());
		System.out.println(groupBook.getBook().getAuthors());
		System.out.println(groupBook.getBook().getSource());
		assertEquals("Got successfully", (Integer)14, groupBook.getBookId());
	}
}
