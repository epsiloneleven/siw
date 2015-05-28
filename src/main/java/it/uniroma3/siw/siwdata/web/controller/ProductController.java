package it.uniroma3.siw.siwdata.web.controller;

import it.uniroma3.siw.siwdata.domain.Product;
import it.uniroma3.siw.siwdata.service.ProductService;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/*	/products 				GET 
 *  /products/{id} 			GET
 *  /products/{id}			DELETE 
 *  /products/{id}?form 	GET
 *	/products/{id}?form 	POST  
 *	/products?form 			GET
 *	/products?form 			POST
 *	/products/photo/{id} 	GET 
 */

@RequestMapping("/products")
@SessionAttributes("product")
@Controller
public class ProductController {
	
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
	
	@RequestMapping(value = "/{id}" ,method = RequestMethod.GET)
		public String show(@PathVariable("id") Long id, Model uiModel) {
		Product product = productService.findById(id);
		uiModel.addAttribute(product);
		return "products/show";
	}
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
		public String update(@ModelAttribute("product") Product product, BindingResult bindingResult, Model uiModel, 
				HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale){
			logger.info("Updating customer"); 
			if (bindingResult.hasErrors()) {
				uiModel.addAttribute("message", new Message("error", messageSource.getMessage("product_save_fail", new Object[]{}, locale)));
				uiModel.addAttribute("product", product);
				return "products/update"; 
			}
			uiModel.asMap().clear(); 
			redirectAttributes.addFlashAttribute("message", new Message("success",messageSource.getMessage("product_save_success", new Object[]{}, locale))); 
			productService.save(product);
		return "redirect:/products/" + UrlUtil.encodeUrlPathSegment(product.getId().toString(),httpServletRequest);
	}
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String create(@Valid Product product, BindingResult bindingResult, Model uiModel,
						HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		logger.info("Creating product"); 
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error", messageSource.getMessage("product_save_fail", new Object[]{}, locale)));
			uiModel.addAttribute("product", product);
			return "products/create"; 
		}
		uiModel.asMap().clear(); redirectAttributes.addFlashAttribute("message", new Message("success",
		messageSource.getMessage("customer_save_success", new Object[]{}, locale))); 
		logger.info("Customer id: " + product.getId());
		productService.save(product);
		return "redirect:/products/" + UrlUtil.encodeUrlPathSegment(product.getId().toString(),httpServletRequest); 
	}
	@RequestMapping(params = "form", method = RequestMethod.GET) 
	public String createForm(Model uiModel) {
		Product product = new Product(); 
		uiModel.addAttribute("product", product); 
		return "products/create";
	}
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET) 
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("product", productService.findById(id));
		return "products/update"; 
	}

	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,Model uiModel,
			HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		    Product product=productService.findById(id);
			productService.delete(product);
			uiModel.asMap().clear(); redirectAttributes.addFlashAttribute("message", new Message("success",
					messageSource.getMessage("product_save_success", new Object[]{}, locale))); 
			return "redirect:/products/";
	}
}
