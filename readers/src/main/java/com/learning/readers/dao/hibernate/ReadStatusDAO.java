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

import com.learning.readers.dao.IReadStatusDAO;
import com.learning.readers.entity.ReadStatus;
import com.learning.readers.model.ReadStatusValueModel;

@Repository
public class ReadStatusDAO implements IReadStatusDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public List<ReadStatusValueModel> listValues() {
		
		return hibernateTemplate.execute(new HibernateCallback<List<ReadStatusValueModel>>() {

			@Override
			public List<ReadStatusValueModel> doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<ReadStatusValueModel> criteriaQuery = criteriaBuilder.createQuery(ReadStatusValueModel.class);
				
				Root<ReadStatus> readStatusRoot = criteriaQuery.from(ReadStatus.class);
				criteriaQuery.select(criteriaBuilder.construct(ReadStatusValueModel.class, 
						readStatusRoot.<Integer>get("id"),
						readStatusRoot.<String>get("value")));
				
				return session.createQuery(criteriaQuery).list();
			}
		});
	}

	@Override
	public ReadStatus findByName(String readStatus) {
		
		return hibernateTemplate.execute(new HibernateCallback<ReadStatus>() {

			@Override
			public ReadStatus doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<ReadStatus> criteriaQuery = criteriaBuilder.createQuery(ReadStatus.class);
				
				Root<ReadStatus> readStatusRoot = criteriaQuery.from(ReadStatus.class);
				criteriaQuery
					.select(readStatusRoot)
					.where(criteriaBuilder.equal(readStatusRoot.<String>get("value"), readStatus));
				
				return session.createQuery(criteriaQuery).uniqueResult();
			}
		});
	}

	@Override
	public ReadStatus findById(int readStatusId) {
		return hibernateTemplate.load(ReadStatus.class, readStatusId);
	}
	
}
