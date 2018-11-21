package com.learning.readers.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.readers.dao.IBookShareDAO;
import com.learning.readers.dao.IReaderGroupDAO;
import com.learning.readers.dao.IShareActionDAO;
import com.learning.readers.entity.BookShare;
import com.learning.readers.entity.GroupBook;
import com.learning.readers.model.SetShareActionModel;
import com.learning.readers.model.ShareActionValueModel;
import com.learning.readers.model.ShareBookModel;
import com.learning.readers.model.UserModel;

@Controller
@RequestMapping(value="/book")
public class BookShareController {

	@Autowired
	IBookShareDAO bookShareDAO;
	@Autowired
	IReaderGroupDAO readerGroupDAO;
	@Autowired
	IShareActionDAO shareActionDAO;
	
	@RequestMapping(value="/shared/{bookShareId}", method = RequestMethod.GET)
	public ModelAndView sharedBookDetails(@PathVariable("bookShareId")int bookShareId,
			RedirectAttributes redirectAttributes,
			HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		BookShare bookShare = bookShareDAO.findById(bookShareId, userModel.getUserId());
		
		if (bookShare == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "The resource you are trying to find is not present, please check your request.");
			mv.setViewName("redirect:/");
			return mv;
		}
		
		mv.addObject("bookShare", bookShare);

		mv.addObject("contentPagePath", "./book/bookShareDetails.jsp");
		mv.addObject("pageTitlebook", bookShare.getBook().getFullName());
		mv.addObject("shareActionValues", shareActionDAO.listValues());
		
		List<ShareActionValueModel> shareActionValues = shareActionDAO.listValues();
		SetShareActionModel setShareActionModel = new SetShareActionModel();
		setShareActionModel.setBookShareId(bookShare.getId());
		setShareActionModel.setShareActionList(shareActionValues);
		mv.addObject("shareAction", setShareActionModel);
		
		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Book", "/");
		breadcrumbItems.put("Shared", "/shared");
		breadcrumbItems.put(bookShare.getBook().getFullName(), "book/shared"+bookShare.getBook().getId());
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
		return mv;
	}
	
	@RequestMapping(value="/share", method=RequestMethod.POST)
	public ModelAndView shareBook(@ModelAttribute("shareBook") ShareBookModel shareBook,
			RedirectAttributes redirectAttributes,
			HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView();
		
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		
		// Share the book to Readers
		List<BookShare> bookShareList = new ArrayList<>();
		for (int receiverUserId : shareBook.getSelectedUsers()) {
			BookShare bookShare = new BookShare();
			bookShare.setSenderId(userModel.getUserId());
			bookShare.setReceiverId(receiverUserId);
			bookShare.setBookId(shareBook.getBookId());
			
			bookShareList.add(bookShare);
		}
		
		// Share the Book in Groups
		List<GroupBook> groupBookList = new ArrayList<>();
		for (int groupId  : shareBook.getSelectedGroups()) {
			GroupBook groupBook = new GroupBook();
			groupBook.setGroupId(groupId);
			groupBook.setBookId(shareBook.getBookId());
			
			groupBookList.add(groupBook);
		}
		
		try {
			if (bookShareList.size() > 0 || groupBookList.size() > 0) {
				String successMessage = "Book shared with ";
				if (bookShareList.size() > 0) {
					bookShareDAO.share(bookShareList);
					successMessage +=  bookShareList.size() + " readers";
				}
				if (groupBookList.size() > 0) {
					bookShareDAO.shareToGroups(groupBookList);
					if (bookShareList.size() > 0)
						successMessage +=  " and ";
					successMessage +=  groupBookList.size() + " groups";
				}
				successMessage += " successfully";
				
				redirectAttributes.addFlashAttribute("successMessage", successMessage);
			} else {
				String errorMessage = "No any reader or group were selected to share with.";
				redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "There was some problem in sharing book, please try again.");
		}

		mv.setViewName("redirect:/book/"+shareBook.getBookId());
		return mv;
	}
	
	@RequestMapping(value="/shared/{bookShareId}", method=RequestMethod.POST)
	public ModelAndView takeShareAction(@ModelAttribute("shareAction") SetShareActionModel shareAction, 
			RedirectAttributes redirectAttributes, 
			HttpSession session) {
		
		ModelAndView mv = new ModelAndView();
		
		//UserModel userModel = (UserModel)session.getAttribute("userModel");
		
		try {
			bookShareDAO.takeShareAction(shareAction.getBookShareId(), shareAction.getShareActionId());
			redirectAttributes.addFlashAttribute("successMessage", "Action taken successfully.");
		} catch (Exception exp) {
			exp.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "There was some problem while taking action, please try again.");
		}
		mv.setViewName("redirect:/book/shared/"+shareAction.getBookShareId());
		return mv;
	}
	
}
