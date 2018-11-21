package com.learning.readers.dao;

import java.util.List;
import java.util.Map;

import com.learning.readers.entity.Book;
import com.learning.readers.model.BookDetailsModel;
import com.learning.readers.model.BookNameEditionModel;
import com.learning.readers.model.BookOverviewModel;
import com.learning.readers.util.FieldNameValue;
import com.learning.readers.util.SortOrder;

public interface IBookDAO {

	Integer create(Book book);
	
	//BookDetailsModel findById(int bookId, int userId, boolean isLazyLoaded);
	
	Book findById(int bookId, int userId, boolean isLazyLoaded);
	List<Book> list();
	List<Book> list(int userId);
	
	List<BookOverviewModel> list(String orderByField, SortOrder sortOrder, int firstResult, int limit, List<FieldNameValue<String, Object>> eqRestrictions);
	List<BookOverviewModel> list(int userId, int readStatusId, String orderByField, SortOrder sortOrder, Integer firstResult, Integer limit);
	//List<BookOverviewModel> getWishList(int userId, String orderByField, SortOrder sortOrder, int firstResult, int limit);

	List<BookNameEditionModel> listMyBooksNameEdition(int userId, String orderByField, SortOrder sortOrder, Integer firstResult, Integer limit);
	
	void update(Book book);
	
	int remove(int bookId);
}
