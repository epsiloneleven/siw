/**
 * Created on Dec 23, 2011
 */
package it.uniroma3.siw.siwdata.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.siwdata.domain.Customer;
import it.uniroma3.siw.siwdata.service.CustomerService;
import it.uniroma3.siw.siwdata.web.form.CustomerGrid;
import it.uniroma3.siw.siwdata.web.form.Message;
import it.uniroma3.siw.siwdata.web.util.UrlUtil;
import com.google.common.collect.Lists;

/**
 * @author Clarence
 *
 */
@RequestMapping("/customers")
@Controller
public class CustomerController {

	final Logger logger = LoggerFactory.getLogger(CustomerController.class);	
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		logger.info("Listing customers");	
		
		List<Customer> customers = customerService.findAll();
		uiModel.addAttribute("customers", customers);
		
		logger.info("No. of customers: " + customers.size());
		
		return "customers/list";
	}
	
}
