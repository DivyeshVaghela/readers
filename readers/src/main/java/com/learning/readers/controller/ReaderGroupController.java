package com.learning.readers.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.readers.dao.IBookDAO;
import com.learning.readers.dao.IReaderGroupDAO;
import com.learning.readers.dao.IUserDAO;
import com.learning.readers.entity.GroupBook;
import com.learning.readers.entity.ReaderGroup;
import com.learning.readers.model.CreateReaderGroupModel;
import com.learning.readers.model.ReaderGroupOverviewModel;
import com.learning.readers.model.UserModel;
import com.learning.readers.util.SortOrder;
import com.learning.readers.validator.ReaderGroupModelValidator;

@Controller
@RequestMapping(value = "/group")
public class ReaderGroupController {
	
	@Autowired
	private IReaderGroupDAO readerGroupDAO;
	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IBookDAO bookDAO;
	
	@Autowired
	private ReaderGroupModelValidator readerGroupModelValidator;
	
	@RequestMapping(value="/my")
	public ModelAndView groupsCreatedByMe(HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");

		mv.addObject("pageTitle", "Reader Groups");
		mv.addObject("contentPagePath", "./reader-group/list.jsp");
		
		List<ReaderGroupOverviewModel> groups = readerGroupDAO.createdByMe(userModel.getUserId(), "creationTime", SortOrder.DESC);
		mv.addObject("readerGroups", groups);
	
		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Groups", "/group");
		breadcrumbItems.put("My Groups", "/group/my");
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
		return mv;
	}
	
	@RequestMapping(value= {"","/"})
	public ModelAndView groups(HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");

		mv.addObject("pageTitle", "Reader Groups");
		mv.addObject("contentPagePath", "./reader-group/list.jsp");
		
		List<ReaderGroupOverviewModel> groups = readerGroupDAO.groupsByMember(userModel.getUserId(), "creationTime", SortOrder.DESC);
		mv.addObject("readerGroups", groups);
	
		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Groups", "/group");
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
		return mv;
	}
	
	private void populateCreateModel(ModelAndView mv, CreateReaderGroupModel readerGroupModel, UserModel userModel) {
		
		mv.setViewName("blankMasterPage");
		mv.addObject("pageTitle", "Reader Group");
		mv.addObject("contentPagePath", "./reader-group/create.jsp");
		
		if (readerGroupModel == null) {
			readerGroupModel = new CreateReaderGroupModel();
		}
		readerGroupModel.setUserList(userDAO.listNameEmail(userModel.getUserId()));
		readerGroupModel.setBookList(bookDAO.listMyBooksNameEdition(userModel.getUserId(), "name", SortOrder.ASC, null, null));
		mv.addObject("readerGroupModel", readerGroupModel);
		
		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Groups", "/group");
		breadcrumbItems.put("New", "/group/create");
		mv.addObject("breadcrumbItems", breadcrumbItems);
	}
	
	@RequestMapping(value = {"/create"})
	public ModelAndView create(HttpSession httpSession) {
		
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		
		ModelAndView mv = new ModelAndView();
		populateCreateModel(mv, null, userModel);
		
		return mv;
	}
	
	@RequestMapping(value={"/create"}, method = RequestMethod.POST)
	public ModelAndView create(@Valid @ModelAttribute("readerGroupModel") CreateReaderGroupModel readerGroupModel,
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes,
			HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView();
		
		readerGroupModelValidator.validate(readerGroupModel, bindingResult);
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		if (bindingResult.hasErrors()) {
			populateCreateModel(mv, readerGroupModel, userModel);
			
			return mv;
		}

		List<Integer> memberIds = new ArrayList<>();
		readerGroupModel.getSelectedMemberdId()
			.forEach(memberId -> {
				memberIds.add(memberId);
			});

		List<Integer> bookIds = new ArrayList<>();
		readerGroupModel.getSelectedBookId()
			.forEach(bookId -> {
				bookIds.add(bookId);
			});
		
		try {
			readerGroupDAO.create(readerGroupModel.getName(), userModel.getUserId(), memberIds, bookIds);
			
			redirectAttributes.addFlashAttribute("successMessage", "New readers' group created successfully.");
			mv.setViewName("redirect:/");
		} catch (Exception exp) {
			exp.printStackTrace();
			mv.addObject("errorMessage", "There was some problem in creating new group, plese try again.");
			populateCreateModel(mv, readerGroupModel, userModel);
		}
		
		return mv;
	}
	

	private void populateGroupDetails(ModelAndView mv, int groupId, UserModel userModel, ReaderGroup groupDetails,
			CreateReaderGroupModel createReaderGroupModel, RedirectAttributes redirectAttributes) {

		mv.setViewName("blankMasterPage");
	
		if (groupDetails == null) {
			groupDetails = readerGroupDAO.groupDetailsFullLoaded(groupId, userModel.getUserId(), true);
			
			if (groupDetails == null) {
				redirectAttributes.addFlashAttribute("errorMessage", "The resource you are trying to find is not present, please check your request.");
				mv.setViewName("redirect:/");
				return;
			}
		}
		
		mv.addObject("group", groupDetails);
		mv.addObject("pageTitle", groupDetails.getName());

		if (createReaderGroupModel == null) {
			createReaderGroupModel = new CreateReaderGroupModel();
			createReaderGroupModel.setId(groupDetails.getId());
		}
		createReaderGroupModel.setName(groupDetails.getName());
		createReaderGroupModel.setBookList(bookDAO.listMyBooksNameEdition(userModel.getUserId(), "name", SortOrder.ASC, null, null));
		createReaderGroupModel.setUserList(userDAO.listNameEmail(userModel.getUserId()));
		
		createReaderGroupModel.setSelectedBookId(
				groupDetails.getBooks()
					.stream()
					.map(book -> book.getBookId())
					.collect(Collectors.toList()));
		
		createReaderGroupModel.setSelectedMemberdId(
				groupDetails.getMembers()
					.stream()
					.map(member -> member.getMemberId())
					.collect(Collectors.toList()));
		
		mv.addObject("readerGroupModel", createReaderGroupModel);

		mv.addObject("contentPagePath", "./reader-group/details.jsp");
		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Groups", "/group");
		breadcrumbItems.put(groupDetails.getName(), "/group/"+groupId);
		mv.addObject("breadcrumbItems", breadcrumbItems);
	}
	
	@RequestMapping(value="/{groupId}")
	public ModelAndView groupDetails(@PathVariable("groupId")int groupId,
			RedirectAttributes redirectAttributes,
			HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView();
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");

		mv.setViewName("blankMasterPage");
	
		ReaderGroup groupDetails = readerGroupDAO.groupDetailsFullLoaded(groupId, true);
		
		if (groupDetails == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "The resource you are trying to find is not present, please check your request.");
			mv.setViewName("redirect:/");
			return mv;
		}
		
		mv.addObject("group", groupDetails);

		mv.addObject("contentPagePath", "./reader-group/details.jsp");
		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Groups", "/group");
		breadcrumbItems.put(groupDetails.getName(), "/group/"+groupId);
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
		return mv;
	}
	
	@RequestMapping(value="/my/{groupId}")
	public ModelAndView myGroupDetails(@PathVariable("groupId")int groupId,
			HttpSession httpSession,
			RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView();
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");

		populateGroupDetails(mv, groupId, userModel, null, null, redirectAttributes);
		return mv;
	}
	
	@RequestMapping(value = "/my/{groupId}/editGroupBooks", method=RequestMethod.POST)
	public ModelAndView updateGroupBooks(@PathVariable("groupId")int groupId,
			@Valid @ModelAttribute("readerGroupModel") CreateReaderGroupModel readerGroupModel,
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes,
			HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView();
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errorMessage", "There was some problem while validating the form, please try again.");
			populateGroupDetails(mv, groupId, userModel, null, readerGroupModel, redirectAttributes);
			
			mv.setViewName("redirect:/group/"+groupId);
			return mv;
		}
		
		try {
			readerGroupDAO.updateBooks(readerGroupModel.getId(), readerGroupModel.getSelectedBookId());
			redirectAttributes.addFlashAttribute("successMessage", "Group books updated successfully.");
			mv.setViewName("redirect:/group/"+groupId);
			
		} catch (Exception exp) {
			exp.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "There was some problem while updating the group books, please try again.");
			mv.setViewName("redirect:/group/"+groupId);
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/my/{groupId}/editGroupMembers", method=RequestMethod.POST)
	public ModelAndView updateGroupMembers(@PathVariable("groupId")int groupId,
			@Valid @ModelAttribute("readerGroupModel") CreateReaderGroupModel readerGroupModel,
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes,
			HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView();
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errorMessage", "There was some problem while validating the form, please try again.");
			populateGroupDetails(mv, groupId, userModel, null, readerGroupModel, redirectAttributes);
			
			mv.setViewName("redirect:/group/"+groupId);
			return mv;
		}
		
		try {
			readerGroupDAO.updateMembers(readerGroupModel.getId(), readerGroupModel.getSelectedMemberdId());
			redirectAttributes.addFlashAttribute("successMessage", "Group members updated successfully.");
			mv.setViewName("redirect:/group/"+groupId);
			
		} catch (Exception exp) {
			exp.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "There was some problem while updating the group members, please try again.");
			mv.setViewName("redirect:/group/"+groupId);
		}
		
		return mv;
	}

	@RequestMapping(value = "/{groupId}/book/{groupBookId}")
	public ModelAndView groupBookDetails(@PathVariable("groupId")int groupId, @PathVariable("groupBookId")int groupBookId,
			RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		
		GroupBook groupBook = readerGroupDAO.getGroupBook(groupBookId, groupId, true);
		
		if (groupBook == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "The resource you are trying to find is not present, please check your request.");
			mv.setViewName("redirect:/");
			return mv;
		}
		
		mv.addObject("contentPagePath", "./reader-group/bookDetails.jsp");
		mv.addObject("pageTitle", groupBook.getBook().getFullName() + " | " + groupBook.getGroup().getName());
		
		mv.addObject("groupBook", groupBook);
		
		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Groups", "/group");
		breadcrumbItems.put(groupBook.getGroup().getName(), "/group/"+groupId);
		breadcrumbItems.put(groupBook.getBook().getFullName(), "/group/"+groupId+"/book/"+groupBookId);
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
		return mv;
	}
}
