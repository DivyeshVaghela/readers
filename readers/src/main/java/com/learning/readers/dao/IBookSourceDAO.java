package com.learning.readers.dao;

import java.util.List;

import com.learning.readers.entity.BookSource;
import com.learning.readers.model.BookSourceValueModel;

public interface IBookSourceDAO {

	List<BookSourceValueModel> listNames();
	void saveOrUpdate(BookSource bookSource);
}
