package com.learning.readers.dao.hibernate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.learning.readers.dao.IRoleDAO;
import com.learning.readers.entity.Role;

@Repository
public class RoleDAO implements IRoleDAO {

	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public List<Role> list() {
		
		List<Role> roleList = hibernateTemplate.loadAll(Role.class);
		return roleList;
	}

	@Override
	public Integer create(Role role) {

		Integer id = (Integer)hibernateTemplate.save(role);
		return id;
	}

	@Override
	public Role get(int roleId) {
		
		Role role = hibernateTemplate.load(Role.class, roleId);
		return role;
	}

	@Override
	public Role get(String roleName) {
		
		return hibernateTemplate.execute(new HibernateCallback<Role>() {
			@Override
			public Role doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
				
				Root<Role> roleRoot = criteriaQuery.from(Role.class);
				criteriaQuery.select(roleRoot)
					.where(criteriaBuilder.equal(roleRoot.<String>get("name"), roleName));
				
				return session.createQuery(criteriaQuery).uniqueResult();
			}
		});
	}

	@Override
	public Role getWithUsers(String roleName) {
		
		return hibernateTemplate.execute(new HibernateCallback<Role>() {
			@Override
			public Role doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
				
				Root<Role> roleRoot = criteriaQuery.from(Role.class);
				roleRoot.fetch("users", JoinType.LEFT);
				criteriaQuery.select(roleRoot)
					.where(criteriaBuilder.equal(roleRoot.<String>get("name"), roleName));
				
				return session.createQuery(criteriaQuery).uniqueResult();
			}
		});
	}

}
