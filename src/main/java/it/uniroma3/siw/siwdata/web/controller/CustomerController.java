package it.uniroma3.siw.siwdata.web.controller;


import it.uniroma3.siw.siwdata.domain.Customer;
import it.uniroma3.siw.siwdata.service.CustomerService;
import it.uniroma3.siw.siwdata.web.form.Message;
import it.uniroma3.siw.siwdata.web.util.UrlUtil;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/*	/customers 				GET 
 *  /customers/{id} 		GET 
 *  /customers/{id}?form 	GET
 *	/customers/{id}?form 	POST  
 *	/customers?form 		GET
 *	/customers?form 		POST
 *	/customers/photo/{id} 	GET 
 */
@RequestMapping("/customers")
@Controller
public class CustomerController {

final Logger logger = LoggerFactory.getLogger(CustomerController.class);	
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private CustomerService customerService;
	
	/* Responds to HTTP GET
	 * Retrieves all customers and then injects the model in
	 * the "list" view.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		logger.info("Listing customers");	
		
		List<Customer> customers = customerService.findAll();
		uiModel.addAttribute("customers", customers);
		
		logger.info("No. of customers: " + customers.size());
		
		return "customers/list";
	}
	/* Responds to HTTP GET
	 * Retrieves customer by Id and then injects the model in
	 * the "show" view.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) 
	public String show(@PathVariable("id") Long id, Model uiModel) {
		
		Customer customer = customerService.findById(id); 
		
		uiModel.addAttribute("customer", customer); 
		
		return "customers/show";
		}
	
	/*The update() method will be triggered when user updates customer information and clicks the Save button. 
	 * First, Spring MVC will try to bind the submitted data to the Customer domain object 
	 * and perform the type conversion and formatting automatically. 
	 * If binding errors are found (for example, the birth date was entered in the wrong format), 
	 * the errors will be saved into the BindingResult interface (under the package org.springframework.validation), 
	 * and an error message will be saved into the Model, redisplaying the edit view.
	 * If the binding is successful, the data will be saved, 
	 * and the logical view name will be returned for the display customer 
	 * view by using redirect: as the prefix.
	 * Note that we want to display the message after the redirect, so we need to use the RedirectAttributes.addFlashAttribute()
	 * method (an interface under the package org.springframework.web.servlet.mvc.support) 
	 * for displaying the success message in the show customer view. 
	 * In Spring MVC attributes are saved temporarily before the redirect (typically in the session) 
	 * to be made available to the request after the redirect and removed immediately.
	 */
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	public String update(@Valid Customer customer, BindingResult bindingResult, 
						Model uiModel, HttpServletRequest httpServletRequest, 
						RedirectAttributes redirectAttributes, Locale locale) {
			logger.info("Updating customer"); 
			if (bindingResult.hasErrors()) {
				uiModel.addAttribute("message", new Message("error", messageSource.getMessage("customer_save_fail", new Object[]{}, locale)));
				uiModel.addAttribute("customer", customer);
				return "customers/update"; 
			}
			uiModel.asMap().clear(); 
			redirectAttributes.addFlashAttribute("message", new Message("success",messageSource.getMessage("customer_save_success", new Object[]{}, locale))); 
			customerService.save(customer);
			return "redirect:/customers/" + UrlUtil.encodeUrlPathSegment(customer.getId().toString(),httpServletRequest);
	}
	/*For the updateForm() method, the customer is retrieved and saved into the Model, 
	 * and then the logical view customers/update is returned, 
	 * which will display the edit customer view.
	 * That is, do nothing if form is sent through HTTP:GET
	 */
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET) 
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("customer", customerService.findById(id));
		return "customers/update"; 
	}
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,Model uiModel,
			HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		    Customer customer=customerService.findById(id);
			customerService.delete(customer);
			uiModel.asMap().clear(); redirectAttributes.addFlashAttribute("message", new Message("success",
					messageSource.getMessage("customer_save_success", new Object[]{}, locale))); 
			return "redirect:/customers/";
	}
	
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String create(@Valid Customer customer, BindingResult bindingResult, Model uiModel,
						HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		logger.info("Creating customer"); 
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error", messageSource.getMessage("customer_save_fail", new Object[]{}, locale)));
			uiModel.addAttribute("customer", customer);
			return "customers/create"; 
		}
		uiModel.asMap().clear(); redirectAttributes.addFlashAttribute("message", new Message("success",
		messageSource.getMessage("customer_save_success", new Object[]{}, locale))); 
		logger.info("Customer id: " + customer.getId());
		customerService.save(customer);
		return "redirect:/customers/" + UrlUtil.encodeUrlPathSegment(customer.getId().toString(),httpServletRequest); 
	}
	@RequestMapping(params = "form", method = RequestMethod.GET) 
	public String createForm(Model uiModel) {
		Customer customer = new Customer(); 
		uiModel.addAttribute("customer", customer); 
		return "customers/create";
	}
	@RequestMapping(params="form" ,method = RequestMethod.DELETE)
	public String deleteCustomer(@PathVariable Long customerId) {
		Customer customer =customerService.findById((Long)customerId);
		customerService.delete(customer);
	    return "redirect:/customers/";
	}
	
}
