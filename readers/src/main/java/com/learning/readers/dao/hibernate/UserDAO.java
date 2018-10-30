package com.learning.readers.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.learning.readers.dao.IUserDAO;
import com.learning.readers.entity.User;
import com.learning.readers.model.UserNameEmailModel;

@Repository
public class UserDAO implements IUserDAO {

	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public List<User> list() {
		
		List<User> userList = hibernateTemplate.loadAll(User.class);
		return userList;
	}

	@Override
	public List<UserNameEmailModel> listNameEmail(Integer exceptUserId) {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<UserNameEmailModel> criteriaQuery = criteriaBuilder.createQuery(UserNameEmailModel.class);
			
			Root<User> userRoot = criteriaQuery.from(User.class);
			criteriaQuery
				.select(criteriaBuilder.construct(UserNameEmailModel.class,
						userRoot.<Integer>get("id"),
						userRoot.<String>get("username"),
						userRoot.<String>get("email")));
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(userRoot.<Boolean>get("enabled"), true));
			
			if (exceptUserId != null && exceptUserId != 0) {
				predicates.add(criteriaBuilder.notEqual(userRoot.<Integer>get("id"), exceptUserId));
			}
			criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));
				
			criteriaQuery.orderBy(criteriaBuilder.asc(userRoot.<String>get("email")));
			
			return session.createQuery(criteriaQuery).list();
		});
	}

	@Override
	public User findByUsername(String username) {
		
		return hibernateTemplate.execute(new HibernateCallback<User>() {
			@Override
			public User doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
				
				Root<User> userRoot = criteriaQuery.from(User.class);
				criteriaQuery.select(userRoot)
					.where(criteriaBuilder.equal(userRoot.<String>get("username"), username));
				
				return session.createQuery(criteriaQuery).uniqueResult();
			}
		});
	}

	@Override
	public User findByEmail(String email) {
		
		return hibernateTemplate.execute(new HibernateCallback<User>() {

			@Override
			public User doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
				
				Root<User> userRoot = criteriaQuery.from(User.class);
				criteriaQuery.select(userRoot)
					.where(criteriaBuilder.equal(userRoot.<String>get("email"), email));
				
				return session.createQuery(criteriaQuery).uniqueResult();
			}
		});
	}

	@Override
	@Transactional
	public Integer create(User user) {
		Integer id = (Integer)hibernateTemplate.save(user);
		return id;
	}

	@Override
	public boolean exists(String fieldName, Object value) {
		
		return hibernateTemplate.execute(new HibernateCallback<Boolean>() {

			@Override
			public Boolean doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Boolean> criteriaQuery = criteriaBuilder.createQuery(Boolean.class);
				
				Root<User> userRoot = criteriaQuery.from(User.class);
				criteriaQuery
					.select(criteriaBuilder.literal(true))
					.where(criteriaBuilder.equal(userRoot.get(fieldName), value));
				
				Boolean result = session.createQuery(criteriaQuery).uniqueResult();
				
				if (result == null) {
					return false;
				} else {
					return true;
				}
			}
		});
	}

	@Override
	public User findById(int userId) {
		return hibernateTemplate.get(User.class, userId);
		//return hibernateTemplate.load(User.class, userId);
	}

	
}
