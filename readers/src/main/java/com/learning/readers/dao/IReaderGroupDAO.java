package com.learning.readers.dao;

import java.util.List;

import com.learning.readers.entity.Book;
import com.learning.readers.entity.GroupBook;
import com.learning.readers.entity.GroupMember;
import com.learning.readers.entity.ReaderGroup;
import com.learning.readers.entity.User;
import com.learning.readers.model.ReaderGroupIdNameModel;
import com.learning.readers.model.ReaderGroupOverviewModel;
import com.learning.readers.util.SortOrder;

public interface IReaderGroupDAO {

	boolean exists(int creatorId, String readerGroupName);
	
	Integer create(ReaderGroup readerGroup, List<Integer> memberIds);
	Integer create(String groupName, int creatorId);
	//Integer createWithMembers(String groupName, int creatorId, List<Integer> memberIds);
	Integer create(String groupName, int creatorId, List<Integer> memberIds);
	Integer create(String groupName, int creatorId, List<Integer> memberIds, List<Integer> bookIds);
	
	void addMembers(int readerGroupId, List<Integer> memberIds);
	
	void addBooks(int readerGroupId, List<Integer> bookIds);
	void addBook(int readerGroupId, int bookId);

	void updateBooks(int readerGroupId, List<Integer> bookIds);
	void updateMembers(int readerGroupId, List<Integer> memberIds);
	
	List<ReaderGroupOverviewModel> createdByMe(int creatorId, String orderByField, SortOrder order);
	List<ReaderGroupIdNameModel> createdByMeNames(int creatorId, String orderByField, SortOrder order);
	
	List<ReaderGroupOverviewModel> groupsByMember(int memberId, String orderByField, SortOrder order);
	ReaderGroup groupDetailsFullLoaded(int readerGroupId, int creatorId, Boolean enabled);
	ReaderGroup groupDetailsFullLoaded(int readerGroupId, Boolean enabled);
	
	List<User> getMembers(int readerGroupId, Boolean enabled);
	List<GroupMember> getGroupMembers(int readerGroupId, Boolean enabled);
	List<Book> getBooks(int readerGroupId, Boolean enabled);
	List<GroupBook> getGroupBooks(int readerGroupId, Boolean enabled);
	
	GroupBook getGroupBook(int groupBookId, int groupId, Boolean enabled);
	
	boolean isActiveMember(int readerGroupId, int memberId);
	boolean existsBook(int readerGroupId, int bookId);
}
