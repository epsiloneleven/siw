package it.uniroma3.siw.siwdata.web.controller;

import it.uniroma3.siw.siwdata.domain.Order;
import it.uniroma3.siw.siwdata.domain.OrderLine;
import it.uniroma3.siw.siwdata.domain.Product;
import it.uniroma3.siw.siwdata.service.OrderLineService;
import it.uniroma3.siw.siwdata.service.OrderService;
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
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/{id}",  method = RequestMethod.POST)
	public String update(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, 
						RedirectAttributes redirectAttributes, Locale locale,Model uiModel) {
			String updatedQuantityString=(String)httpServletRequest.getParameter("quantity");
			String orderId=(String)httpServletRequest.getParameter("orderId");
			OrderLine orderLine=orderLineService.findById(id);
			Integer updatedQuantity=Integer.parseInt(updatedQuantityString);
			Product product =orderLine.getProduct();
			
			//NOTHING TO CHANGE SUCCESS MESSAGE
			if(updatedQuantity ==orderLine.getQuantity()){
		   		  redirectAttributes.addFlashAttribute("message", new Message("success",messageSource.getMessage("order_save_success", new Object[]{}, locale)));			
			}
			//SEND PRODUCT QUANTITY BACK TO STOCK SUCCESS MESSAGE
			if(updatedQuantity < orderLine.getQuantity()){
		   		 product.setInStock(product.getInStock() + orderLine.getQuantity()-updatedQuantity);
		   		 productService.save(product);
		   		 orderLine.setQuantity(updatedQuantity);
		   		 orderLineService.save(orderLine);
		   		 redirectAttributes.addFlashAttribute("message", new Message("success",messageSource.getMessage("order_save_success", new Object[]{}, locale)));		
			}
		   //GET PRODUCT QUANTITY FROM STOCK IF AVAILABLE
		   if(updatedQuantity > orderLine.getQuantity()){
			   Integer quantity= updatedQuantity-orderLine.getQuantity();
			   //PRODUCT AVAILABLE SUCCESS MESSAGE
			   if (product.getInStock() >= quantity) {
				   product.setInStock(product.getInStock()-quantity);
				   orderLine.setQuantity(updatedQuantity);
				   productService.save(product);
				   orderLineService.save(orderLine);
			   	   redirectAttributes.addFlashAttribute("message", new Message("success",messageSource.getMessage("order_save_success", new Object[]{}, locale)));		
			   }
			   //PRODUCT OUT OF STOCK FAIL MESSAGE
			   else {
			   	   redirectAttributes.addFlashAttribute("message", new Message("success",messageSource.getMessage("order_save_fail", new Object[]{}, locale))); 
			   }
			}
		return "redirect:/orders/" + UrlUtil.encodeUrlPathSegment(orderId.toString(),httpServletRequest);
	}
		
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,Model uiModel,
			HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		    OrderLine orderLine=orderLineService.findById(id);
		    Product product=productService.findById(orderLine.getProduct().getId());
		    //SEND DELETED QUANTITY BACK TO STOCK
		    product.setInStock(product.getInStock()+orderLine.getQuantity());
		    productService.save(product);
			orderLineService.delete(orderLine);
			//IF NO ORDERLINES ARE LEFT PROCEED TO DELETE ORDER TOO
			String orderId=(String)httpServletRequest.getParameter("orderId");
			Order order=orderService.findById(Long.decode(orderId));
			if (order.getOrderLines().size() == 0 ){
			orderService.delete(order);
			}
			uiModel.asMap().clear(); redirectAttributes.addFlashAttribute("message", new Message("success",
					messageSource.getMessage("order_save_success", new Object[]{}, locale))); 
			return "redirect:/orders/";
	}
}
