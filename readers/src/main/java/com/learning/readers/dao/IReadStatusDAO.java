package com.learning.readers.dao;

import java.util.List;

import com.learning.readers.entity.ReadStatus;
import com.learning.readers.model.ReadStatusValueModel;

public interface IReadStatusDAO {

	List<ReadStatusValueModel> listValues();
	ReadStatus findByName(String readStatus);
	ReadStatus findById(int readStatusId);
}
