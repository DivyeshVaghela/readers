package com.learning.readers.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value = {"/", "/index", "/home"})
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView("masterPage");
		mv.addObject("grettingMessage", "Welcome to Readers'");
		
		return mv;
	}
	
	@RequestMapping(value = {"/secured"})
	public ModelAndView securedIndex() {
		
		ModelAndView mv = new ModelAndView("masterPage");
		mv.addObject("grettingMessage", "Welcome to the secured area of Readers'");
		
		return mv;
	}
	
	@PreAuthorize(value = "hasAnyAuthority('ADMIN')")
	@RequestMapping(value = {"/admin"})
	public ModelAndView securedAdminIndex() {
		
		ModelAndView mv = new ModelAndView("masterPage");
		mv.addObject("grettingMessage", "Welcome admin to Readers'");
		
		return mv;
	}
}
