package it.uniroma3.siw.siwdata.web.controller;

import it.uniroma3.siw.siwdata.domain.OrderLine;
import it.uniroma3.siw.siwdata.service.OrderLineService;
import it.uniroma3.siw.siwdata.service.ProductService;
import it.uniroma3.siw.siwdata.web.form.Message;
import it.uniroma3.siw.siwdata.web.util.UrlUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RequestMapping("/orderlines")
@Controller
public class OrderLineController {

final Logger logger = LoggerFactory.getLogger(OrderController.class);	
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private OrderLineService orderLineService;
	
	@Autowired
	private ProductService productService;
	
	//@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/{id}",  method = RequestMethod.POST)
	public String update(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, 
						RedirectAttributes redirectAttributes, Locale locale) {
			String quantity=(String)httpServletRequest.getParameter("quantity");
			String orderId=(String)httpServletRequest.getParameter("orderId");
			OrderLine orderLine=orderLineService.findById(id);
			orderLine.setQuantity(Integer.parseInt(quantity));
			orderLineService.save(orderLine);
			return "redirect:/orders/" + UrlUtil.encodeUrlPathSegment(orderId.toString(),httpServletRequest);
	}
		
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,Model uiModel,
			HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		    OrderLine orderLine=orderLineService.findById(id);
			orderLineService.delete(orderLine);
			uiModel.asMap().clear(); redirectAttributes.addFlashAttribute("message", new Message("success",
					messageSource.getMessage("order_save_success", new Object[]{}, locale))); 
			return "redirect:/orders/";
	}
	
	
	
}
