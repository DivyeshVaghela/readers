package com.learning.readers.controller;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.learning.readers.dao.IRoleDAO;
import com.learning.readers.dao.IUserDAO;
import com.learning.readers.entity.Role;
import com.learning.readers.entity.User;
import com.learning.readers.model.ExceptionCapsule;
import com.learning.readers.model.SignUpReaderModel;
import com.learning.readers.validator.SignUpReaderModelValidator;

@Controller
public class SecurityController {
	
	@Autowired
	IUserDAO userDAO;
	
	@Autowired
	IRoleDAO roleDAO;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	SignUpReaderModelValidator signUpReaderModelValidator;

	@RequestMapping(value = {"/login"})
	public ModelAndView login(@RequestParam(name="error", required=false)String error, 
			@RequestParam(name="logout", required=false)String logout,
			@RequestParam(name="registered", required=false)String registered) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		
		if (error != null) {
			mv.addObject("loginError", "Invalid credentials");
		}
		if (logout != null) {
			mv.addObject("logoutSuccess", "Logged out successfully");
		}
		if (registered != null) {
			mv.addObject("registerSuccessMessage", "Registered successfully, you can login now.");
		}
		
		mv.addObject("pageTitle", "Login");
		mv.addObject("contentPagePath", "./security/login.jsp");
		
		return mv;
	}

	@RequestMapping(value = {"/logout"}, method=RequestMethod.POST)
	public ModelAndView logout(HttpServletRequest req, HttpServletResponse res) {
	
		//Fetch the authentication object;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(req, res, auth);
		}
		return new ModelAndView("redirect:/login?logout");
	}
	
	@RequestMapping(value = {"access-denied"})
	public ModelAndView accessDenied() {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		mv.addObject("pageTitle", "Access Denied");
		mv.addObject("contentPagePath", "./security/accessDenied.jsp");
		
		return mv;
	}
	
	@RequestMapping(value = {"/error"})
	public ModelAndView errorHandler(HttpServletRequest req) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		Integer errorCode = (Integer)req.getAttribute("javax.servlet.error.status_code");
		ExceptionCapsule exceptionCapsule = new ExceptionCapsule();
		exceptionCapsule.setCode(""+errorCode);
		
		switch (errorCode) {
			case 404:
				exceptionCapsule.setTitle("Not Found");
				exceptionCapsule.setMessage("The page or resource you requested is not available.");
				break;
				
			case 500:
				exceptionCapsule.setTitle("Server Problem");
				exceptionCapsule.setMessage("There was some problem while proccessing your request, please check your request properly and try again.");
				break;
				
			default:
				exceptionCapsule.setTitle("Request processing problem");
				exceptionCapsule.setMessage("There was some problem while proccessing your request, please check your request properly and try again.");
				break;
		}
		
		mv.addObject("pageTitle", errorCode);
		mv.addObject("exceptionInfo", exceptionCapsule);
		mv.addObject("contentPagePath", "./security/error.jsp");
		
		return mv;
	}
	
	@RequestMapping(value = {"/signup-reader"}, method=RequestMethod.GET)
	public ModelAndView signupReader() {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		prepareSignUpReader(mv, "blankMasterPage", new SignUpReaderModel());
		
		return mv;
	}
	
	protected void prepareSignUpReader(ModelAndView mv, String viewName, SignUpReaderModel signUpReaderModel) {
		
		mv.setViewName(viewName);
		mv.addObject("pageTitle", "Register Reader");		
		mv.addObject("contentPagePath", "./reader/signup.jsp");
		mv.addObject("signUpReaderModel", signUpReaderModel);
	}
	
	@RequestMapping(value = {"/signup-reader"}, method=RequestMethod.POST)
	public ModelAndView signupReader(@Valid @ModelAttribute("signUpReaderModel") SignUpReaderModel signUpReaderModel, BindingResult bindingResults) {
		
		ModelAndView mv = new ModelAndView();
		signUpReaderModelValidator.validate(signUpReaderModel, bindingResults);
		
		if (bindingResults.hasErrors()) {
			prepareSignUpReader(mv, "blankMasterPage", signUpReaderModel);			
			return mv;
		}
		
		User newReader = new User();
		newReader.setEmail(signUpReaderModel.getEmail());
		newReader.setUsername(signUpReaderModel.getUsername());
		newReader.setPassword(passwordEncoder.encode(signUpReaderModel.getPassword()));
		
		HashSet<Role> roles = new HashSet<>();
		roles.add(roleDAO.get("READER"));
		newReader.setRoles(roles);
		
		Integer newReaderId = userDAO.create(newReader);
		
		if (newReaderId != null) {
			mv.setViewName("redirect:/login?registered");
		}	else {
			prepareSignUpReader(mv, "blankMasterPage", signUpReaderModel);			
			mv.addObject("signUpError", "There was some problem in sign up, please try again.");
		}
		return mv;
	}
}
