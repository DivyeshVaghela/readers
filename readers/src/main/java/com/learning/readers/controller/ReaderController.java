package com.learning.readers.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.learning.readers.dao.IBookDAO;
import com.learning.readers.model.ReaderHomeModel;
import com.learning.readers.model.UserModel;
import com.learning.readers.util.ConstantUtil;
import com.learning.readers.util.SortOrder;

@Controller
public class ReaderController {

	@Autowired
	IBookDAO bookDAO;
	
	@RequestMapping(value = {"", "/", "/home", "/index"})
	public ModelAndView home(HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		mv.addObject("contentPagePath", "./reader/home.jsp");

		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
		ReaderHomeModel readerHomeModel = new ReaderHomeModel();
		
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		readerHomeModel.setResentlyAdded(bookDAO.list(userModel.getUserId(), "creationTime", SortOrder.DESC, 0, ConstantUtil.BOOK_PER_CAROUSEL_SCREEN * 3));
		//readerHomeModel.setResentlyAdded(bookDAO.list(userModel.getUserId(), ConstantUtil.BOOK_PER_CAROUSEL_SCREEN * 3));
		//readerHomeModel.setResentlyAdded(bookDAO.list(2, 0));
		
		mv.addObject("model", readerHomeModel);
		
		return mv;
	}
}
