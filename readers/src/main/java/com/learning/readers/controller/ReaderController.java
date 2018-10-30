package com.learning.readers.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.learning.readers.dao.IBookDAO;
import com.learning.readers.dao.IBookShareDAO;
import com.learning.readers.model.BookOverviewModel;
import com.learning.readers.model.ReaderHomeModel;
import com.learning.readers.model.UserModel;
import com.learning.readers.util.ConstantUtil;
import com.learning.readers.util.FieldNameValue;
import com.learning.readers.util.SortOrder;

@Controller
public class ReaderController {

	@Autowired
	IBookDAO bookDAO;
	@Autowired
	IBookShareDAO bookShareDAO;
	
	@RequestMapping(value = {"", "/", "/home", "/index"})
	public ModelAndView home(HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		mv.addObject("contentPagePath", "./reader/home.jsp");

		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
		ReaderHomeModel readerHomeModel = new ReaderHomeModel();
		
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");

		FieldNameValue<String, Object> userIdRestriction = new FieldNameValue<String, Object>();
		userIdRestriction.setName("userId");
		userIdRestriction.setValue(userModel.getUserId());
		
		readerHomeModel.setResentlyAdded(bookDAO.list("creationTime", SortOrder.DESC, 0, ConstantUtil.BOOK_PER_CAROUSEL_SCREEN * 3, userIdRestriction));
		readerHomeModel.setWishList(bookDAO.list(userModel.getUserId(), 1, "creationTime", SortOrder.DESC, 0, ConstantUtil.BOOK_PER_CAROUSEL_SCREEN * 2));
		readerHomeModel.setSharedToMeBooks(bookShareDAO.sharedToMe(userModel.getUserId(), "creationTime", SortOrder.ASC, 0, ConstantUtil.BOOK_PER_CAROUSEL_SCREEN * 2));
		
		mv.addObject("model", readerHomeModel);
		
		return mv;
	}

	@RequestMapping(value="/wishlist")
	public ModelAndView wishList(HttpSession httpSession) {
	
		ModelAndView mv = new ModelAndView("blankMasterPage");
		mv.addObject("contentPagePath", "./reader/wishlist.jsp");
		
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		
		List<BookOverviewModel> wishlist = bookDAO.list(userModel.getUserId(), 1, "creationTime", SortOrder.ASC, null, null);
		mv.addObject("wishlist", wishlist);

		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Wishlist", "/wishlish");
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
		return mv;
	}

	@RequestMapping(value = "/shared")
	public ModelAndView shared(HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		mv.addObject("contentPagePath", "./reader/sharedToMe.jsp");
		
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		
		List<BookOverviewModel> sharedToMeBookList = bookShareDAO.sharedToMe(userModel.getUserId(), "creationTime", SortOrder.ASC, null, null);
		mv.addObject("sharedToMeBookList", sharedToMeBookList);

		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Shared", "/shared");
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
		return mv;
	}
	
	@RequestMapping(value = "/shared/history")
	public ModelAndView shareHistory(HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		mv.addObject("contentPagePath", "./reader/shareHistory.jsp");
		
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		
		List<BookOverviewModel> shareHistory = bookShareDAO.shareHistory(userModel.getUserId(), "creationTime", SortOrder.DESC, null, null);
		mv.addObject("shareHistoryList", shareHistory);

		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Share History", "/shared/history");
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
		return mv;
	}
}
