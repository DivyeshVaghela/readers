package com.learning.readers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.readers.dao.IUserDAO;
import com.learning.readers.entity.User;
import com.learning.readers.model.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	IUserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDAO.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Username not found");
		} else if (!user.getEnabled()) {
			throw new DisabledException("Please varify your email address, before login");
		}
		return new CustomUserDetails(user);
	}
}
