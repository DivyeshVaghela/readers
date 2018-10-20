package com.learning.readers.dao;

import java.util.List;

import com.learning.readers.entity.User;
import com.learning.readers.model.UserNameEmailModel;

public interface IUserDAO {

	List<User> list();
	List<UserNameEmailModel> listNameEmail(Integer exceptUserId);
	
	boolean exists(String fieldName, Object value);
	User findByUsername(String username);
	User findByEmail(String email);
	User findById(int userId);
	Integer create(User user);
}
