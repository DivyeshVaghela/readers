package com.learning.readers.dao;

import java.util.List;

import com.learning.readers.entity.Role;

public interface IRoleDAO {

	List<Role> list();
	Integer create(Role role);
	Role get(int roleId);
	Role get(String roleName);
	Role getWithUsers(String roleName);
}
