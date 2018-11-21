package com.learning.readers.controller;

import java.util.Arrays;
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
import com.learning.readers.dao.IReadlistDAO;
import com.learning.readers.entity.Readlist;
import com.learning.readers.model.CreateReadlistModel;
import com.learning.readers.model.ReadlistOverviewModel;
import com.learning.readers.model.UserModel;
import com.learning.readers.util.SortOrder;
import com.learning.readers.validator.ReadlistModelValidator;

@Controller
@RequestMapping(value="/readlist")
public class ReadlistController {

	@Autowired
	private IReadlistDAO readlistDAO;
	@Autowired
	private IBookDAO bookDAO;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private ReadlistModelValidator readlistModelValidator;
	
	@RequestMapping(value= {"","/"})
	public ModelAndView list() {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		
		List<ReadlistOverviewModel> readlists = readlistDAO.list(userModel.getUserId(), "creationTime", SortOrder.DESC, null, null);
		mv.addObject("readlists", readlists);
		
		mv.addObject("contentPagePath", "./readlist/list.jsp");
		mv.addObject("pageTitle", "Readlists");
		
		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Readlist", "/readlist");
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
		return mv;
	}
	
 	private void populateCreateReadlist(ModelAndView mv, UserModel userModel, CreateReadlistModel createReadlistModel) {
		
		mv.addObject("contentPagePath", "./readlist/create.jsp");
		mv.addObject("pageTitle", "New Readlist");
		
		if (createReadlistModel == null) {
			createReadlistModel = new CreateReadlistModel();
		}
		createReadlistModel.setBookList(bookDAO.listMyBooksNameEdition(userModel.getUserId(), "name", SortOrder.ASC, null, null));
		mv.addObject("createReadlistModel", createReadlistModel);
		
		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Readlist", "/readlist");
		breadcrumbItems.put("Create", "/readlist/create");
		mv.addObject("breadcrumbItems", breadcrumbItems);
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create() {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");

		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		populateCreateReadlist(mv, userModel, null);
		
		return mv;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView create(@Valid @ModelAttribute("createReadlistModel")CreateReadlistModel createReadlistModel, 
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView();
		readlistModelValidator.validate(createReadlistModel, bindingResult);
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		
		if (bindingResult.hasErrors()) {
			mv.setViewName("blankMasterPage");
			populateCreateReadlist(mv, userModel, createReadlistModel);
			return mv;
		}

		try {
			Readlist readlist = new Readlist();
			readlist.setName(createReadlistModel.getName());
			readlist.setDetails(createReadlistModel.getDetails());
			readlist.setCreatorId(userModel.getUserId());
			List<Integer> selectedBookList = Arrays.stream(createReadlistModel.getSelectedBookIds()).boxed().collect(Collectors.toList());
			Integer newReadlistId = readlistDAO.create(readlist, selectedBookList);
			
			if (newReadlistId != null)
				redirectAttributes.addFlashAttribute("successMessage", "New readlist named '"+ createReadlistModel.getName() +"' created successfully");
			else
				redirectAttributes.addFlashAttribute("errorMessage", "There was some problem in creating new playlist, please try again");
			mv.setViewName("redirect:/");
		} catch (Exception exp) {
			exp.printStackTrace();
			
			mv.setViewName("blankMasterPage");
			mv.addObject("readlistCreateError", "There was some problem in creating new Readlist, please try again.");
		}
		
		return mv;
	}

	@RequestMapping(value="/my/{readlistId}")
	public ModelAndView readlistDetails(@PathVariable("readlistId")int readlistId, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		
		Readlist readlist = readlistDAO.getFullyloaded(readlistId, userModel.getUserId());
		
		if (readlist == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "The resource you are trying to find is not present, please check your request.");
			mv.setViewName("redirect:/");
			return mv;
		}
		mv.addObject("readlist", readlist);
		
		mv.addObject("contentPagePath", "./readlist/details.jsp");
		mv.addObject("pageTitle", readlist.getName() + " | Readlist");
		
		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Readlist", "/readlist");
		breadcrumbItems.put(readlist.getName(), "/readlist/" + readlistId);
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
		return mv;
	}

	
	private void populateEditReadlist(ModelAndView mv, UserModel userModel, int readlistId, CreateReadlistModel createReadlistModel) {
		
		if (createReadlistModel == null) {
			Readlist readlist = readlistDAO.getFullyloaded(readlistId, userModel.getUserId());
			
			createReadlistModel = new CreateReadlistModel();
			createReadlistModel.setId(readlist.getId());
			createReadlistModel.setName(readlist.getName());
			createReadlistModel.setDetails(readlist.getDetails());
			
			int[] selectedBookIds = readlist.getBooks().stream().mapToInt(book -> 
				book.getBook().getId()
			).toArray();
			
			createReadlistModel.setSelectedBookIds(selectedBookIds);
		}
		createReadlistModel.setBookList(bookDAO.listMyBooksNameEdition(userModel.getUserId(), "name", SortOrder.ASC, null, null));
		
		mv.addObject("createReadlistModel", createReadlistModel);
		mv.addObject("contentPagePath", "./readlist/create.jsp");
		mv.addObject("pageTitle", createReadlistModel.getName() + " | Readlist");
		
		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Readlist", "/readlist");
		breadcrumbItems.put(createReadlistModel.getName(), "/readlist/my/" + readlistId);
		breadcrumbItems.put("Readlist", "/readlist/my/"+ readlistId + "/edit");
		mv.addObject("breadcrumbItems", breadcrumbItems);
	}
	
	@RequestMapping(value="/my/{readlistId}/edit")
	public ModelAndView edit(@PathVariable("readlistId")int readlistId, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		
		populateEditReadlist(mv, userModel, readlistId, null);
		
		return mv;
	}
	
	@RequestMapping(value="/my/{readlistId}/edit", method=RequestMethod.POST)
	public ModelAndView edit(@PathVariable("readlistId")int readlistId,
			@Valid @ModelAttribute("createReadlistModel")CreateReadlistModel createReadlistModel,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView();
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		readlistModelValidator.validate(createReadlistModel, bindingResult);
		
		if (bindingResult.hasErrors()) {
			mv.setViewName("blankMasterPage");
			populateEditReadlist(mv, userModel, readlistId, createReadlistModel);
			
			return mv;
		}

		try {
			
			Readlist readlist = readlistDAO.getById(createReadlistModel.getId());
			List<Integer> updatedSelectedBookIds = Arrays.stream(createReadlistModel.getSelectedBookIds())
				.boxed()
				.collect(Collectors.toList());
			readlistDAO.update(readlist, updatedSelectedBookIds);
			
			mv.setViewName("redirect:/readlist/my/"+createReadlistModel.getId());
			redirectAttributes.addFlashAttribute("successMessage", "Readlist details updated successfully");
		} catch (Exception exp) {
			exp.printStackTrace();
			
			mv.setViewName("redirect:/");
			mv.addObject("errorMessage", "There was some problem in updating Readlist details, please try again.");
		}
		
		return mv;
	}
	
	@RequestMapping(value="/remove/{readlistId}", method=RequestMethod.POST)
	public ModelAndView remove(@PathVariable("readlistId")int readlistId, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("redirect:/");
		
		try {
			int removed = readlistDAO.remove(readlistId);
			
			if (removed <= 0) {
				redirectAttributes.addFlashAttribute("errorMessage", "There was some problem while removing the readlist, please try again.");
			} else {
				redirectAttributes.addFlashAttribute("successMessage", "Readlist removed successfully.");
			}
			
		} catch (Exception exp) {
			exp.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "There was some problem while removing the readlist, please try again.");
		}
		return mv;
	}
}
