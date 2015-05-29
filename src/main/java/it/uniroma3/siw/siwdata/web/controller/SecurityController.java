package it.uniroma3.siw.siwdata.web.controller;

import it.uniroma3.siw.siwdata.web.form.Message;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.security.access.prepost.PreAuthorize;

@RequestMapping("/security") @Controller
public class SecurityController {
	final Logger logger = LoggerFactory.getLogger(SecurityController.class); 
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping("/loginfail")
	public String loginFail(Model uiModel, Locale locale) {
		logger.info("Login failed detected"); 
		uiModel.addAttribute("message", new Message("error",messageSource.getMessage("message_login_fail", new Object[]{}, locale))); 
		return "customers/list";

	} 
}