package com.learning.readers.dao.hibernate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.learning.readers.dao.ISecretQuestionDAO;
import com.learning.readers.entity.SecretQuestion;
import com.learning.readers.model.SecretQuestionModel;

@Repository
public class SecretQuestionDAO implements ISecretQuestionDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public List<SecretQuestionModel> secretQuestionsOnly() {
		
		return hibernateTemplate.execute(session -> {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<SecretQuestionModel> criteriaQuery = criteriaBuilder.createQuery(SecretQuestionModel.class);
			
			Root<SecretQuestion> secretQuestionRoot = criteriaQuery.from(SecretQuestion.class);
			criteriaQuery
				.select(criteriaBuilder.construct(SecretQuestionModel.class, 
						secretQuestionRoot.<Integer>get("id"),
						secretQuestionRoot.<String>get("question")))
				.orderBy(criteriaBuilder.asc(secretQuestionRoot.get("creationTime")));
			
			return session.createQuery(criteriaQuery).list();
			
		});
	}

}
