package com.learning.readers.dao.hibernate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.learning.readers.dao.IShareActionDAO;
import com.learning.readers.entity.ShareAction;
import com.learning.readers.model.ShareActionValueModel;

@Repository
public class ShareActionDAO implements IShareActionDAO {

	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public List<ShareActionValueModel> listValues() {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ShareActionValueModel> criteriaQuery = criteriaBuilder.createQuery(ShareActionValueModel.class);
			
			Root<ShareAction> shareActionRoot = criteriaQuery.from(ShareAction.class);
			criteriaQuery.select(criteriaBuilder.construct(ShareActionValueModel.class,
					shareActionRoot.<Integer>get("id"),
					shareActionRoot.<String>get("value")));
			
			return session.createQuery(criteriaQuery).list();
		});
	}

}
