package com.learning.readers.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.learning.readers.dao.IBookDAO;
import com.learning.readers.entity.Author;
import com.learning.readers.entity.Book;
import com.learning.readers.entity.BookUser;
import com.learning.readers.entity.ReadStatus;
import com.learning.readers.entity.User;
import com.learning.readers.model.BookNameEditionModel;
import com.learning.readers.model.BookOverviewModel;
import com.learning.readers.util.FieldNameValue;
import com.learning.readers.util.SortOrder;

@Repository
public class BookDAO implements IBookDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	@Transactional
	public Integer create(Book book) {
		return (Integer) hibernateTemplate.save(book);
	}

	@Override
	public List<Book> list() {
		return hibernateTemplate.loadAll(Book.class);
	}

	@Override
	public List<Book> list(int userId) {

		return hibernateTemplate.execute(new HibernateCallback<List<Book>>() {

			@Override
			public List<Book> doInHibernate(Session session) throws HibernateException {

				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);

				Root<Book> bookRoot = criteriaQuery.from(Book.class);
				criteriaQuery.select(bookRoot)
						.where(criteriaBuilder.and(
								criteriaBuilder.equal(bookRoot.<User>get("user").<Integer>get("id"), userId),
								criteriaBuilder.equal(bookRoot.<Boolean>get("enabled"), true)));

				return session.createQuery(criteriaQuery).list();
			}
		});
	}

	@Override
	public List<BookOverviewModel> list(int userId, int readStatusId, String orderByField, SortOrder sortOrder, Integer firstResult, Integer limit){
		
		return hibernateTemplate.execute(new HibernateCallback<List<BookOverviewModel>>() {

			@Override
			public List<BookOverviewModel> doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<BookOverviewModel> criteriaQuery = criteriaBuilder.createQuery(BookOverviewModel.class);
				
				Root<BookUser> bookUserRoot = criteriaQuery.from(BookUser.class);
				Path<Book> bookPath = bookUserRoot.<Book>get("book");
				criteriaQuery
					.select(criteriaBuilder.construct(BookOverviewModel.class, 
						bookPath.<Integer>get("id"),
						bookPath.<String>get("name"),
						bookPath.<String>get("edition"),
						bookPath.<String>get("coverPhoto"),
						bookPath.<String>get("details")))
					.where(criteriaBuilder.and(
							criteriaBuilder.equal(bookUserRoot.<User>get("user").<Integer>get("id"), userId),
							criteriaBuilder.equal(bookUserRoot.<ReadStatus>get("status").<Integer>get("id"), readStatusId),
							criteriaBuilder.equal(bookPath.<Boolean>get("enabled"), true)
						));
				
				if (orderByField != null) {
					if (sortOrder == SortOrder.ASC) {
						criteriaQuery.orderBy(criteriaBuilder.asc(bookUserRoot.get(orderByField)));
					} else if (sortOrder == SortOrder.DESC) {
						criteriaQuery.orderBy(criteriaBuilder.desc(bookUserRoot.get(orderByField)));
					}
				}

				Query<BookOverviewModel> query = session.createQuery(criteriaQuery);
				if (firstResult != null)
					query.setFirstResult(firstResult);
				if (limit != null && limit != 0)
					query.setMaxResults(limit);
				
				return query.list();
			}
		});
	}

	@Override
	public List<BookNameEditionModel> listMyBooksNameEdition(int userId, String orderByField, SortOrder sortOrder, Integer firstResult, Integer limit){
		
		return hibernateTemplate.execute(new HibernateCallback<List<BookNameEditionModel>>() {

			@Override
			public List<BookNameEditionModel> doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<BookNameEditionModel> criteriaQuery = criteriaBuilder.createQuery(BookNameEditionModel.class);
				
				Root<Book> bookRoot = criteriaQuery.from(Book.class);
				criteriaQuery
					.select(criteriaBuilder.construct(BookNameEditionModel.class, 
						bookRoot.<Integer>get("id"),
						bookRoot.<String>get("name"),
						bookRoot.<String>get("edition")))
					.where(criteriaBuilder.and(
							criteriaBuilder.equal(bookRoot.<User>get("user").<Integer>get("id"), userId),
							criteriaBuilder.equal(bookRoot.<Boolean>get("enabled"), true)
						));
				
				if (orderByField != null) {
					if (sortOrder == SortOrder.ASC) {
						criteriaQuery.orderBy(criteriaBuilder.asc(bookRoot.get(orderByField)));
					} else if (sortOrder == SortOrder.DESC) {
						criteriaQuery.orderBy(criteriaBuilder.desc(bookRoot.get(orderByField)));
					}
				}

				Query<BookNameEditionModel> query = session.createQuery(criteriaQuery);
				if (firstResult != null)
					query.setFirstResult(firstResult);
				if (limit != null && limit != 0)
					query.setMaxResults(limit);
				
				return query.list();
			}
		});
	}
	
	@Override
	public List<BookOverviewModel> list(String orderByField, SortOrder sortOrder, int firstResult, int limit,
			List<FieldNameValue<String, Object>> eqRestrictions) {

		return hibernateTemplate.execute(new HibernateCallback<List<BookOverviewModel>>() {

			@Override
			public List<BookOverviewModel> doInHibernate(Session session) throws HibernateException {

				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<BookOverviewModel> criteriaQuery = criteriaBuilder.createQuery(BookOverviewModel.class);

				Root<Book> bookRoot = criteriaQuery.from(Book.class);
				criteriaQuery.select(criteriaBuilder.construct(BookOverviewModel.class, bookRoot.<Integer>get("id"),
						bookRoot.<String>get("name"), bookRoot.<String>get("edition"),
						bookRoot.<String>get("coverPhoto"), bookRoot.<String>get("details")));

				List<Predicate> predicates = new ArrayList<>();
				for (FieldNameValue<String, Object> restriction : eqRestrictions) {
					predicates.add(criteriaBuilder.equal(bookRoot.get(restriction.getName()), restriction.getValue()));
				}
				criteriaQuery.where(predicates.toArray(new Predicate[] {}));

				if (orderByField != null) {
					if (sortOrder == SortOrder.ASC) {
						criteriaQuery.orderBy(criteriaBuilder.asc(bookRoot.get(orderByField)));
					} else if (sortOrder == SortOrder.DESC) {
						criteriaQuery.orderBy(criteriaBuilder.desc(bookRoot.get(orderByField)));
					}
				}

				Query<BookOverviewModel> query = session.createQuery(criteriaQuery);
				query.setFirstResult(firstResult);
				if (limit != 0) {
					query.setMaxResults(limit);
				}

				return query.list();
			}
		});
	}

	@Override
	public Book findById(int bookId, int userId, boolean isLazyLoaded) {

		return hibernateTemplate.execute(new HibernateCallback<Book>() {

			@Override
			public Book doInHibernate(Session session) throws HibernateException {

				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);

				Root<Book> bookRoot = criteriaQuery.from(Book.class);
				bookRoot.fetch("publication", JoinType.LEFT);
				bookRoot.fetch("bookReaders", JoinType.LEFT).fetch("status", JoinType.LEFT);

				criteriaQuery.select(bookRoot)
						.where(criteriaBuilder.equal(bookRoot.<Integer>get("id"), bookId));

				Book book = session.createQuery(criteriaQuery).uniqueResult();

				if (book != null)
					for (Author author : book.getAuthors());

				return book;
			}
		});
	}

	@Override
	@Transactional
	public void update(Book book) {
		// hibernateTemplate.merge(book);
		hibernateTemplate.update(book);
	}

	@Override
	@Transactional
	public int remove(int bookId) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaUpdate<Book> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Book.class);
			
			Root<Book> bookRoot = criteriaUpdate.from(Book.class);
			criteriaUpdate.set(bookRoot.<Boolean>get("enabled"), false);
			criteriaUpdate.where(criteriaBuilder.equal(bookRoot.<Integer>get("id"), bookId));
			
			return session.createQuery(criteriaUpdate).executeUpdate();
		});
	}

	
}
