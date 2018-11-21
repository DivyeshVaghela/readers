package com.learning.readers.controller;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.readers.dao.IRoleDAO;
import com.learning.readers.dao.ISecretQuestionDAO;
import com.learning.readers.dao.IUserDAO;
import com.learning.readers.entity.Role;
import com.learning.readers.entity.User;
import com.learning.readers.model.ExceptionCapsule;
import com.learning.readers.model.ForgotPasswordModel;
import com.learning.readers.model.SignUpReaderModel;
import com.learning.readers.service.EmailNotificationService;
import com.learning.readers.validator.SignUpReaderModelValidator;

@Controller
public class SecurityController {
	
	@Autowired
	IUserDAO userDAO;
	@Autowired
	IRoleDAO roleDAO;
	@Autowired
	ISecretQuestionDAO secretQuestionDAO;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	SignUpReaderModelValidator signUpReaderModelValidator;
	@Autowired
	EmailNotificationService emailNotificationService;
	
	@RequestMapping(value = {"/login"})
	public ModelAndView login(@RequestParam(name="error", required=false)String error, 
			@RequestParam(name="logout", required=false)String logout,
			@RequestParam(name="registered", required=false)String registered,
			HttpSession httpSession) {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		
		if (error != null) {
			mv.addObject("loginError", ((Exception)httpSession.getAttribute("SPRING_SECURITY_LAST_EXCEPTION")).getMessage());
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
		signUpReaderModel.setSecretQuestionList(secretQuestionDAO.secretQuestionsOnly());
		
		mv.addObject("signUpReaderModel", signUpReaderModel);
	}
	
	@RequestMapping(value = {"/signup-reader"}, method=RequestMethod.POST)
	public ModelAndView signupReader(@Valid @ModelAttribute("signUpReaderModel") SignUpReaderModel signUpReaderModel,
			BindingResult bindingResults,
			RedirectAttributes redirectAttributes,
			HttpServletRequest servletRequest) {
		
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

		newReader.setSecretQuestionId(signUpReaderModel.getSelectedSecretQuestion());
		newReader.setSecretAnswer(signUpReaderModel.getSecretQuestionAnswer());
		
		HashSet<Role> roles = new HashSet<>();
		roles.add(roleDAO.get("READER"));
		newReader.setRoles(roles);
		
		try {
			Integer newReaderId = userDAO.create(newReader);
			
			//Integer newReaderId = 1;
			//emailNotificationService.sendMail(newReader.getEmail(), "Email Varification", "Hello");
			
			//Sending the verification email
			/*String content = "This is the varification mail";
			try {
				content = FileUtil.getResourceFileContent("emailTemplates/registration.html");
				content = content.replaceAll("##username##", newReader.getUsername());
				
				String hostname = ServerUtil.getHostname(servletRequest);
				int portNo = ServerUtil.getPortnumber(servletRequest);
				content = content.replaceAll("##verificationLink##", hostname+":"+portNo+"/emailVarification?id="+newReaderId+"&key="+newReader.getVarificationKey());
				
				emailNotificationService.sendMail("complaintbook.mail@gmail.com", newReader.getEmail(), "Email Varification", content);
				
			} catch(IOException exp) {
				exp.printStackTrace();
			}*/
			
			if (newReaderId!=null) {
				redirectAttributes.addFlashAttribute("registerSuccessMessage", "Registered successfully, you can login now");
				mv.setViewName("redirect:/login?registered");
			} else {
				prepareSignUpReader(mv, "blankMasterPage", signUpReaderModel);			
				mv.addObject("signUpError", "There was some problem in sign up, please try again.");
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			prepareSignUpReader(mv, "blankMasterPage", signUpReaderModel);			
			mv.addObject("signUpError", "There was some problem in sign up, please try again.");
		}
		return mv;
	}
	
	private void populateForgotPasswordModel(ModelAndView mv, ForgotPasswordModel forgotPasswordModel, Integer userId) {
		
		if (forgotPasswordModel == null) {
			forgotPasswordModel = new ForgotPasswordModel();
		}
		
		if (userId != null) {
			forgotPasswordModel.setUserId(userId);
			forgotPasswordModel.setSecretQuestion(userDAO.getSecretQuestion(userId));
		}
		
		mv.addObject("forgotPasswordModel", forgotPasswordModel);
		mv.addObject("contentPagePath", "./security/forgot-password.jsp");
	}
	
	@RequestMapping(value = {"/forgot-password"}, method=RequestMethod.GET)
	public ModelAndView forgotPassword() {
		
		ModelAndView mv = new ModelAndView("blankMasterPage");
		populateForgotPasswordModel(mv, null, null);
		
		return mv;
	}
	
	@RequestMapping(value = {"/forgot-password"}, method=RequestMethod.POST)
	public ModelAndView forgotPasswordGetQuestion(@Valid @ModelAttribute("forgotPasswordModel") ForgotPasswordModel forgotPasswordModel, 
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView();
		
		if (bindingResult.hasErrors()) {
			populateForgotPasswordModel(mv, forgotPasswordModel, null);
			mv.setViewName("blankMasterPage");
			
			return mv;
		}
		
		User user = userDAO.findByEmail(forgotPasswordModel.getEmail());
		
		if (user == null) {
			populateForgotPasswordModel(mv, forgotPasswordModel, null);
			mv.setViewName("blankMasterPage");
			mv.addObject("forgotPasswordError", "This email address is not registered");
			
			return mv;
		}
		if (forgotPasswordModel.getUserId() == null) {

			forgotPasswordModel.setUserId(user.getId());
			populateForgotPasswordModel(mv, forgotPasswordModel, user.getId());
			mv.setViewName("blankMasterPage");
		} else {
			
			if (user.getSecretAnswer().equals(forgotPasswordModel.getSecretAnswer())) {

				String newPassword = RandomStringUtils.random(10, true, true);
				user.setPassword(passwordEncoder.encode(newPassword));
				userDAO.update(user);

				redirectAttributes.addFlashAttribute("passwordChanged", true);
				redirectAttributes.addFlashAttribute("username", user.getUsername());
				redirectAttributes.addFlashAttribute("email", user.getEmail());
				redirectAttributes.addFlashAttribute("password", newPassword);
				
				/*mv.addObject("username", user.getUsername());
				mv.addObject("email", user.getEmail());
				mv.addObject("password", newPassword);
				populateForgotPasswordModel(mv, null, null);
				mv.setViewName("blankMasterPage");*/
				mv.setViewName("redirect:/login");
			} else {
				
				forgotPasswordModel.setUserId(user.getId());
				populateForgotPasswordModel(mv, forgotPasswordModel, user.getId());
				mv.setViewName("blankMasterPage");
				mv.addObject("forgotPasswordError", "Answer of Secret question is invalid");
			}
		}
		
		return mv;
	}
}
