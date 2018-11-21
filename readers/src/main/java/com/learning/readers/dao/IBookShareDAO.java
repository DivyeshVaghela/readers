package com.learning.readers.dao;

import java.util.List;

import com.learning.readers.entity.BookShare;
import com.learning.readers.entity.GroupBook;
import com.learning.readers.model.BookOverviewModel;
import com.learning.readers.util.SortOrder;

public interface IBookShareDAO {

	Integer share(int senderId, int receiverId, int bookId, Integer actionId);
	void share(List<BookShare> bookShareList);
	void shareToGroups(List<GroupBook> groupBookList);
	
	List<BookOverviewModel> sharedToMe(int receiverId, String orderByField, SortOrder sortOrder, Integer firstResult, Integer limit);
	List<BookOverviewModel> shareHistory(int userId, String orderByField, SortOrder sortOrder, Integer firstResult, Integer limit);
	
	BookShare findById(int bookShareId, int receiverId);
	
	void takeShareAction(int bookShareId, int actionId);
}
