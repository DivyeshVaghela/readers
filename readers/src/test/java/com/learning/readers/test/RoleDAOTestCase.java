package com.learning.readers.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.learning.readers.dao.IRoleDAO;
import com.learning.readers.dao.hibernate.RoleDAO;
import com.learning.readers.entity.Role;

public class RoleDAOTestCase {

	private static AnnotationConfigApplicationContext context;
	private static IRoleDAO roleDAO;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("com.learning.readers");
		context.refresh();
		
		roleDAO = context.getBean("roleDAO", RoleDAO.class);
	}
	
	//@Test
	public void createRole() {
		
		Role role = new Role();
		role.setName("READER");
		role.setDetails("Reader or client will have the access to his/her account only.");
		
		assertEquals("New role created successfully", (Integer)2 ,roleDAO.create(role));
	}
	
	@Test
	public void getRolesWithUsers() {
		
		Role role = roleDAO.getWithUsers("ADMIN");
		assertEquals("ADMIN role with users got wuccessfully.", "ADMIN", role.getName());
		assertEquals("ADMIN user count.", 0, role.getUsers().size());
	}
}
