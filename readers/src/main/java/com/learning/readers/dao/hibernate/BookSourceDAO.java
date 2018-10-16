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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.learning.readers.dao.IBookSourceDAO;
import com.learning.readers.entity.Book;
import com.learning.readers.entity.BookSource;
import com.learning.readers.model.BookSourceValueModel;

@Repository
public class BookSourceDAO implements IBookSourceDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public List<BookSourceValueModel> listNames() {
		
		return hibernateTemplate.execute(new HibernateCallback<List<BookSourceValueModel>>() {

			@Override
			public List<BookSourceValueModel> doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<BookSourceValueModel> criteriaQuery = criteriaBuilder.createQuery(BookSourceValueModel.class);
				
				Root<BookSource> bookSourceRoot = criteriaQuery.from(BookSource.class);
				criteriaQuery
					.select(criteriaBuilder.construct(BookSourceValueModel.class, 
							bookSourceRoot.<Integer>get("id"),
							bookSourceRoot.<String>get("value")));
				
				return session.createQuery(criteriaQuery).list();
			}
		});
	}

	@Override
	@Transactional
	public void saveOrUpdate(BookSource bookSource) {
		
		hibernateTemplate.execute(session -> {
			
			Book book = session.load(Book.class, bookSource.getBook().getId());
			BookSource source = book.getSource();
			if (source == null) {
				book.setSource(bookSource);
			} else {
				source.setType(bookSource.getType());
				source.setValue(bookSource.getValue());
			}
			session.saveOrUpdate(book);
			return null;
		});
	}

}
