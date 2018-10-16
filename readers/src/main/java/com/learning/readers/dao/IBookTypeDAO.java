package com.learning.readers.dao;

import java.util.List;

import com.learning.readers.entity.BookType;
import com.learning.readers.model.BookTypeValueModel;

public interface IBookTypeDAO {

	List<BookTypeValueModel> listValues();
	BookType findByValue(String value);
	BookType findById(int id);
}
