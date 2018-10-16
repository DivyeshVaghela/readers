package com.learning.readers.dao;

import java.util.List;

import com.learning.readers.entity.Book;
import com.learning.readers.model.BookDetailsModel;
import com.learning.readers.model.BookOverviewModel;
import com.learning.readers.util.SortOrder;

public interface IBookDAO {

	Integer create(Book book);
	
	//BookDetailsModel findById(int bookId, int userId, boolean isLazyLoaded);
	
	Book findById(int bookId, int userId, boolean isLazyLoaded);
	List<Book> list();
	List<Book> list(int userId);
	
	List<BookOverviewModel> list(int userId, String orderByField, SortOrder sortOrder, int firstResult, int limit);
	
	void update(Book book);
}
