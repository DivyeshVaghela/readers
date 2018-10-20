package com.learning.readers.dao.hibernate;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.learning.readers.dao.IBookShareDAO;
import com.learning.readers.dao.IReadStatusDAO;
import com.learning.readers.entity.Book;
import com.learning.readers.entity.BookShare;
import com.learning.readers.entity.BookUser;
import com.learning.readers.entity.User;
import com.learning.readers.model.BookOverviewModel;
import com.learning.readers.util.SortOrder;

@Repository
public class BookShareDAO implements IBookShareDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private IReadStatusDAO readStatusDAO;

	@Override
	public Integer share(int senderId, int receiverId, int bookId, Integer actionId) {

		BookShare newBookShare = new BookShare();
		newBookShare.setSenderId(senderId);
		newBookShare.setReceiverId(receiverId);
		newBookShare.setBookId(bookId);
		newBookShare.setActionId(actionId);

		return (Integer) hibernateTemplate.save(newBookShare);
	}
	
	@Transactional
	@Override
	public void share(List<BookShare> bookShareList) {
		bookShareList.forEach(bookShare -> hibernateTemplate.save(bookShare));
	}
	
	@Override
	public List<BookOverviewModel> shareHistory(int userId, String orderByField, SortOrder sortOrder, Integer firstResult,
			Integer limit){
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<BookOverviewModel> criteriaQuery = criteriaBuilder.createQuery(BookOverviewModel.class);
			
			Root<BookShare> bookShareRoot = criteriaQuery.from(BookShare.class);
			Path<Book> bookPath = bookShareRoot.<Book>get("book");
			
			criteriaQuery
				.select(criteriaBuilder.construct(BookOverviewModel.class, 
						bookPath.<String>get("id"),
						bookShareRoot.<Integer>get("id"),
						bookPath.<String>get("name"),
						bookPath.<String>get("edition"),
						bookPath.<String>get("coverPhoto"),
						bookPath.<String>get("details"),
						bookShareRoot.<Integer>get("senderId"),
						bookShareRoot.<Integer>get("receiverId"),
						bookShareRoot.<User>get("sender").<String>get("email"),
						bookShareRoot.<User>get("receiver").<String>get("email"),
						bookShareRoot.<Date>get("creationTime")))
				.where(criteriaBuilder.or(
						criteriaBuilder.equal(bookShareRoot.<Integer>get("senderId"), userId),
						criteriaBuilder.equal(bookShareRoot.<Integer>get("receiverId"), userId)));
			
			if (orderByField != null) {
				if (sortOrder == SortOrder.ASC) {
					criteriaQuery.orderBy(criteriaBuilder.asc(bookShareRoot.get(orderByField)));
				} else if (sortOrder == SortOrder.DESC) {
					criteriaQuery.orderBy(criteriaBuilder.desc(bookShareRoot.get(orderByField)));
				}
			}

			Query<BookOverviewModel> query = session.createQuery(criteriaQuery);
			if (firstResult != null && firstResult != 0)
				query.setFirstResult(firstResult);
			if (limit != null && limit != 0)
				query.setMaxResults(limit);
			
			return session.createQuery(criteriaQuery).list();
		});
	}

	@Override
	public List<BookOverviewModel> sharedToMe(int receiverId, String orderByField, SortOrder sortOrder, Integer firstResult,
			Integer limit) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<BookOverviewModel> criteriaQuery = criteriaBuilder.createQuery(BookOverviewModel.class);
			
			Root<BookShare> bookShareRoot = criteriaQuery.from(BookShare.class);
			
			Path<Book> bookPath = bookShareRoot.<Book>get("book");
			
			criteriaQuery
				.select(criteriaBuilder.construct(BookOverviewModel.class, 
						bookPath.<String>get("id"),
						bookShareRoot.<Integer>get("id"),
						bookPath.<String>get("name"),
						bookPath.<String>get("edition"),
						bookPath.<String>get("coverPhoto"),
						bookPath.<String>get("details"),
						bookShareRoot.<Integer>get("senderId"),
						bookShareRoot.<Integer>get("receiverId"),
						bookShareRoot.<User>get("sender").<String>get("email"),
						bookShareRoot.<User>get("receiver").<String>get("email")))
				.where(criteriaBuilder.and(
						criteriaBuilder.equal(bookShareRoot.<Integer>get("receiverId"), receiverId),
						criteriaBuilder.isNull(bookShareRoot.<Integer>get("actionId"))));
			
			if (orderByField != null) {
				if (sortOrder == SortOrder.ASC) {
					criteriaQuery.orderBy(criteriaBuilder.asc(bookShareRoot.get(orderByField)));
				} else if (sortOrder == SortOrder.DESC) {
					criteriaQuery.orderBy(criteriaBuilder.desc(bookShareRoot.get(orderByField)));
				}
			}

			Query<BookOverviewModel> query = session.createQuery(criteriaQuery);
			if (firstResult != null && firstResult != 0)
				query.setFirstResult(firstResult);
			if (limit != null && limit != 0)
				query.setMaxResults(limit);
			
			return session.createQuery(criteriaQuery).list();
		});
	}

	@Override
	public BookShare findById(int bookShareId, int receiverId) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<BookShare> criteriaQuery = criteriaBuilder.createQuery(BookShare.class);
			
			Root<BookShare> bookShareRoot = criteriaQuery.from(BookShare.class);
			bookShareRoot.fetch("sender");
			bookShareRoot.fetch("receiver");
			Fetch<Object, Object> fetchBook = bookShareRoot.fetch("book");
			fetchBook.fetch("publication", JoinType.LEFT);
			fetchBook.fetch("authors", JoinType.LEFT);
			bookShareRoot.fetch("action", JoinType.LEFT);
			
			criteriaQuery
				.select(bookShareRoot);
			
			if (receiverId != 0) {
				criteriaQuery.where(
						criteriaBuilder.and(
								criteriaBuilder.equal(bookShareRoot.<Integer>get("id"), bookShareId),
								criteriaBuilder.equal(bookShareRoot.<Integer>get("receiverId"), receiverId)));
			} else {
				criteriaQuery.where(criteriaBuilder.equal(bookShareRoot.<Integer>get("id"), bookShareId));
			}
			
			return session.createQuery(criteriaQuery).uniqueResult();
		});
	}
	
	@Override
	@Transactional
	public void takeShareAction(int bookShareId, int actionId) {
		BookShare bookShare = hibernateTemplate.get(BookShare.class, bookShareId);
		
		if (actionId == 3) {
			
			BookUser bookUser = new BookUser();
			bookUser.setBook(bookShare.getBook());
			bookUser.setUser(bookShare.getReceiver());
			bookUser.setStatus(readStatusDAO.findById(1));
			
			hibernateTemplate.save(bookUser);
		}
		
		bookShare.setActionId(actionId);
		hibernateTemplate.update(bookShare);
	}
}
