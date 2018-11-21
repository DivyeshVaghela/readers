package com.learning.readers.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.readers.dao.IBookDAO;
import com.learning.readers.dao.IBookShareDAO;
import com.learning.readers.dao.IReadlistDAO;
import com.learning.readers.dao.IUserDAO;
import com.learning.readers.entity.User;
import com.learning.readers.model.BookOverviewModel;
import com.learning.readers.model.ChangePasswordModel;
import com.learning.readers.model.ReaderHomeModel;
import com.learning.readers.model.UserModel;
import com.learning.readers.util.ConstantUtil;
import com.learning.readers.util.FieldNameValue;
import com.learning.readers.util.SortOrder;

@Controller
public class ReaderController {

	@Autowired
	IUserDAO userDAO;
	@Autowired
	IBookDAO bookDAO;
	@Autowired
	IBookShareDAO bookShareDAO;
	@Autowired
	IReadlistDAO readlistDAO;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	HttpSession httpSession;
	
	@RequestMapping(value = {"", "/", "/home", "/index"})
	public ModelAndView home(HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		mv.addObject("contentPagePath", "./reader/home.jsp");
		mv.addObject("pageTitle", "Home");

		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
		ReaderHomeModel readerHomeModel = new ReaderHomeModel();
		
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");

		FieldNameValue<String, Object> userIdRestriction = new FieldNameValue<>();
		userIdRestriction.setName("userId");
		userIdRestriction.setValue(userModel.getUserId());
		
		FieldNameValue<String, Object> enabledRestriction = new FieldNameValue<>();
		enabledRestriction.setName("enabled");
		enabledRestriction.setValue(true);
		
		readerHomeModel.setResentlyAdded(bookDAO.list("creationTime", SortOrder.DESC, 0, ConstantUtil.BOOK_PER_CAROUSEL_SCREEN * 3, new ArrayList<FieldNameValue<String, Object>>() {{ add(userIdRestriction); add(enabledRestriction); }}));
		readerHomeModel.setWishList(bookDAO.list(userModel.getUserId(), 1, "creationTime", SortOrder.DESC, 0, ConstantUtil.BOOK_PER_CAROUSEL_SCREEN * 2));
		readerHomeModel.setSharedToMeBooks(bookShareDAO.sharedToMe(userModel.getUserId(), "creationTime", SortOrder.ASC, 0, ConstantUtil.BOOK_PER_CAROUSEL_SCREEN * 2));
		readerHomeModel.setReadlists(readlistDAO.list(userModel.getUserId(), "creationTime", SortOrder.DESC, null, null));
		
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

	private void populateChangePasswordModel(ModelAndView mv, ChangePasswordModel changePasswordModel) {
		
		if (changePasswordModel == null) {
			changePasswordModel = new ChangePasswordModel();
		}
		mv.addObject("changePasswordModel", changePasswordModel);
		
		mv.addObject("contentPagePath", "./reader/change-password.jsp");
	}
	
	@RequestMapping(value = {"change-password"}, method=RequestMethod.GET)
	public ModelAndView changePassword() {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		populateChangePasswordModel(mv, null);
		
		return mv;
	}
	
	@RequestMapping(value = {"change-password"}, method=RequestMethod.POST)
	public ModelAndView changePassword(@Valid @ModelAttribute("changePasswordModel") ChangePasswordModel changePasswordModel,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView();
		
		if (bindingResult.hasErrors()) {
			
			populateChangePasswordModel(mv, changePasswordModel);
			mv.setViewName("blankMasterPage");
			return mv;
		}
		
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		User user = userDAO.findById(userModel.getUserId());
		
		if (!passwordEncoder.matches(changePasswordModel.getCurrentPassword(), user.getPassword())) {
			populateChangePasswordModel(mv, null);
			mv.addObject("changePasswordError", "Invalid Current password");
			mv.setViewName("blankMasterPage");
			
		} else if (passwordEncoder.matches(changePasswordModel.getNewPassword(), user.getPassword())) {
			populateChangePasswordModel(mv, null);
			mv.addObject("changePasswordError", "New password can not be same as old password");
			mv.setViewName("blankMasterPage");
			
		} else {
			user.setPassword(passwordEncoder.encode(changePasswordModel.getNewPassword()));
			userDAO.update(user);
			redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully.");
			
			mv.setViewName("redirect:/");
		}
		
		return mv;
	}
}
