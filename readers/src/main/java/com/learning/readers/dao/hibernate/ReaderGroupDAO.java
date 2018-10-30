package com.learning.readers.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.FetchType;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.learning.readers.dao.IReaderGroupDAO;
import com.learning.readers.entity.Author;
import com.learning.readers.entity.Book;
import com.learning.readers.entity.BookSource;
import com.learning.readers.entity.GroupBook;
import com.learning.readers.entity.GroupMember;
import com.learning.readers.entity.Publication;
import com.learning.readers.entity.ReaderGroup;
import com.learning.readers.entity.User;
import com.learning.readers.model.CreateReaderGroupModel;
import com.learning.readers.model.ReaderGroupOverviewModel;
import com.learning.readers.util.SortOrder;

@Repository
public class ReaderGroupDAO implements IReaderGroupDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public List<ReaderGroupOverviewModel> groupsByMember(int memberId, String orderByField, SortOrder order){
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ReaderGroupOverviewModel> criteriaQuery = criteriaBuilder.createQuery(ReaderGroupOverviewModel.class);
			
			Root<ReaderGroup> readerGroupRoot = criteriaQuery.from(ReaderGroup.class);
			Join<ReaderGroup, GroupMember> memberJoin = readerGroupRoot.join("members", JoinType.LEFT);
			Join<ReaderGroup, GroupBook> bookJoin = readerGroupRoot.join("books", JoinType.LEFT);

			Subquery<Long> groupMemberSubquery = criteriaQuery.subquery(Long.class);
			Root<GroupMember> groupMemberRoot = groupMemberSubquery.from(GroupMember.class);
			groupMemberSubquery
				.select(criteriaBuilder.count(groupMemberRoot))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(groupMemberRoot.<Integer>get("groupId"), readerGroupRoot.<Integer>get("id")),
						criteriaBuilder.equal(groupMemberRoot.<Boolean>get("enabled"), true)));
			
			Subquery<Long> groupBookSubquery = criteriaQuery.subquery(Long.class);
			Root<GroupBook> groupBookRoot = groupBookSubquery.from(GroupBook.class);
			groupBookSubquery
				.select(criteriaBuilder.count(groupBookRoot))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(groupBookRoot.<Integer>get("groupId"), readerGroupRoot.<Integer>get("id")),
						criteriaBuilder.equal(groupBookRoot.<Boolean>get("enabled"), true)));
			
			Subquery<GroupMember> isMemberSubquery = criteriaQuery.subquery(GroupMember.class);
			Root<GroupMember> isGroupMemberRoot = isMemberSubquery.from(GroupMember.class);
			isMemberSubquery
				.select(isGroupMemberRoot)
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(isGroupMemberRoot.<Integer>get("groupId"), readerGroupRoot.<Integer>get("id")),
						criteriaBuilder.equal(isGroupMemberRoot.<Integer>get("memberId"), memberId),
						criteriaBuilder.equal(isGroupMemberRoot.<Boolean>get("enabled"), true)));
			
			criteriaQuery
				.select(criteriaBuilder.construct(ReaderGroupOverviewModel.class,
					readerGroupRoot.<Integer>get("id"),
					readerGroupRoot.<String>get("name"),
					readerGroupRoot.<Integer>get("creatorId"),
					groupMemberSubquery.getSelection(),
					groupBookSubquery.getSelection()))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(readerGroupRoot.<Boolean>get("enabled"), true)),
						criteriaBuilder.exists(isMemberSubquery))
				.groupBy(readerGroupRoot.<Integer>get("id"));
			
			String orderByField_local = "creationTime";
			if (orderByField != null)
				orderByField_local = orderByField;
			
			SortOrder order_local = SortOrder.DESC;
			if (order != null)
				order_local = order;
			
			if (order_local == SortOrder.ASC) {
				criteriaQuery.orderBy(criteriaBuilder.asc(readerGroupRoot.<Date>get(orderByField_local)));
			} else if (order_local == SortOrder.DESC) {
				criteriaQuery.orderBy(criteriaBuilder.desc(readerGroupRoot.<Date>get(orderByField_local)));
			}
			
			return session.createQuery(criteriaQuery).list();
		});
	}

	@Override
	public List<ReaderGroupOverviewModel> createdByMe(int creatorId, String orderByField, SortOrder order){
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ReaderGroupOverviewModel> criteriaQuery = criteriaBuilder.createQuery(ReaderGroupOverviewModel.class);
			
			Root<ReaderGroup> readerGroupRoot = criteriaQuery.from(ReaderGroup.class);
			Join<ReaderGroup, GroupMember> memberJoin = readerGroupRoot.join("members", JoinType.LEFT);
			Join<ReaderGroup, GroupBook> bookJoin = readerGroupRoot.join("books", JoinType.LEFT);

			Subquery<Long> groupMemberSubquery = criteriaQuery.subquery(Long.class);
			Root<GroupMember> groupMemberRoot = groupMemberSubquery.from(GroupMember.class);
			groupMemberSubquery
				.select(criteriaBuilder.count(groupMemberRoot))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(groupMemberRoot.<Integer>get("groupId"), readerGroupRoot.<Integer>get("id")),
						criteriaBuilder.equal(groupMemberRoot.<Boolean>get("enabled"), true)));
			
			Subquery<Long> groupBookSubquery = criteriaQuery.subquery(Long.class);
			Root<GroupBook> groupBookRoot = groupBookSubquery.from(GroupBook.class);
			groupBookSubquery
				.select(criteriaBuilder.count(groupBookRoot))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(groupBookRoot.<Integer>get("groupId"), readerGroupRoot.<Integer>get("id")),
						criteriaBuilder.equal(groupBookRoot.<Boolean>get("enabled"), true)));
			
			criteriaQuery
				.select(criteriaBuilder.construct(ReaderGroupOverviewModel.class,
					readerGroupRoot.<Integer>get("id"),
					readerGroupRoot.<String>get("name"),
					readerGroupRoot.<Integer>get("creatorId"),
					groupMemberSubquery.getSelection(),
					groupBookSubquery.getSelection()))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(readerGroupRoot.<Integer>get("creatorId"), creatorId),
						criteriaBuilder.equal(readerGroupRoot.<Boolean>get("enabled"), true)))
				.groupBy(readerGroupRoot.<Integer>get("id"));
			
			/*criteriaQuery.select(criteriaBuilder.construct(ReaderGroupOverviewModel.class,
					readerGroupRoot.<Integer>get("id"),
					readerGroupRoot.<String>get("name"),
					criteriaBuilder.count(memberJoin),
					criteriaBuilder.count(bookJoin)))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(readerGroupRoot.<Integer>get("creatorId"), creatorId),
						criteriaBuilder.equal(readerGroupRoot.<Boolean>get("enabled"), true),
						criteriaBuilder.or(
								criteriaBuilder.isNull(memberJoin.<Boolean>get("enabled")),
								criteriaBuilder.equal(memberJoin.<Boolean>get("enabled"), true)),
						criteriaBuilder.or(
								criteriaBuilder.isNull(bookJoin.<Boolean>get("enabled")),
								criteriaBuilder.equal(bookJoin.<Boolean>get("enabled"), true))
						))
				.groupBy(readerGroupRoot.<Integer>get("id"));*/
			
			String orderByField_local = "creationTime";
			if (orderByField != null)
				orderByField_local = orderByField;
			
			SortOrder order_local = SortOrder.DESC;
			if (order != null)
				order_local = order;
			
			if (order_local == SortOrder.ASC) {
				criteriaQuery.orderBy(criteriaBuilder.asc(readerGroupRoot.<Date>get(orderByField_local)));
			} else if (order_local == SortOrder.DESC) {
				criteriaQuery.orderBy(criteriaBuilder.desc(readerGroupRoot.<Date>get(orderByField_local)));
			}
				
			/*
			 CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
			 criteriaQuery.multiselect(readerGroupRoot.<Integer>get("id"),
					readerGroupRoot.<String>get("name"),
					criteriaBuilder.count(readerGroupRoot.join("members", JoinType.LEFT)),
					criteriaBuilder.count(readerGroupRoot.join("books", JoinType.LEFT)))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(readerGroupRoot.<Integer>get("creatorId"), creatorId),
						criteriaBuilder.equal(readerGroupRoot.<Boolean>get("enabled"), true)))
				.groupBy(readerGroupRoot.<Integer>get("id"));
				
			List<Tuple> list = session.createQuery(criteriaQuery).list();
			
			List<ReaderGroupOverviewModel> finalList = list.stream()
				.map(tuple -> new ReaderGroupOverviewModel((Integer)tuple.get(0), (String)tuple.get(1), (Long)tuple.get(2), (Long)tuple.get(3)))
				.collect(Collectors.toList());
			return finalList;*/
			
			return session.createQuery(criteriaQuery).list();
		});
	}

	@Override
	public boolean isActiveMember(int readerGroupId, int memberId) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Boolean> criteriaQuery = criteriaBuilder.createQuery(Boolean.class);
			
			Root<GroupMember> groupMemberRoot = criteriaQuery.from(GroupMember.class);
			criteriaQuery
				.select(criteriaBuilder.literal(true))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(groupMemberRoot.<Integer>get("groupId"), readerGroupId),
						criteriaBuilder.equal(groupMemberRoot.<Integer>get("memberId"), memberId),
						criteriaBuilder.equal(groupMemberRoot.<Boolean>get("enabled"), true)));
			return session.createQuery(criteriaQuery).uniqueResult();
		});
	}
	
	@Override
	public ReaderGroup groupDetailsFullLoaded(int readerGroupId, Boolean enabled){
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ReaderGroup> criteriaQuery = criteriaBuilder.createQuery(ReaderGroup.class);
			
			Root<ReaderGroup> readerGroupRoot = criteriaQuery.from(ReaderGroup.class);
			Join<ReaderGroup, User> creatorJoin = (Join)readerGroupRoot.fetch("creator");
			Join<ReaderGroup, GroupMember> groupMemberJoin = (Join)readerGroupRoot.fetch("members", JoinType.LEFT);
			Join<GroupMember, User> memberJoin = (Join)groupMemberJoin.fetch("member", JoinType.LEFT);
			Join<ReaderGroup, GroupBook> groupBookJoin = (Join)readerGroupRoot.fetch("books", JoinType.LEFT);
			Join<GroupBook, Book> bookJoin = (Join)groupBookJoin.fetch("book", JoinType.LEFT);
			
			criteriaQuery
				.select(readerGroupRoot);
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(readerGroupRoot.<Integer>get("id"), readerGroupId));
			
			boolean enabled_local = true;
			if (enabled != null) {
				enabled_local = enabled;
			}
			
			predicates.add(criteriaBuilder.or(
					criteriaBuilder.equal(readerGroupRoot.<Boolean>get("enabled"), enabled_local),
					criteriaBuilder.isNull(readerGroupRoot.<Boolean>get("enabled"))));
			predicates.add(criteriaBuilder.or(
					criteriaBuilder.equal(creatorJoin.<Boolean>get("enabled"), enabled_local),
					criteriaBuilder.isNull(creatorJoin.<Boolean>get("enabled"))));
			predicates.add(criteriaBuilder.or(
					criteriaBuilder.equal(groupMemberJoin.<Boolean>get("enabled"), enabled_local),
					criteriaBuilder.isNull(groupMemberJoin.<Boolean>get("enabled"))));
			predicates.add(criteriaBuilder.or(
					criteriaBuilder.equal(memberJoin.<Boolean>get("enabled"), enabled_local),
					criteriaBuilder.isNull(memberJoin.<Boolean>get("enabled"))));
			predicates.add(criteriaBuilder.or(
					criteriaBuilder.equal(groupBookJoin.<Boolean>get("enabled"), enabled_local),
					criteriaBuilder.isNull(groupBookJoin.<Boolean>get("enabled"))));
			predicates.add(criteriaBuilder.or(
					criteriaBuilder.equal(bookJoin.<Boolean>get("enabled"), enabled_local),
					criteriaBuilder.isNull(bookJoin.<Boolean>get("enabled"))));
			
			criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));
			
			return session.createQuery(criteriaQuery).uniqueResult();
		});
	}
	
	@Override
	public ReaderGroup groupDetailsFullLoaded(int readerGroupId, int creatorId, Boolean enabled){
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ReaderGroup> criteriaQuery = criteriaBuilder.createQuery(ReaderGroup.class);
			
			Root<ReaderGroup> readerGroupRoot = criteriaQuery.from(ReaderGroup.class);
			Join<ReaderGroup, User> creatorJoin = (Join)readerGroupRoot.fetch("creator");
			Join<ReaderGroup, GroupMember> groupMemberJoin = (Join)readerGroupRoot.fetch("members", JoinType.LEFT);
			Join<GroupMember, User> memberJoin = (Join)groupMemberJoin.fetch("member", JoinType.LEFT);
			Join<ReaderGroup, GroupBook> groupBookJoin = (Join)readerGroupRoot.fetch("books", JoinType.LEFT);
			Join<GroupBook, Book> bookJoin = (Join)groupBookJoin.fetch("book", JoinType.LEFT);
			
			criteriaQuery
				.select(readerGroupRoot);
			
			/*List<Order> orders = new ArrayList<>();
			orders.add(criteriaBuilder.desc(groupMemberJoin.<Date>get("creationTime")));
			orders.add(criteriaBuilder.desc(groupBookJoin.<Date>get("creationTime")));
			orders.add(criteriaBuilder.asc(bookJoin.<String>get("name")));
			
			criteriaQuery
				.orderBy(orders);*/
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(readerGroupRoot.<Integer>get("id"), readerGroupId));
			predicates.add(criteriaBuilder.equal(readerGroupRoot.<Integer>get("creatorId"), creatorId));
			
			boolean enabled_local = true;
			if (enabled != null) {
				enabled_local = enabled;
			}
			
			predicates.add(criteriaBuilder.or(
					criteriaBuilder.equal(readerGroupRoot.<Boolean>get("enabled"), enabled_local),
					criteriaBuilder.isNull(readerGroupRoot.<Boolean>get("enabled"))));
			predicates.add(criteriaBuilder.or(
					criteriaBuilder.equal(creatorJoin.<Boolean>get("enabled"), enabled_local),
					criteriaBuilder.isNull(creatorJoin.<Boolean>get("enabled"))));
			predicates.add(criteriaBuilder.or(
					criteriaBuilder.equal(groupMemberJoin.<Boolean>get("enabled"), enabled_local),
					criteriaBuilder.isNull(groupMemberJoin.<Boolean>get("enabled"))));
			predicates.add(criteriaBuilder.or(
					criteriaBuilder.equal(memberJoin.<Boolean>get("enabled"), enabled_local),
					criteriaBuilder.isNull(memberJoin.<Boolean>get("enabled"))));
			predicates.add(criteriaBuilder.or(
					criteriaBuilder.equal(groupBookJoin.<Boolean>get("enabled"), enabled_local),
					criteriaBuilder.isNull(groupBookJoin.<Boolean>get("enabled"))));
			predicates.add(criteriaBuilder.or(
					criteriaBuilder.equal(bookJoin.<Boolean>get("enabled"), enabled_local),
					criteriaBuilder.isNull(bookJoin.<Boolean>get("enabled"))));
			
			criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));
			
			return session.createQuery(criteriaQuery).uniqueResult();
		});
	}
	
	@Override
	public boolean exists(int creatorId, String readerGroupName) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Boolean> criteriaQuery = criteriaBuilder.createQuery(Boolean.class);
			
			Root<ReaderGroup> readerGroupRoot = criteriaQuery.from(ReaderGroup.class);
			criteriaQuery
				.select(criteriaBuilder.literal(true))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(readerGroupRoot.<Integer>get("creatorId"), creatorId),
						criteriaBuilder.like(
								criteriaBuilder.upper(readerGroupRoot.<String>get("name")), 
								readerGroupName.toUpperCase()),
						criteriaBuilder.equal(readerGroupRoot.<Boolean>get("enabled"), true)));

			List<Boolean> list = session.createQuery(criteriaQuery).list();
			
			if (list == null || list.size() > 0) {
				return true;
			} else {
				return false;
			}
		});
	}
	
	@Override
	@Transactional
	public Integer create(ReaderGroup readerGroup, List<Integer> memberIds) {
		
		if (memberIds != null && memberIds.size() != 0) {
			memberIds.forEach(memberId -> {
				GroupMember groupMember = new GroupMember();
				groupMember.setGroup(readerGroup);
				groupMember.setMemberId(memberId);
				
				readerGroup.getMembers().add(groupMember);
			});
		}
		
		return (Integer)hibernateTemplate.save(readerGroup);
	}
	
	@Override
	public List<User> getMembers(int readerGroupId, Boolean enabled) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

			Root<GroupMember> groupMemberRoot = criteriaQuery.from(GroupMember.class);
			criteriaQuery.select(groupMemberRoot.<User>get("member"));
			
			List<Predicate> predicates = new ArrayList<>();
			
			predicates.add(criteriaBuilder.equal(groupMemberRoot.<Integer>get("groupId"), readerGroupId));
			
			boolean enabled_local = true;
			if (enabled != null) {
				enabled_local = enabled;
			}
			predicates.add(criteriaBuilder.equal(groupMemberRoot.<Boolean>get("enabled"), enabled_local));
			criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));
			
			return session.createQuery(criteriaQuery).list();
		});
	}
	
	@Override
	public List<Book> getBooks(int readerGroupId, Boolean enabled) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);

			Root<GroupBook> groupBookRoot = criteriaQuery.from(GroupBook.class);
			criteriaQuery.select(groupBookRoot.<Book>get("book"));
			
			List<Predicate> predicates = new ArrayList<>();
			
			predicates.add(criteriaBuilder.equal(groupBookRoot.<Integer>get("groupId"), readerGroupId));
			
			boolean enabled_local = true;
			if (enabled != null) {
				enabled_local = enabled;
			}
			predicates.add(criteriaBuilder.equal(groupBookRoot.<Boolean>get("enabled"), enabled_local));
			criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));
			
			return session.createQuery(criteriaQuery).list();
		});
	}
	
	@Override
	@Transactional
	public Integer create(String groupName, int creatorId) {
		
		ReaderGroup readerGroup = new ReaderGroup();
		readerGroup.setName(groupName);
		readerGroup.setCreatorId(creatorId);
		
		return (Integer)hibernateTemplate.save(readerGroup);
	}
	
	@Override
	@Transactional
	public Integer create(String groupName, int creatorId, List<Integer> memberIds) {
		
		ReaderGroup readerGroup = new ReaderGroup();
		readerGroup.setName(groupName);
		readerGroup.setCreatorId(creatorId);
		
		Integer newGroupId = (Integer)hibernateTemplate.save(readerGroup);
		
		if (memberIds!=null && memberIds.size()>0) {
			memberIds.forEach(memberId -> {
				GroupMember groupMember = new GroupMember();
				groupMember.setMemberId(memberId);
				groupMember.setGroupId(newGroupId);
				
				hibernateTemplate.save(groupMember);
			});
		}
		
		return newGroupId;
	}
	
	@Override
	@Transactional
	public Integer create(String groupName, int creatorId, List<Integer> memberIds, List<Integer> bookIds) {
		
		ReaderGroup readerGroup = new ReaderGroup();
		readerGroup.setName(groupName);
		readerGroup.setCreatorId(creatorId);
		
		Integer newGroupId = (Integer)hibernateTemplate.save(readerGroup);
		
		if (memberIds!=null && memberIds.size()>0) {
			memberIds.forEach(memberId -> {
				GroupMember groupMember = new GroupMember();
				groupMember.setMemberId(memberId);
				groupMember.setGroupId(newGroupId);
				
				hibernateTemplate.save(groupMember);
			});
		}
		
		if (bookIds!=null && bookIds.size()>0) {
			bookIds.forEach(bookId -> {
				GroupBook groupBook = new GroupBook();
				groupBook.setGroupId(newGroupId);
				groupBook.setBookId(bookId);
				
				hibernateTemplate.save(groupBook);
			});
		}
		return newGroupId;
	}

	@Override
	@Transactional
	public void addMembers(int readerGroupId, List<Integer> memberIds) {
		
		memberIds.forEach(memberId -> {
			GroupMember groupMember = new GroupMember();
			groupMember.setGroupId(readerGroupId);
			groupMember.setMemberId(memberId);
			
			hibernateTemplate.save(groupMember);
		});
	}

	@Override
	@Transactional
	public void addBooks(int readerGroupId, List<Integer> bookIds) {
		
		bookIds.forEach(bookId -> {
			GroupBook groupBook = new GroupBook();
			groupBook.setGroupId(readerGroupId);
			groupBook.setBookId(bookId);
			
			hibernateTemplate.save(groupBook);
		});
	}
	
	@Override
	public boolean existsBook(int readerGroupId, int bookId) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Boolean> criteriaQuery = criteriaBuilder.createQuery(Boolean.class);
			
			Root<GroupBook> groupBookRoot = criteriaQuery.from(GroupBook.class);
			criteriaQuery
				.select(criteriaBuilder.literal(true))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(groupBookRoot.<Integer>get("bookId"), bookId),
						criteriaBuilder.equal(groupBookRoot.<Integer>get("groupId"), readerGroupId),
						criteriaBuilder.equal(groupBookRoot.<Boolean>get("enabled"), true)));
			
			List<Boolean> list = session.createQuery(criteriaQuery).list();
			
			if (list == null || list.size() == 0) {
				return false;
			} else {
				return true;
			}
		});
		
	}
	
	@Override
	public List<GroupBook> getGroupBooks(int readerGroupId, Boolean enabled) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<GroupBook> criteriaQuery = criteriaBuilder.createQuery(GroupBook.class);

			Root<GroupBook> groupBookRoot = criteriaQuery.from(GroupBook.class);
			criteriaQuery.select(groupBookRoot);
			
			List<Predicate> predicates = new ArrayList<>();
			
			predicates.add(criteriaBuilder.equal(groupBookRoot.<Integer>get("groupId"), readerGroupId));
			
			if (enabled != null) {
				predicates.add(criteriaBuilder.equal(groupBookRoot.<Boolean>get("enabled"), enabled));
			}
			criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));
			
			return session.createQuery(criteriaQuery).list();
		});
	}

	@Override
	public GroupBook getGroupBook(int groupBookId, int groupId, Boolean enabled) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<GroupBook> criteriaQuery = criteriaBuilder.createQuery(GroupBook.class);
			
			Root<GroupBook> groupBookRoot = criteriaQuery.from(GroupBook.class);
			Fetch<GroupBook, Book> bookFetch = groupBookRoot.fetch("book");
			Fetch<Book, Publication> publicationFetch = bookFetch.fetch("publication", JoinType.LEFT);
			Fetch<Book, Author> authorsfetch = bookFetch.fetch("authors", JoinType.LEFT);
			Fetch<Book, BookSource> fetch = bookFetch.fetch("source", JoinType.LEFT);
			
			groupBookRoot.fetch("group");
			
			criteriaQuery
				.select(groupBookRoot);
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(groupBookRoot.<Integer>get("id"), groupBookId));
			predicates.add(criteriaBuilder.equal(groupBookRoot.<Integer>get("groupId"), groupId));
			
			if (enabled != null) {
				predicates.add(criteriaBuilder.equal(groupBookRoot.<Boolean>get("enabled"), enabled));
				predicates.add(criteriaBuilder.equal(groupBookRoot.<Book>get("book").<Boolean>get("enabled"), enabled));
				predicates.add(criteriaBuilder.equal(groupBookRoot.<ReaderGroup>get("group").<Boolean>get("enabled"), enabled));
			}
			
			criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));
			
			return session.createQuery(criteriaQuery).uniqueResult();
		});
	}
	
	@Override
	@Transactional
	public void updateBooks(int readerGroupId, List<Integer> bookIds) {
		
		List<GroupBook> currentGroupBookList = getGroupBooks(readerGroupId, null);
		List<Integer> addedBookIds = new ArrayList<>();
		
		bookIds.forEach(bookId -> {
			
			Optional<GroupBook> currentGroupBook = currentGroupBookList.stream()
								.filter(currentBook -> currentBook.getBookId() == bookId)
								.findFirst();
			
			GroupBook groupBook;
			if (currentGroupBook.isPresent()) {
				groupBook = currentGroupBook.get();
				
				if (!groupBook.getEnabled()) {
					groupBook.setEnabled(true);
					hibernateTemplate.update(groupBook);
				}
			} else {
				addedBookIds.add(bookId);
			}
		});
		addBooks(readerGroupId, addedBookIds);
		
		currentGroupBookList.forEach(currentGroupBook -> {
			
			Optional<Integer> bookIdOptional = bookIds.stream()
				.filter(bookId -> bookId == currentGroupBook.getBookId())
				.findFirst();
			
			if (!bookIdOptional.isPresent()) {
				if (currentGroupBook.getEnabled()) {
					currentGroupBook.setEnabled(false);
					hibernateTemplate.update(currentGroupBook);
				}
			}
		});
	}
	
	@Override
	public List<GroupMember> getGroupMembers(int readerGroupId, Boolean enabled) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<GroupMember> criteriaQuery = criteriaBuilder.createQuery(GroupMember.class);

			Root<GroupMember> groupMemberRoot = criteriaQuery.from(GroupMember.class);
			criteriaQuery.select(groupMemberRoot);
			
			List<Predicate> predicates = new ArrayList<>();
			
			predicates.add(criteriaBuilder.equal(groupMemberRoot.<Integer>get("groupId"), readerGroupId));
			
			if (enabled != null) {
				predicates.add(criteriaBuilder.equal(groupMemberRoot.<Boolean>get("enabled"), enabled));
			}
			criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));
			
			return session.createQuery(criteriaQuery).list();
		});
	}
	
	@Override
	@Transactional
	public void updateMembers(int readerGroupId, List<Integer> memberIds) {
		
		List<GroupMember> currentGroupMemberList = getGroupMembers(readerGroupId, null);
		List<Integer> addedMemberIds = new ArrayList<>();
		
		memberIds.forEach(memberId -> {
			
			Optional<GroupMember> currentGroupMember = currentGroupMemberList.stream()
								.filter(currentMember -> currentMember.getMemberId() == memberId)
								.findFirst();
			
			GroupMember groupMember;
			if (currentGroupMember.isPresent()) {
				groupMember = currentGroupMember.get();
				
				if (!groupMember.getEnabled()) {
					groupMember.setEnabled(true);
					hibernateTemplate.update(groupMember);
				}
			} else {
				addedMemberIds.add(memberId);
			}
		});
		addMembers(readerGroupId, addedMemberIds);
		
		currentGroupMemberList.forEach(currentGroupMember -> {
			
			Optional<Integer> memberIdOptional = memberIds.stream()
				.filter(memberId -> memberId == currentGroupMember.getMemberId())
				.findFirst();
			
			if (!memberIdOptional.isPresent()) {
				if (currentGroupMember.getEnabled()) {
					currentGroupMember.setEnabled(false);
					hibernateTemplate.update(currentGroupMember);
				}
			}
		});
	}
}
