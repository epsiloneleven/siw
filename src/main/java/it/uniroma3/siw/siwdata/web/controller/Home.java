package it.uniroma3.siw.siwdata.web.controller;


import it.uniroma3.siw.siwdata.domain.Product;
import it.uniroma3.siw.siwdata.service.ProductService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/index.html")
@Controller
public class Home {

final Logger logger = LoggerFactory.getLogger(CustomerController.class);	
	
@Autowired
MessageSource messageSource;

@Autowired
private ProductService productService;


/* Responds to HTTP GET
 * Retrieves all products and then injects the model in
 * the "list" view.
 */
@RequestMapping(method = RequestMethod.GET)
public String list(Model uiModel) {
	
	List<Product> products = productService.findAll();
	uiModel.addAttribute("products",products);
	return "products/list";
}

}
