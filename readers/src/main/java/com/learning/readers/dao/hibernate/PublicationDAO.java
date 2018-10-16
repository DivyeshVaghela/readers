package com.learning.readers.dao.hibernate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.learning.readers.dao.IPublicationDAO;
import com.learning.readers.entity.Publication;
import com.learning.readers.model.PublicationNameModel;

@Repository
public class PublicationDAO implements IPublicationDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public List<PublicationNameModel> listNames() {
		
		return hibernateTemplate.execute(new HibernateCallback<List<PublicationNameModel>>() {

			@Override
			public List<PublicationNameModel> doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<PublicationNameModel> criteriaQuery = criteriaBuilder.createQuery(PublicationNameModel.class);
				
				Root<Publication> publicationRoot = criteriaQuery.from(Publication.class);
				criteriaQuery
					.select(criteriaBuilder.construct(PublicationNameModel.class, 
							publicationRoot.<Integer>get("id"), 
							publicationRoot.<String>get("name")));
				
				return session.createQuery(criteriaQuery).list();
			}
		});
	}

	@Override
	public Publication findByName(String publicationName) {
		
		return hibernateTemplate.execute(new HibernateCallback<Publication>() {

			@Override
			public Publication doInHibernate(Session session) throws HibernateException {
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Publication> criteriaQuery = criteriaBuilder.createQuery(Publication.class);
				
				Root<Publication> publicationRoot = criteriaQuery.from(Publication.class);
				criteriaQuery
					.select(publicationRoot)
					.where(criteriaBuilder.equal(publicationRoot.<String>get("name"), publicationName));
				
				return session.createQuery(criteriaQuery).uniqueResult();
			}
		});
	}

	@Override
	public Publication findById(int publicationId) {
		return hibernateTemplate.load(Publication.class, publicationId);
	}

}
