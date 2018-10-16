package com.learning.readers.dao;

import java.util.List;

import com.learning.readers.entity.Publication;
import com.learning.readers.model.PublicationNameModel;

public interface IPublicationDAO {

	List<PublicationNameModel> listNames();
	Publication findByName(String publicationName);
	Publication findById(int publicationId);
}
