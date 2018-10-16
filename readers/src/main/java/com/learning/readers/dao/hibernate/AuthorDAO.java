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

import com.learning.readers.dao.IAuthorDAO;
import com.learning.readers.entity.Author;
import com.learning.readers.entity.Book;
import com.learning.readers.model.AuthorNameModel;

@Repository
public class AuthorDAO implements IAuthorDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public List<AuthorNameModel> listAutherNames() {
		
		return hibernateTemplate.execute(new HibernateCallback<List<AuthorNameModel>>() {

			@Override
			public List<AuthorNameModel> doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<AuthorNameModel> criteriaQuery = criteriaBuilder.createQuery(AuthorNameModel.class);
				
				Root<Author> authorRoot = criteriaQuery.from(Author.class);
				criteriaQuery
					.select(criteriaBuilder.construct(AuthorNameModel.class, 
							authorRoot.<Integer>get("id"),
							authorRoot.<String>get("firstName"),
							authorRoot.<String>get("middleName"),
							authorRoot.<String>get("lastName")));
				
				return session.createQuery(criteriaQuery).list();
			}
		});
	}

	
	
	@Override
	public List<Author> findByField(String fieldName, Object value) {

		return hibernateTemplate.execute(new HibernateCallback<List<Author>>() {

			@Override
			public List<Author> doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
				
				Root<Author> authorRoot = criteriaQuery.from(Author.class);
				criteriaQuery
					.select(authorRoot)
					.where(criteriaBuilder.equal(authorRoot.get(fieldName), value));
				
				return session.createQuery(criteriaQuery).list();
			}
		});
	}

	@Override
	public Author findById(int authorId) {
		
		return hibernateTemplate.load(Author.class, authorId);
	}
	
	
}
