package com.learning.readers.dao;

import java.util.List;

import com.learning.readers.entity.Readlist;
import com.learning.readers.entity.ReadlistBook;
import com.learning.readers.model.ReadlistOverviewModel;
import com.learning.readers.util.SortOrder;

public interface IReadlistDAO {

	Integer create(Readlist readlist);
	Integer create(Readlist readlist, List<Integer> bookIds);
	
	Integer addBook(int readlistId, int bookId);
	void addBooks(int readlistId, List<Integer> bookIds);
	
	boolean exists(int creatorId, String readlistName);
	boolean existsAnother(int creatorId, String readlistName, int exceptReadlistId);
	
	List<ReadlistOverviewModel> list(int creatorId, String orderByField, SortOrder order, Integer firstResult, Integer limit);
	List<ReadlistBook> getReadlistBooks(int readlistId, Boolean enabled);
	
	Readlist getById(int readlistId);
	Readlist getFullyloaded(int readlistId, int creatorId);
	
	void updateBasicDetails(Readlist readlist);
	void updateBooks(int readlistId, List<Integer> bookIds);
	void update(Readlist readlist, List<Integer> bookIds);
	
	int remove(int readlistId);
}
