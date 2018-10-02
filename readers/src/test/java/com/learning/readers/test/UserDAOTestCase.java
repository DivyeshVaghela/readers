package com.learning.readers.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.learning.readers.dao.IRoleDAO;
import com.learning.readers.dao.IUserDAO;
import com.learning.readers.dao.hibernate.RoleDAO;
import com.learning.readers.dao.hibernate.UserDAO;
import com.learning.readers.entity.Role;
import com.learning.readers.entity.User;

public class UserDAOTestCase {

	private static AnnotationConfigApplicationContext context;
	private static IUserDAO userDAO;
	private static IRoleDAO roleDAO;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("com.learning.readers");
		context.refresh();
		
		userDAO = context.getBean("userDAO", IUserDAO.class);
		roleDAO = context.getBean("roleDAO", IRoleDAO.class);
	}
	
	//@Test
	public void testListUser() {
		
		List<User> userList = userDAO.list();
		assertEquals("Successfull", 2, userList.size());
	}
	
	//@Test
	public void createUser() {
		
		User user = new User();
		user.setUsername("shankar");
		user.setEmail("shankar@gmail.com");
		user.setEnabled(true);
		user.setPassword("123456");
		
		assertEquals("New user created Successfully", (Integer)2, userDAO.create(user));
	}
	
	//@Test
	public void createUserWithExistingRole() {
		
		User user = new User();
		user.setUsername("gagan");
		user.setEmail("ganag@gmail.com");
		user.setPassword("123456");
		
		Role roleAdmin = roleDAO.get("ADMIN");
		
		if (roleAdmin != null) {
			System.out.println("Within roleAdmin");
			user.getRoles().add(roleAdmin);
			roleAdmin.getUsers().add(user);
		}
		
		Role roleReader = roleDAO.get("READER");
		
		if (roleReader != null) {
			System.out.println("Within roleReader");
			user.getRoles().add(roleReader);
			roleReader.getUsers().add(user);
		}
		
		assertEquals("New User created successfully with existing roles", (Integer)13, userDAO.create(user));
	}
	
	//@Test
	public void getUserByUsername() {
		
		assertEquals("User found successfully", "ganesh@gmail.com", userDAO.findByUsername("ganesh").getEmail());
	}
	
	//@Test
	public void getUserByEmail() {
		
		assertEquals("User found successfully", "ganesh@gmail.com", userDAO.findByEmail("ganesh@gmail.com").getEmail());
	}
	
	@Test
	public void usernameExists() {
		
		assertEquals("Done", false, userDAO.exists("username", "ganesha"));
	}
}
