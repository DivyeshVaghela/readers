package com.learning.readers.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.learning.readers.dao.IAuthorDAO;
import com.learning.readers.dao.IBookDAO;
import com.learning.readers.dao.IBookShareDAO;
import com.learning.readers.dao.IBookSourceDAO;
import com.learning.readers.dao.IBookTypeDAO;
import com.learning.readers.dao.IPublicationDAO;
import com.learning.readers.dao.IReadStatusDAO;
import com.learning.readers.dao.IUserDAO;
import com.learning.readers.entity.Author;
import com.learning.readers.entity.Book;
import com.learning.readers.entity.BookShare;
import com.learning.readers.entity.BookSource;
import com.learning.readers.entity.BookUser;
import com.learning.readers.entity.Publication;
import com.learning.readers.entity.ReadDetail;
import com.learning.readers.entity.User;
import com.learning.readers.model.CreateAuthorModel;
import com.learning.readers.model.CreateBookBasicDetailsModel;
import com.learning.readers.model.CreateBookModel;
import com.learning.readers.model.CreateBookSourceModel;
import com.learning.readers.model.CreatePublicationModel;
import com.learning.readers.model.CreateReadDetailsModel;
import com.learning.readers.model.CreateReadProgressModel;
import com.learning.readers.model.ShareBookModel;
import com.learning.readers.model.UserModel;
import com.learning.readers.util.ConstantUtil;
import com.learning.readers.util.FileUploadUtil;
import com.learning.readers.validator.CreateBookModelValidator;
import com.learning.readers.validator.CreateBookSourceModelValidator;

@Controller
@RequestMapping(value = "/book")
public class BookController {

	@Autowired
	IAuthorDAO authorDAO;
	@Autowired
	IPublicationDAO publicationDAO;
	@Autowired
	IReadStatusDAO readStatusDAO;
	@Autowired
	IBookDAO bookDAO;
	@Autowired
	IBookTypeDAO bookTypeDAO;
	@Autowired
	IBookSourceDAO bookSourceDAO;
	@Autowired
	IUserDAO userDAO;
	@Autowired
	CreateBookModelValidator bookModelValidator;
	@Autowired
	CreateBookSourceModelValidator createBookSourceModelValidator;
	
	private void populateBookDetails(ModelAndView mv, Book book, CreateBookSourceModel createBookSourceModel, CreateReadProgressModel createReadProgressModel, UserModel userModel) {
		
		mv.addObject("contentPagePath", "./book/details.jsp");
		mv.addObject("pageTitle", book.getFullName());
		
		mv.addObject("book", book);
		
		if (book.getUser().getId() == userModel.getUserId()) {
			if (createBookSourceModel == null) {
				createBookSourceModel = new CreateBookSourceModel();
				BookSource source = book.getSource();
				if (source != null) {
					createBookSourceModel.setId(source.getId());
					createBookSourceModel.setBookTypeId(source.getType().getId());
					createBookSourceModel.setValue(source.getValue());
				}
			}
			createBookSourceModel.setBookTypeList(bookTypeDAO.listValues());
		}
		mv.addObject("bookSource", createBookSourceModel);
		
		if (createReadProgressModel == null) {
			
			BookUser bookUser = null;
			if (book.getBookReaders() != null) {
				bookUser = book.getBookReaders().stream()
					.filter(bookReader -> bookReader.getUser().getId() == userModel.getUserId())
					.findFirst()
					.orElse(null);
			}
			
			createReadProgressModel = new CreateReadProgressModel();
			ReadDetail readDetails = null;
			if (bookUser != null) {
				createReadProgressModel.setReadStatus(bookUser.getStatus().getId());
				readDetails = bookUser.getReadDetails();
			}
			CreateReadDetailsModel createReadDetailsModel = new CreateReadDetailsModel();
			if (readDetails == null) {
				readDetails = new ReadDetail();
			}
			
			createReadDetailsModel.setId(book.getId());
			createReadDetailsModel.setStartYear(readDetails.getStartYear());
			createReadDetailsModel.setStartMonth(readDetails.getStartMonth());
			createReadDetailsModel.setStartDate(readDetails.getStartDate());
			
			createReadDetailsModel.setEndYear(readDetails.getEndYear());
			createReadDetailsModel.setEndMonth(readDetails.getEndMonth());
			createReadDetailsModel.setEndDate(readDetails.getEndDate());
			
			createReadDetailsModel.setReview(readDetails.getReview());
			createReadDetailsModel.setRating(readDetails.getRating());
			
			createReadProgressModel.setReadDetails(createReadDetailsModel);
		}
		
		createReadProgressModel.setReadStatusList(readStatusDAO.listValues());
		mv.addObject("readProgress", createReadProgressModel);
		
		//Book sharing
		ShareBookModel shareBookModel = new ShareBookModel();
		shareBookModel.setBookId(book.getId());
		shareBookModel.setUserNameEmailList(userDAO.listNameEmail(userModel.getUserId()));
		mv.addObject("shareBook", shareBookModel);
		
		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Book", "book");
		breadcrumbItems.put(book.getFullName(), "book/"+book.getId());
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
	}
	
	@RequestMapping(value = {"/{bookId}"})
	public ModelAndView bookDetails(@PathVariable("bookId")int bookId,
			HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");

		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		Book book = bookDAO.findById(bookId, userModel.getUserId(), true);
		
		populateBookDetails(mv, book, null, null, userModel);
		
		return mv;
	}

	private void populateCreateBookModel(ModelAndView mv, CreateBookModel model) {
		
		mv.addObject("pageTitle", "New Book");
		
		model.setAuthorList(authorDAO.listAutherNames());
		model.setPublicationList(publicationDAO.listNames());
		model.setReadStatusList(readStatusDAO.listValues());
		
		if (model.getBookSource() == null) {
			model.setBookSource(new CreateBookSourceModel());
		}
		model.getBookSource().setBookTypeList(bookTypeDAO.listValues());
		//model.setBookTypeList(bookTypeDAO.listValues());
		mv.addObject("book", model);
	}
	
	@RequestMapping(value = {"/create"}, method=RequestMethod.GET)
	public ModelAndView createBook() {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		mv.addObject("contentPagePath", "./book/create.jsp");
		
		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Book", "/book");
		breadcrumbItems.put("New", "/book/create");
		mv.addObject("breadcrumbItems", breadcrumbItems);
		
		CreateBookModel model = new CreateBookModel();
		populateCreateBookModel(mv, model);
		
		return mv;
	}
	
	@RequestMapping(value = {"/create"}, method=RequestMethod.POST)
	public ModelAndView createBook(@Valid @ModelAttribute("book") CreateBookModel model, 
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes, 
			HttpSession session,
			HttpServletRequest req) {
		
		ModelAndView mv = new ModelAndView();
		bookModelValidator.validate(model, bindingResult);
		
		if (bindingResult.hasErrors()) {
			mv.setViewName("blankMasterPage");
			mv.addObject("contentPagePath", "./book/create.jsp");
			
			populateCreateBookModel(mv, model);
			return mv;
		}
		
		CreateBookBasicDetailsModel basicDetails = model.getBasicDetails();
		
		//Save the uploaded book cover image file
		String bookCoverPhoto;
		if (basicDetails.getBookCover() != null && !basicDetails.getBookCover().getOriginalFilename().isEmpty()) {
			bookCoverPhoto = basicDetails.getName() + "_" + (new Date()).getTime() + ".jpg";
			FileUploadUtil.uploadFile(req, basicDetails.getBookCover(), ConstantUtil.BOOK_COVER_DIR, bookCoverPhoto);
		} else {
			bookCoverPhoto = ConstantUtil.BOOK_DEFAULT_COVER;
		}
		
		//Save the book file
		CreateBookSourceModel bookSource = model.getBookSource();
		if (bookSource.getBookTypeId()==2 && bookSource.getBookFile() != null && !bookSource.getBookFile().getOriginalFilename().isEmpty()) {
			String bookFileName = basicDetails.getName() + " " + basicDetails.getEdition() + "_" + new Date().getTime() + "." + ConstantUtil.BOOK_FORMAT;
			FileUploadUtil.uploadFile(req, bookSource.getBookFile(), ConstantUtil.BOOK_DIR, bookFileName);
			model.getBookSource().setValue(bookFileName);
		}
		
		Book book = new Book();
		
		//Basic details
		book.setName(basicDetails.getName());
		book.setCoverPhoto(bookCoverPhoto);
		book.setEdition(basicDetails.getEdition());
		book.setDetails(basicDetails.getDetails());
		book.setISBN(basicDetails.getISBN());

		//add the user
		UserModel userModel = (UserModel)session.getAttribute("userModel");
		User user = userDAO.findById(userModel.getUserId());
		book.setUser(user);
		
		//add the publication
		if (model.getPublicationId() != null) {
			book.setPublication(publicationDAO.findById(model.getPublicationId()));
		}
		else {
			CreatePublicationModel createPublicationModel = model.getPublication();
			if (createPublicationModel.getName() != null && !createPublicationModel.getName().isEmpty()) {
				Publication publication = new Publication();
				publication.setName(createPublicationModel.getName());
				publication.setDetails(createPublicationModel.getDetails());
				
				book.setPublication(publication);
			}
		}
		
		book.setPublishedYear(basicDetails.getPublishedYear());
		book.setPublishedMonth(basicDetails.getPublishedMonth());
		book.setPublishedDate(basicDetails.getPublishedDate());

		//Existing author
		for (Integer authorId : model.getAuthorIds()) {
			if (authorId!=null) {
				Author author = authorDAO.findById(authorId);
				if (author != null) {
					book.getAuthors().add(author);
				}
			}
		}
		
		//New authors
		for (CreateAuthorModel createAuthorModel : model.getAuthors()) {
			if (createAuthorModel.getFirstName() != null && !createAuthorModel.getFirstName().isEmpty()) {
				Author author = new Author();
				author.setFirstName(createAuthorModel.getFirstName());
				author.setMiddleName(createAuthorModel.getMiddleName());
				author.setLastName(createAuthorModel.getLastName());
				author.setDetails(createAuthorModel.getDetails());
				
				book.getAuthors().add(author);
			}
		}
		
		//Book Source
		if (bookSource.getBookTypeId() != null) {
			BookSource source = new BookSource();
			source.setType(bookTypeDAO.findById(bookSource.getBookTypeId()));
			source.setValue(bookSource.getValue());

			source.setBook(book);
			book.setSource(source);
		}
		
		//set the read status
		BookUser bookUser = new BookUser();
		bookUser.setUser(user);
		bookUser.setBook(book);
		bookUser.setStatus(readStatusDAO.findById(model.getReadStatus()));
		book.getBookReaders().add(bookUser);
		
		//book.setStatus(readStatusDAO.findById(model.getReadStatus()));
		
		//Read details
		if (model.getReadStatus() != 1) {
			ReadDetail readDetail = new ReadDetail();
			
			readDetail.setStartDate(model.getReadDetails().getStartDate());
			readDetail.setStartMonth(model.getReadDetails().getStartMonth());
			readDetail.setStartYear(model.getReadDetails().getStartYear());
			
			if (model.getReadStatus() == 3) {
				readDetail.setEndDate(model.getReadDetails().getEndDate());
				readDetail.setEndMonth(model.getReadDetails().getEndMonth());
				readDetail.setEndYear(model.getReadDetails().getEndYear());
				
				readDetail.setRating(model.getReadDetails().getRating());
				readDetail.setReview(model.getReadDetails().getReview());
			}
			readDetail.setBookUser(bookUser);
			bookUser.setReadDetails(readDetail);
			//readDetail.setBook(book);
			//book.setReadDetails(readDetail);
		}
		
		try {
			bookDAO.create(book);
			redirectAttributes.addFlashAttribute("successMessage", "New book added successfully.");
			mv.setViewName("redirect:/");
		} catch (Exception exp) {
			mv.addObject("errorMessage", "There was some problem in adding new book, please try again.");mv.setViewName("blankMasterPage");
			mv.addObject("contentPagePath", "./book/create.jsp");
			
			populateCreateBookModel(mv, model);
			exp.printStackTrace();
		}
		
		return mv;
	}
	
	@RequestMapping(value = {"/edit/{bookId}"})
	public ModelAndView edit(@PathVariable("bookId")int bookId, HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		mv.addObject("contentPagePath", "./book/edit.jsp");

		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		Book book = bookDAO.findById(bookId, userModel.getUserId(), true);
		
		CreateBookModel model = new CreateBookModel();
		model.setId(book.getId());
		
		CreateBookBasicDetailsModel basicDetailsModel = new CreateBookBasicDetailsModel();
		
		basicDetailsModel.setName(book.getName());
		basicDetailsModel.setEdition(book.getEdition());
		basicDetailsModel.setDetails(book.getDetails());
		basicDetailsModel.setISBN(book.getISBN());
		basicDetailsModel.setPublishedYear(book.getPublishedYear());
		basicDetailsModel.setPublishedMonth(book.getPublishedMonth());
		basicDetailsModel.setPublishedDate(book.getPublishedDate());
		
		model.setBasicDetails(basicDetailsModel);
		
		//Publication
		Publication publication = book.getPublication();
		if (publication != null) {
			model.setPublicationId(publication.getId());
		}
		//model.setReadStatus(book.getStatus().getId());
		
		//Authors
		if (book.getAuthors() != null && book.getAuthors().size() > 0) {
			ArrayList<Integer> authorIdList = new ArrayList<>();
			book.getAuthors().forEach(author -> authorIdList.add(author.getId()));
			model.setAuthorIds(authorIdList);
		}
		
		populateCreateBookModel(mv, model);
		
		mv.addObject("book", model);
		mv.addObject("pageTitle", "Edit" + book.getFullName());
		
		Map<String, String> breadcrumbItems = new LinkedHashMap<>();
		breadcrumbItems.put("Home", "");
		breadcrumbItems.put("Book", "/book");
		breadcrumbItems.put(book.getFullName(), "/book/"+bookId);
		breadcrumbItems.put("Edit", "/book/"+bookId+"/edit");
		mv.addObject("breadcrumbItems", breadcrumbItems);		
		
		return mv;
	}

	@RequestMapping(value = {"/edit/{bookId}"}, method=RequestMethod.POST)
	public ModelAndView edit(@PathVariable("bookId")int bookId, @Valid @ModelAttribute("book") CreateBookModel model,
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes, 
			HttpSession httpSession,
			HttpServletRequest req) {
		
		ModelAndView mv = new ModelAndView();
		
		if (bindingResult.hasErrors()) {
			mv.setViewName("blankMasterPage");
			mv.addObject("contentPagePath", "./book/edit.jsp");
			
			populateCreateBookModel(mv, model);
			return mv;
		}

		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		Book book = bookDAO.findById(bookId, userModel.getUserId(), true);
		
		CreateBookBasicDetailsModel basicDetails = model.getBasicDetails();
		
		try {
			
			//Save the uploaded book cover image file
			String bookCoverPhoto = null;
			if (basicDetails.getBookCover() != null && !basicDetails.getBookCover().getOriginalFilename().isEmpty()) {
				bookCoverPhoto = basicDetails.getName() + "_" + (new Date()).getTime() + ".jpg";
				FileUploadUtil.uploadFile(req, basicDetails.getBookCover(), ConstantUtil.BOOK_COVER_DIR, bookCoverPhoto);
			}
			
			//Basic details
			book.setName(basicDetails.getName());
			if (bookCoverPhoto != null) {
				book.setCoverPhoto(bookCoverPhoto);
			}
			book.setEdition(basicDetails.getEdition());
			book.setDetails(basicDetails.getDetails());
			book.setISBN(basicDetails.getISBN());
			
			//Update the publication
			if (model.getPublicationId() != null) {
				book.setPublication(publicationDAO.findById(model.getPublicationId()));
			}
			else {
				CreatePublicationModel createPublicationModel = model.getPublication();
				if (createPublicationModel.getName() != null && !createPublicationModel.getName().isEmpty()) {
					Publication publication = new Publication();
					publication.setName(createPublicationModel.getName());
					publication.setDetails(createPublicationModel.getDetails());
					
					book.setPublication(publication);
				}
			}
			
			book.setPublishedYear(basicDetails.getPublishedYear());
			book.setPublishedMonth(basicDetails.getPublishedMonth());
			book.setPublishedDate(basicDetails.getPublishedDate());

			//remove Existing author if not there in the list of AuthorIDs
			for (int i=0; i<book.getAuthors().size(); i++) {
				try {
					Author author = book.getAuthors().get(i);
					if (!model.getAuthorIds().contains(author.getId())) {
						book.getAuthors().remove(author);
						i--;
					}
				} catch (IndexOutOfBoundsException exp) {
					break;
				}
			}
			
			/*book.getAuthors().forEach(author -> {
				if (!model.getAuthorIds().contains(author.getId())) {
					book.getAuthors().remove(author);
				}
			});*/
			
			//Existing author
			model.getAuthorIds().forEach(authorId -> {
				if (authorId!=null) {
					boolean contains = false;
					for (Author author : book.getAuthors()) {
						if (author.getId() == authorId) {
							contains = true;
							break;
						}
					}
					if (!contains) {
						Author author = authorDAO.findById(authorId);
						if (author != null) {
							book.getAuthors().add(author);
						}
					}
				}
			});

			//New authors
			model.getAuthors().forEach(createAuthorModel -> {
				if (createAuthorModel.getFirstName() != null && !createAuthorModel.getFirstName().isEmpty()) {
					Author author = new Author();
					author.setFirstName(createAuthorModel.getFirstName());
					author.setMiddleName(createAuthorModel.getMiddleName());
					author.setLastName(createAuthorModel.getLastName());
					author.setDetails(createAuthorModel.getDetails());
					
					book.getAuthors().add(author);
				}
			});
			
			bookDAO.update(book);
			redirectAttributes.addFlashAttribute("successMessage", "New book added successfully.");
			mv.setViewName("redirect:/book/"+book.getId());
			
		} catch (Exception exp) {
			redirectAttributes.addFlashAttribute("errorMessage", "There was some problem in updating book details, please try again.");
			mv.setViewName("redirect:/book/"+book.getId());
			exp.printStackTrace();
		}
		
		return mv;
	}
	
	
	@RequestMapping(value = "/edit/readProgress", method = RequestMethod.POST)
	public ModelAndView updateReadProgress(@Valid @ModelAttribute("readProgress") CreateReadProgressModel readProgressModel,
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes, 
			HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView();
		
		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		Book book = bookDAO.findById(readProgressModel.getReadDetails().getId(), userModel.getUserId(), true);
		if (bindingResult.hasErrors()) {
			mv.setViewName("blankMasterPage");
			mv.addObject("updateError", "Validation failed, please fill form properly");
			
			populateBookDetails(mv, book, null, readProgressModel, userModel);

			return mv;
		}
		
		//Update the Read Progress status
		List<BookUser> bookReaders = book.getBookReaders();
		BookUser bookReader;
		if (bookReaders != null) {
			bookReader = bookReaders.stream()
						.filter(reader -> reader.getUser().getId() == userModel.getUserId())
						.findFirst()
						.orElse(new BookUser());
		} else {
			bookReader = new BookUser();
		}
		bookReader.setStatus(readStatusDAO.findById(readProgressModel.getReadStatus()));
		//book.setStatus(readStatusDAO.findById(readProgressModel.getReadStatus()));

		//Read details
		if (readProgressModel.getReadStatus() != 1) {
			
			ReadDetail readDetail = bookReader.getReadDetails();
			if (readDetail == null) {
				readDetail = new ReadDetail();
				readDetail.setBookUser(bookReader);
			}
			
			readDetail.setStartDate(readProgressModel.getReadDetails().getStartDate());
			readDetail.setStartMonth(readProgressModel.getReadDetails().getStartMonth());
			readDetail.setStartYear(readProgressModel.getReadDetails().getStartYear());
			
			if (readProgressModel.getReadStatus() == 3) {
				readDetail.setEndDate(readProgressModel.getReadDetails().getEndDate());
				readDetail.setEndMonth(readProgressModel.getReadDetails().getEndMonth());
				readDetail.setEndYear(readProgressModel.getReadDetails().getEndYear());
				
				readDetail.setRating(readProgressModel.getReadDetails().getRating());
				readDetail.setReview(readProgressModel.getReadDetails().getReview());
			}
			readDetail.getBookUser().setBook(book);
			//readDetail.setBook(book);
			
			bookReader.setReadDetails(readDetail);
			//book.setReadDetails(readDetail);
		}

		bookDAO.update(book);
		mv.setViewName("redirect:/book/"+readProgressModel.getReadDetails().getId());
		redirectAttributes.addFlashAttribute("successMessage", "Book reading progress details updated successfully.");
		
		return mv;
	}
	
	@RequestMapping(value = "/edit/bookSource", method = RequestMethod.POST)
	public ModelAndView updateBookSource(@Valid @ModelAttribute("bookSource") CreateBookSourceModel bookSourceModel,
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes, 
			HttpSession httpSession,
			HttpServletRequest req) {
		
		ModelAndView mv = new ModelAndView();
		createBookSourceModelValidator.validate(bookSourceModel, bindingResult);

		UserModel userModel = (UserModel)httpSession.getAttribute("userModel");
		Book book = bookDAO.findById(bookSourceModel.getId(), userModel.getUserId(), true);
		if (bindingResult.hasErrors()) {
			mv.setViewName("blankMasterPage");
			mv.addObject("updateError", "Validation failed, please fill form properly");
			
			populateBookDetails(mv, book, bookSourceModel, null, userModel);

			return mv;	
		}
		
		
		if (bookSourceModel.getBookTypeId() == 2) {
			//Save the book file
			if (bookSourceModel.getBookTypeId()==2 && bookSourceModel.getBookFile() != null && !bookSourceModel.getBookFile().getOriginalFilename().isEmpty()) {
				String bookFileName = book.getName() + "_" + book.getEdition() + "_" + new Date().getTime() + "." + ConstantUtil.BOOK_FORMAT;
				FileUploadUtil.uploadFile(req, bookSourceModel.getBookFile(), ConstantUtil.BOOK_DIR, bookFileName);
				bookSourceModel.setValue(bookFileName);
			}
		}
		
		BookSource bookSource = new BookSource();
		bookSource.setType(bookTypeDAO.findById(bookSourceModel.getBookTypeId()));
		bookSource.setValue(bookSourceModel.getValue());
		bookSource.setBook(book);
		
		bookSourceDAO.saveOrUpdate(bookSource);
		mv.setViewName("redirect:/book/"+bookSourceModel.getId());
		redirectAttributes.addFlashAttribute("successMessage", "Book source details updated successfully.");
		
		return mv;
	}
}
