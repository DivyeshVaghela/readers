package com.learning.readers.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.learning.readers.dao.IUserDAO;
import com.learning.readers.entity.User;
import com.learning.readers.model.UserModel;

@ControllerAdvice
public class GlobalController {

	@Autowired
	private HttpSession session;

	@Autowired
	private IUserDAO userDAO;

	@ModelAttribute("userModel")
	public UserModel getUserModel() {

		if (session.getAttribute("userModel") == null) {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (auth != null) {

				if (auth.getName() != null && !auth.getName().equals("anonymousUser")) {
					User user = userDAO.findByUsername(auth.getName());
					if (user != null) {
						UserModel userModel = new UserModel();
						userModel.setUserId(user.getId());
						userModel.setUsername(user.getUsername());
						userModel.setEmail(user.getEmail());
						userModel.setRoles(user.getRoles());
	
						session.setAttribute("userModel", userModel);
						return userModel;
					}
				}
			}
		}

		return (UserModel) session.getAttribute("userModel");
	}
}
