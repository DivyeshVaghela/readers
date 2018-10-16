package com.learning.readers.dao.hibernate;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
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
import com.learning.readers.entity.BookSource;
import com.learning.readers.entity.BookType;
import com.learning.readers.entity.Publication;
import com.learning.readers.entity.ReadDetail;
import com.learning.readers.entity.ReadStatus;
import com.learning.readers.entity.User;
import com.learning.readers.model.BookDetailsModel;
import com.learning.readers.model.BookOverviewModel;
import com.learning.readers.model.BookSourceValueModel;
import com.learning.readers.model.BookTypeValueModel;
import com.learning.readers.model.PublicationNameModel;
import com.learning.readers.model.ReadStatusValueModel;
import com.learning.readers.util.SortOrder;

@Repository
public class BookDAO implements IBookDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	@Transactional
	public Integer create(Book book) {
		return (Integer)hibernateTemplate.save(book);
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
				criteriaQuery
					.select(bookRoot)
					.where(criteriaBuilder.equal(bookRoot.<User>get("reader").<Integer>get("id"), userId));
				
				return session.createQuery(criteriaQuery).list();
			}
		});
	}

	@Override
	public List<BookOverviewModel> list(int userId, String orderByField, SortOrder sortOrder, int firstResult, int limit) {
		
		return hibernateTemplate.execute(new HibernateCallback<List<BookOverviewModel>>() {

			@Override
			public List<BookOverviewModel> doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<BookOverviewModel> criteriaQuery = criteriaBuilder.createQuery(BookOverviewModel.class);
				
				Root<Book> bookRoot = criteriaQuery.from(Book.class);
				criteriaQuery
					.select(criteriaBuilder.construct(BookOverviewModel.class,
						bookRoot.<Integer>get("id"),
						bookRoot.<String>get("name"),
						bookRoot.<String>get("edition"),
						bookRoot.<String>get("coverPhoto"),
						bookRoot.<String>get("details")))
					.where(criteriaBuilder.equal(bookRoot.<Integer>get("readerID"), userId));
				
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
				bookRoot.fetch("status");
				
				criteriaQuery
					.select(bookRoot)
					.where(
							criteriaBuilder.and(
									criteriaBuilder.equal(bookRoot.<Integer>get("id"), bookId),
									criteriaBuilder.equal(bookRoot.<Integer>get("readerID"), userId)));
				
				Book book = session.createQuery(criteriaQuery).uniqueResult();
				
				for (Author author : book.getAuthors());
				
				return book;
			}
		});
	}

	@Override
	@Transactional
	public void update(Book book) {
		//hibernateTemplate.merge(book);
		hibernateTemplate.update(book);
	}

	/*@Override
	public BookDetailsModel findById(int bookId, int userId, boolean isLazyLoaded) {
		
		return hibernateTemplate.execute(new HibernateCallback<BookDetailsModel>() {

			@Override
			public BookDetailsModel doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<BookDetailsModel> criteriaQuery = criteriaBuilder.createQuery(BookDetailsModel.class);
				
				Root<Book> bookRoot = criteriaQuery.from(Book.class);
				
				//bookRoot.fetch("readDetails", JoinType.LEFT);
				
				Join<Book, Publication> joinPublication = bookRoot.join("publication", JoinType.LEFT);
				Join<Book, ReadStatus> joinStatus = bookRoot.join("status", JoinType.INNER);
				Join<Book, BookSource> joinSource = bookRoot.join("source", JoinType.LEFT);
				Join<BookSource, BookType> joinType = joinSource.join("type", JoinType.LEFT);
				Join<Book, ReadDetail> joinReadDetail = bookRoot.join("readDetails", JoinType.LEFT);
				
				criteriaQuery
					.select(criteriaBuilder.construct(BookDetailsModel.class, 
							bookRoot.<Integer>get("id"),
							bookRoot.<String>get("name"),
							bookRoot.<String>get("coverPhoto"),
							bookRoot.<String>get("edition"),
							bookRoot.<String>get("details"),
							bookRoot.<String>get("ISBN"),
							bookRoot.<Integer>get("publishedYear"),
							bookRoot.<Integer>get("publishedMonth"),
							bookRoot.<Integer>get("publishedDate"),
							bookRoot.<Boolean>get("enabled"),
							bookRoot.<Date>get("creationTime"),
							bookRoot.<Date>get("modificationTime"),
							
							//publication
							joinPublication.<Integer>get("id"),
							joinPublication.<String>get("name"),
							
							//reading status
							joinStatus.<Integer>get("id"),
							joinStatus.<String>get("value"),

							//Book type
							joinType.<Integer>get("id"),
							joinType.<String>get("value"),
							
							//book source
							joinSource.<Integer>get("id"),
							joinSource.<String>get("value"),
							
							//read details
							joinReadDetail
						))
					.where(
							criteriaBuilder.and(
									criteriaBuilder.equal(bookRoot.<Integer>get("id"), bookId),
									criteriaBuilder.equal(bookRoot.<Integer>get("readerID"), userId)
									)
							);
				
				BookDetailsModel result = session.createQuery(criteriaQuery).uniqueResult();
				
				if (result != null) {
					
					CriteriaQuery<Author> criteriaQueryAuthors = criteriaBuilder.createQuery(Author.class);
					Root<Author> authorRoot = criteriaQueryAuthors.from(Author.class);

				}
				
				return result;
			}
		});
	}*/
	
	
}
