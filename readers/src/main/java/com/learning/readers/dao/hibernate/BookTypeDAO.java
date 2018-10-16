package com.learning.readers.dao.hibernate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.learning.readers.dao.IBookTypeDAO;
import com.learning.readers.entity.BookType;
import com.learning.readers.model.BookTypeValueModel;

@Repository
public class BookTypeDAO implements IBookTypeDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<BookTypeValueModel> listValues() {
		
		return hibernateTemplate.execute(new HibernateCallback<List<BookTypeValueModel>>() {

			@Override
			public List<BookTypeValueModel> doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<BookTypeValueModel> criteriaQuery = criteriaBuilder.createQuery(BookTypeValueModel.class);
				
				Root<BookType> bookTypeRoot = criteriaQuery.from(BookType.class);
				criteriaQuery
					.select(criteriaBuilder.construct(BookTypeValueModel.class,
								bookTypeRoot.<Integer>get("id"),
								bookTypeRoot.<String>get("value")));
				
				return session.createQuery(criteriaQuery).list();
			}
		});
	}

	@Override
	public BookType findByValue(String value) {
		
		return hibernateTemplate.execute(new HibernateCallback<BookType>() {

			@Override
			public BookType doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<BookType> criteriaQuery = criteriaBuilder.createQuery(BookType.class);
				
				Root<BookType> bookTypeRoot = criteriaQuery.from(BookType.class);
				criteriaQuery
					.select(bookTypeRoot)
					.where(criteriaBuilder.equal(bookTypeRoot.<String>get("value"), value));
				
				return session.createQuery(criteriaQuery).uniqueResult();
			}
		});
	}

	@Override
	public BookType findById(int id) {
		return hibernateTemplate.load(BookType.class, id);
	}

}
