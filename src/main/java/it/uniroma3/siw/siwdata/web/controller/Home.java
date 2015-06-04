package it.uniroma3.siw.siwdata.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.security.access.prepost.PreAuthorize;


/*	/customers 				GET 
 *  /customers/{id} 		GET 
 *  /customers/{id}?form 	GET
 *	/customers/{id}?form 	POST  
 *	/customers?form 		GET
 *	/customers?form 		POST
 *	/customers/photo/{id} 	GET 
 */
@RequestMapping("/home")
@Controller
public class Home {

final Logger logger = LoggerFactory.getLogger(CustomerController.class);	
	
	/* Responds to HTTP GET
	 * Retrieves all customers and then injects the model in
	 * the "list" view.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String page() {
		
		return "home/page";
	}

}
