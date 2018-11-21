package com.learning.readers.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.learning.readers.dao.IAuthorDAO;
import com.learning.readers.dao.IBookDAO;
import com.learning.readers.dao.IBookTypeDAO;
import com.learning.readers.dao.IPublicationDAO;
import com.learning.readers.dao.IReadStatusDAO;
import com.learning.readers.dao.IUserDAO;
import com.learning.readers.entity.Author;
import com.learning.readers.entity.Book;
import com.learning.readers.entity.BookSource;
import com.learning.readers.model.BookOverviewModel;

public class BookDAOTestCase {

	private static AnnotationConfigApplicationContext context;
	private static IBookDAO bookDAO;
	private static IUserDAO userDAO;
	private static IPublicationDAO publicationDAO;
	private static IReadStatusDAO readStatusDAO;
	private static IAuthorDAO authorDAO;
	private static IBookTypeDAO bookTypeDAO;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("com.learning.readers");
		context.refresh();
		
		bookDAO = context.getBean("bookDAO", IBookDAO.class);
		userDAO = context.getBean("userDAO", IUserDAO.class);
		publicationDAO = context.getBean("publicationDAO", IPublicationDAO.class);
		readStatusDAO = context.getBean("readStatusDAO", IReadStatusDAO.class);
		authorDAO = context.getBean("authorDAO", IAuthorDAO.class);
		bookTypeDAO = context.getBean("bookTypeDAO", IBookTypeDAO.class);
	}
	
	//@Test
	public void listAllBooks() {
		
		List<Book> list = bookDAO.list();
		for (Book book : list) {
			System.out.println(book);
		}
		assertEquals("List got successfully.", 1, list.size());
	}
	
	@Test
	public void listAllBookByUser() {
		
		List<Book> list = bookDAO.list(2);
		for (Book book : list) {
			System.out.println(book);
			//System.out.println(book.getReaderID());
		}
		assertEquals("List got successfully", 8, list.size());
	}
	
	//@Test
	public void createBook() {
		
		Book book = new Book();
		
		book.setName("Testing");
		/*book.setEdition("1st");
		book.setDetails("This is just for testing purpose");
		book.setReader(userDAO.findByUsername("shankar"));
		book.setPublication(publicationDAO.findByName("Wiley India"));
		book.setStatus(readStatusDAO.findByName("Want to read"));
		*/
		Integer newBookId = bookDAO.create(book);
		assertEquals("created successfully", (Integer)2, newBookId);
	}
	
	//@Test
	public void createBookWithAuthor() {
		
		Book book = new Book();
		/*book.setName("Testing");
		book.setEdition("1st");
		book.setDetails("This is just for testing purpose");
		book.setReader(userDAO.findByUsername("shankar"));
		book.setPublication(publicationDAO.findByName("Wiley India"));
		book.setStatus(readStatusDAO.findByName("Want to read"));
		*/
		book.getAuthors().addAll(authorDAO.findByField("firstName", "Peter"));		
		
		Integer newBookId = bookDAO.create(book);
		assertEquals("created successfully", (Integer)3, newBookId);
	}
	
	//@Test
	public void createBookWithNewAuthor() {
		
		Book book = new Book();
		/*book.setName("Testing");
		book.setEdition("1st");
		book.setDetails("This is just for testing purpose");
		book.setReader(userDAO.findByUsername("shankar"));
		book.setPublication(publicationDAO.findByName("Wiley India"));
		book.setStatus(readStatusDAO.findByName("Want to read"));
		*/
		Author author = new Author();
		author.setFirstName("Gagan");
		author.setLastName("Mehta");
		author.setDetails("This is a test author");
		
		book.getAuthors().add(author);
		
		Integer newBookId = bookDAO.create(book);
		assertEquals("created successfully", (Integer)4, newBookId);
	}
	
	//@Test
	public void createBookWithSource() {
		
		Book book = new Book();
		/*book.setName("Testing");
		book.setEdition("1st");
		book.setDetails("This is just for testing purpose");
		book.setReader(userDAO.findByUsername("shankar"));
		book.setPublication(publicationDAO.findByName("Wiley India"));
		book.setStatus(readStatusDAO.findByName("Want to read"));
		*/
		BookSource bookSource = new BookSource();
		bookSource.setType(bookTypeDAO.findByValue("Physical Copy"));
		bookSource.setValue("In my desk at home");
		bookSource.setBook(book);
		
		book.setSource(bookSource);
		
		Integer newBookId = bookDAO.create(book);
		assertEquals("created successfully", (Integer)8, newBookId);
	}
	
	//@Test
	public void createBookWithReadDetails() {
		
		Book book = new Book();
		/*book.setName("Testing");
		book.setEdition("1st");
		book.setDetails("This is just for testing purpose");
		book.setReader(userDAO.findByUsername("shankar"));
		book.setPublication(publicationDAO.findByName("Wiley India"));
		book.setStatus(readStatusDAO.findByName("Want to read"));
		
		ReadDetail readDetail = new ReadDetail();
		readDetail.setRating(4);
		readDetail.setReview("A good book for beginners");
		readDetail.setBook(book);
		
		book.setReadDetails(readDetail);
		*/
		Integer newBookId = bookDAO.create(book);
		assertEquals("created successfully", (Integer)8, newBookId);
	}
	
	//@Test
	public void listBookOverview() {
		
		List<BookOverviewModel> list = bookDAO.list(null, null, 0, 10, null);
		assertEquals("List got successfully", 7, list.size());
	}
	
	/*@Test
	public void findByIdFullyLoaded() {
		
		BookDetailsModel model = bookDAO.findById(16, 2, true);
		System.out.println(model);
		assertEquals("Successfully got", (Integer)16, model.getId());
	}*/
	
	//@Test
	public void findBookDeatilsById() {
		
		Book book = bookDAO.findById(14, 2, true);
		System.out.println(book);
		
		System.out.println(book.getPublication());
		System.out.println(book.getSource().getType());
		System.out.println(book.getSource());
		//System.out.println(book.getReadDetails());
		book.getAuthors().forEach(System.out::println);
		assertEquals("Successfully got", (Integer)14, book.getId());
	}
	
	//@Test
	public void getWishList() {
		
		List<BookOverviewModel> wishList = bookDAO.list(2, 1, null, null, 0, 0);
		wishList.forEach(bookOverviewModel -> {
			System.out.println(bookOverviewModel.getFullName());
		});
		assertEquals("Done", 2, wishList.size());
	}
}
