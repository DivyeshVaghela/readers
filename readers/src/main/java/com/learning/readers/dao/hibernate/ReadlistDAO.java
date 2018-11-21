package com.learning.readers.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.learning.readers.dao.IReadlistDAO;
import com.learning.readers.entity.Book;
import com.learning.readers.entity.GroupBook;
import com.learning.readers.entity.Readlist;
import com.learning.readers.entity.ReadlistBook;
import com.learning.readers.model.ReadlistOverviewModel;
import com.learning.readers.util.SortOrder;

@Repository
public class ReadlistDAO implements IReadlistDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	@Transactional
	public Integer create(Readlist readlist) {
		return (Integer)hibernateTemplate.save(readlist);
	}
	
	@Override
	@Transactional
	public Integer create(Readlist readlist, List<Integer> bookIds) {
		
		Integer newReadlistId = create(readlist);
		addBooks(newReadlistId, bookIds);
		
		return newReadlistId;
	}

	@Override
	@Transactional
	public void addBooks(int readlistId, List<Integer> bookIds) {
		
		bookIds.forEach(bookId -> {
			addBook(readlistId, bookId);
		});
	}

	@Override
	@Transactional
	public Integer addBook(int readlistId, int bookId) {
		ReadlistBook readlistBook = new ReadlistBook();
		readlistBook.setBookId(bookId);
		readlistBook.setReadlistId(readlistId);
		
		return (Integer)hibernateTemplate.save(readlistBook);
	}
	
	@Override
	public boolean existsAnother(int creatorId, String readlistName, int exceptReadlistId) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Boolean> criteriaQuery = criteriaBuilder.createQuery(Boolean.class);
			
			Root<Readlist> readlistRoot = criteriaQuery.from(Readlist.class);
			criteriaQuery
				.select(criteriaBuilder.literal(true))
				.where(criteriaBuilder.and(
						criteriaBuilder.notEqual(readlistRoot.<Integer>get("id"), exceptReadlistId),
						criteriaBuilder.equal(readlistRoot.<Integer>get("creatorId"), creatorId),
						criteriaBuilder.equal(readlistRoot.<String>get("name"), readlistName),
						criteriaBuilder.equal(readlistRoot.<Boolean>get("enabled"), true)));
			
			List<Boolean> list = session.createQuery(criteriaQuery).list();
			if (list == null || list.size() > 0) {
				return true;
			} else {
				return false;
			}
		});
	}
	
	@Override
	public boolean exists(int creatorId, String readlistName){
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Boolean> criteriaQuery = criteriaBuilder.createQuery(Boolean.class);
			
			Root<Readlist> readlistRoot = criteriaQuery.from(Readlist.class);
			criteriaQuery
				.select(criteriaBuilder.literal(true))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(readlistRoot.<Integer>get("creatorId"), creatorId),
						criteriaBuilder.equal(readlistRoot.<String>get("name"), readlistName),
						criteriaBuilder.equal(readlistRoot.<Boolean>get("enabled"), true)));
			
			List<Boolean> list = session.createQuery(criteriaQuery).list();
			if (list == null || list.size() > 0) {
				return true;
			} else {
				return false;
			}
		});
	}
	
	@Override
	public List<ReadlistOverviewModel> list(int creatorId, String orderByField, SortOrder order, Integer firstResult, Integer limit){
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ReadlistOverviewModel> criteriaQuery = criteriaBuilder.createQuery(ReadlistOverviewModel.class);
			
			Root<Readlist> readlistRoot = criteriaQuery.from(Readlist.class);
			
			Subquery<Long> bookCountSubquery = criteriaQuery.subquery(Long.class);
			Root<ReadlistBook> readlistBookRoot = bookCountSubquery.from(ReadlistBook.class);
			bookCountSubquery
				.select(criteriaBuilder.count(readlistBookRoot))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(readlistBookRoot.<Integer>get("readlistId"), readlistRoot.<Integer>get("id")),
						criteriaBuilder.equal(readlistBookRoot.<Boolean>get("enabled"), true)));
			
			criteriaQuery
				.select(criteriaBuilder.construct(ReadlistOverviewModel.class,
						readlistRoot.<Integer>get("id"),
						readlistRoot.<String>get("name"),
						readlistRoot.<Integer>get("creatorId"),
						bookCountSubquery.getSelection()))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(readlistRoot.<Integer>get("creatorId"), creatorId),
						criteriaBuilder.equal(readlistRoot.<Boolean>get("enabled"), true)));
			
			String orderByField_local = "creationTime";
			if (orderByField != null)
				orderByField_local = orderByField;
			
			SortOrder order_local = SortOrder.DESC;
			if (order != null)
				order_local = order;
			
			if (order_local == SortOrder.ASC) {
				criteriaQuery.orderBy(criteriaBuilder.asc(readlistRoot.<Date>get(orderByField_local)));
			} else if (order_local == SortOrder.DESC) {
				criteriaQuery.orderBy(criteriaBuilder.desc(readlistRoot.<Date>get(orderByField_local)));
			}
			
			Query<ReadlistOverviewModel> query = session.createQuery(criteriaQuery);
			if (firstResult != null)
				query.setFirstResult(firstResult);
			if (limit != null)
				query.setMaxResults(limit);
			
			return query.list();
		});
	}
	
	@Override
	public Readlist getById(int readlistId) {
		
		return hibernateTemplate.get(Readlist.class, readlistId);
	}
	
	@Override
	public Readlist getFullyloaded(int readlistId, int creatorId) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Readlist> criteriaQuery = criteriaBuilder.createQuery(Readlist.class);
			
			Root<Readlist> readlistRoot = criteriaQuery.from(Readlist.class);
			Join<Readlist, ReadlistBook> readlistBookJoin = (Join)readlistRoot.fetch("books", JoinType.LEFT);
			Join<ReadlistBook, Book> bookJoin = (Join)readlistBookJoin.fetch("book", JoinType.LEFT);
			readlistRoot.fetch("creator", JoinType.INNER);
			
			criteriaQuery
				.select(readlistRoot)
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(readlistRoot.<Integer>get("id"), readlistId),
						criteriaBuilder.equal(readlistRoot.<Integer>get("creatorId"), creatorId),
						criteriaBuilder.equal(readlistRoot.<Boolean>get("enabled"), true),
						criteriaBuilder.equal(readlistBookJoin.<Boolean>get("enabled"), true),
						criteriaBuilder.equal(bookJoin.<Boolean>get("enabled"), true)));
			
			return session.createQuery(criteriaQuery).uniqueResult();
		});
	}
	
	@Override
	public List<ReadlistBook> getReadlistBooks(int readlistId, Boolean enabled) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ReadlistBook> criteriaQuery = criteriaBuilder.createQuery(ReadlistBook.class);

			Root<ReadlistBook> readlistBookRoot = criteriaQuery.from(ReadlistBook.class);
			criteriaQuery.select(readlistBookRoot);
			
			List<Predicate> predicates = new ArrayList<>();
			
			predicates.add(criteriaBuilder.equal(readlistBookRoot.<Integer>get("readlistId"), readlistId));
			
			if (enabled != null) {
				predicates.add(criteriaBuilder.equal(readlistBookRoot.<Boolean>get("enabled"), enabled));
			}
			criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));
			
			return session.createQuery(criteriaQuery).list();
		});
	}
	
	@Override
	@Transactional
	public void updateBooks(int readlistId, List<Integer> bookIds) {
		
		List<ReadlistBook> currentReadlistBookList = getReadlistBooks(readlistId, null);
		List<Integer> addedBookIds = new ArrayList<>();
		
		bookIds.forEach(bookId -> {
			
			Optional<ReadlistBook> currentReadlistBook = currentReadlistBookList.stream()
								.filter(currentBook -> currentBook.getBookId() == bookId)
								.findFirst();
			
			ReadlistBook readlistBook;
			if (currentReadlistBook.isPresent()) {
				readlistBook = currentReadlistBook.get();
				
				if (!readlistBook.getEnabled()) {
					readlistBook.setEnabled(true);
					hibernateTemplate.update(readlistBook);
				}
			} else {
				addedBookIds.add(bookId);
			}
		});
		addBooks(readlistId, addedBookIds);
		
		currentReadlistBookList.forEach(currentReadlistBook -> {
			
			Optional<Integer> bookIdOptional = bookIds.stream()
				.filter(bookId -> bookId == currentReadlistBook.getBookId())
				.findFirst();
			
			if (!bookIdOptional.isPresent()) {
				if (currentReadlistBook.getEnabled()) {
					currentReadlistBook.setEnabled(false);
					hibernateTemplate.update(currentReadlistBook);
				}
			}
		});
	}
	
	@Override
	@Transactional
	public void updateBasicDetails(Readlist readlist) {
		hibernateTemplate.update(readlist);
	}

	@Override
	@Transactional
	public void update(Readlist readlist, List<Integer> bookIds) {
		updateBasicDetails(readlist);
		updateBooks(readlist.getId(), bookIds);
	}

	@Override
	@Transactional
	public int remove(int readlistId) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaUpdate<Readlist> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Readlist.class);
			
			Root<Readlist> readlistRoot = criteriaUpdate.from(Readlist.class);
			criteriaUpdate.set(readlistRoot.<Boolean>get("enabled"), false);
			criteriaUpdate.where(criteriaBuilder.equal(readlistRoot.<Integer>get("id"), readlistId));
			
			return session.createQuery(criteriaUpdate).executeUpdate();
		});
	}
}
