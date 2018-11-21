package com.learning.readers.dao;

import java.util.List;

import com.learning.readers.model.SecretQuestionModel;

public interface ISecretQuestionDAO {

	List<SecretQuestionModel> secretQuestionsOnly(); 
}
