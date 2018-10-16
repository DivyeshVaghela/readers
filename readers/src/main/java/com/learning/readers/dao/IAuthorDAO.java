package com.learning.readers.dao;

import java.util.List;

import com.learning.readers.entity.Author;
import com.learning.readers.model.AuthorNameModel;

public interface IAuthorDAO {

	List<AuthorNameModel> listAutherNames();
	List<Author> findByField(String fieldName, Object value);
	Author findById(int authorId);
}
