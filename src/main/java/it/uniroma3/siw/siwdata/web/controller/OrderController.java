package it.uniroma3.siw.siwdata.web.controller;

import it.uniroma3.siw.siwdata.domain.Order;
import it.uniroma3.siw.siwdata.domain.OrderLine;
import it.uniroma3.siw.siwdata.domain.Product;
import it.uniroma3.siw.siwdata.service.OrderService;
import it.uniroma3.siw.siwdata.service.ProductService;
import it.uniroma3.siw.siwdata.web.form.Message;
import it.uniroma3.siw.siwdata.web.util.UrlUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RequestMapping("/orders")
@Controller
public class OrderController {

final Logger logger = LoggerFactory.getLogger(OrderController.class);	
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	/* Responds to HTTP GET
	 * Retrieves all orders and then injects the model in
	 * the "list" view.
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		logger.info("Listing orders");	
		
		List<Order> orders = orderService.findAll();
		uiModel.addAttribute("orders", orders);
		
		logger.info("No. of orders: " + orders.size());
		
		return "orders/list";
	}
	/* Responds to HTTP GET
	 * Retrieves order by Id and then injects the model in
	 * the "show" view.
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) 
	public String show(@PathVariable("id") Long id, Model uiModel) {
		
		Order order = orderService.findById(id); 
		
		uiModel.addAttribute("order", order); 
		
		return "orders/show";
		}
	
	/*The update() method will be triggered when user updates order information and clicks the Save button. 
	 * First, Spring MVC will try to bind the submitted data to the Order domain object 
	 * and perform the type conversion and formatting automatically. 
	 * If binding errors are found (for example, the birth date was entered in the wrong format), 
	 * the errors will be saved into the BindingResult interface (under the package org.springframework.validation), 
	 * and an error message will be saved into the Model, redisplaying the edit view.
	 * If the binding is successful, the data will be saved, 
	 * and the logical view name will be returned for the display order 
	 * view by using redirect: as the prefix.
	 * Note that we want to display the message after the redirect, so we need to use the RedirectAttributes.addFlashAttribute()
	 * method (an interface under the package org.springframework.web.servlet.mvc.support) 
	 * for displaying the success message in the show order view. 
	 * In Spring MVC attributes are saved temporarily before the redirect (typically in the session) 
	 * to be made available to the request after the redirect and removed immediately.
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	public String update(@Valid Order order, BindingResult bindingResult, 
						Model uiModel, HttpServletRequest httpServletRequest, 
						RedirectAttributes redirectAttributes, Locale locale) {
			logger.info("Updating order"); 
			if (bindingResult.hasErrors()) {
				uiModel.addAttribute("message", new Message("error", messageSource.getMessage("order_save_fail", new Object[]{}, locale)));
				uiModel.addAttribute("order", order);
				return "orders/update"; 
			}
			uiModel.asMap().clear(); 
			redirectAttributes.addFlashAttribute("message", new Message("success",messageSource.getMessage("order_save_success", new Object[]{}, locale))); 
			orderService.save(order);
			return "redirect:/orders/" + UrlUtil.encodeUrlPathSegment(order.getId().toString(),httpServletRequest);
	}
	/*For the updateForm() method, the order is retrieved and saved into the Model, 
	 * and then the logical view orders/update is returned, 
	 * which will display the edit order view.
	 * That is, do nothing if form is sent through HTTP:GET
	 */
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET) 
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("order", orderService.findById(id));
		return "orders/update"; 
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,Model uiModel,
			HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		    Order order=orderService.findById(id);
			orderService.delete(order);
			uiModel.asMap().clear(); redirectAttributes.addFlashAttribute("message", new Message("success",
					messageSource.getMessage("order_save_success", new Object[]{}, locale))); 
			return "redirect:/orders/";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.POST )
	public String create( Model uiModel,
						HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		logger.info("Creating order"); 
		String id=httpServletRequest.getParameter("productId");
		String quantity=httpServletRequest.getParameter("productQuantity");
		Integer productQuantity=Integer.parseInt(quantity);
		Long productId=Long.decode(id);
		Product product= productService.findById(productId);
        Date creationDate = new Date();
		Order order = new Order();
		OrderLine orderLine=new OrderLine();
   	    List<OrderLine> orderlines1= new ArrayList<OrderLine>();
   	    orderLine.setItem("Test");
 	    orderLine.setProduct(product);
 	    orderLine.setQuantity(productQuantity);
 	    orderlines1.add(orderLine);
		order.setOrderLines(orderlines1);
		order.setCreationdate(creationDate);
		orderService.save(order);
		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute("message", new Message("success",
		messageSource.getMessage("order_save_success", new Object[]{}, locale))); 
		logger.info("Order id: " + order.getId());
		return "redirect:/orders/" + UrlUtil.encodeUrlPathSegment(order.getId().toString(),httpServletRequest); 
	}
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(params = "form", method = RequestMethod.GET) 
	public String createForm(Model uiModel) {
		Order order = new Order(); 
		uiModel.addAttribute("order", order); 
		return "orders/create";
	}
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(params="form" ,method = RequestMethod.DELETE)
	public String deleteOrder(@PathVariable Long orderId) {
		Order order =orderService.findById((Long)orderId);
		orderService.delete(order);
	    return "redirect:/orders/";
	}
	
}
