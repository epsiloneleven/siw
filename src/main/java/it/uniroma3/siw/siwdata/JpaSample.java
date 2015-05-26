package it.uniroma3.siw.siwdata;

import it.uniroma3.siw.siwdata.domain.Address;
import it.uniroma3.siw.siwdata.domain.Customer;
import it.uniroma3.siw.siwdata.domain.Order;
import it.uniroma3.siw.siwdata.domain.OrderLine;
import it.uniroma3.siw.siwdata.domain.Product;
import it.uniroma3.siw.siwdata.service.CustomerService;
import it.uniroma3.siw.siwdata.service.OrderService;
import it.uniroma3.siw.siwdata.service.ProductService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.support.GenericXmlApplicationContext;


public class JpaSample {
	
	@PersistenceContext
	private static EntityManager em;

	public static void main(String[] args) {

		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:app-context.xml");
		ctx.refresh();
		CustomerService customerService =ctx.getBean("jpaCustomerService" , CustomerService.class);
		ProductService productService =ctx.getBean("jpaProductService" , ProductService.class);
		OrderService orderService =ctx.getBean("jpaOrderService" , OrderService.class);

		//ADD CUSTOMER
		Customer customer= new Customer();
		customer.setFirstName("Tony");
		customer.setLastName("Jones");
		
		//em.persist(customer);
		Address address = new Address();
    	address.setCity("San Francisco");
    	address.setCountry("United States");
    	address.setState("California");
    	address.setStreet("456, My Street");
    	address.setZipcode("555-22");
		
		customer.setAddress(address);

		customerService.save(customer);

    	
		
		//FIND CUSTOMER BY ID
		Customer c_search=customerService.findById(1l);
		System.out.println("");
		System.out.println("Customer with id 1:" + c_search);
		System.out.println("");	
		
		//FIND ALL CUSTOMERS
		List<Customer> customers;
		customers=customerService.findAll();
		for (Customer c_iter : customers) {
			System.out.println("");
			System.out.println("Customer:" + c_iter);
			System.out.println("");	
		}
		
		//FIND CUSTOMERS BY LAST NAME
		customers=customerService.findByLastName("Smith");
		for (Customer c_iter : customers) {
			System.out.println("");
			System.out.println("Customer:" + c_iter);
			System.out.println("");	
		}
		
		customers=customerService.findAllWithDetails();
		for (Customer c_iter : customers) {
			System.out.println("");
			System.out.println("Customer:" + c_iter);
			System.out.println("");	
		}
		
		
		Product product1= new Product();
    	product1.setCode("007");
    	product1.setDescription("Description 007");
    	product1.setName("Product 007");
    	product1.setPrice(100.00F);
    	product1.setInStock(5);
    	
    	productService.save(product1);
    	
    	
    	Product product2= new Product();
    	product2.setCode("011");
    	product2.setDescription("Description 011");
    	product2.setName("Product 011");
    	product2.setPrice(150.00F);
    	product2.setInStock(5);
    	
    	productService.save(product2);

    	
        Order order1 = new Order();
        Date creationDate1 = new Date();
        order1.setState(0);
   	    order1.setCreation_date(creationDate1);
   	    order1.setCustomer(customer);
   	  	
   	 
   	    List<OrderLine> orderlines1= new ArrayList<OrderLine>();
   	    OrderLine orderline1 = new OrderLine();
   	    orderline1.setItem("Test33");
   	    orderline1.setProduct(product1);
   	    //watch
   	    int quantity1=5;
   	    if(product1.getInStock() >= quantity1){
   		  orderline1.setQuantity(2);
   		  product1.setInStock(product1.getInStock() - quantity1);
   		  productService.save(product1);
   		  orderlines1.add(orderline1);
   		  //order1.setOrderLines(orderlines1);
   	    }
   	
   	    OrderLine orderline2 = new OrderLine();
  	    orderline2.setItem("Test33");
  	    orderline2.setProduct(product2);
  	    //watch
  	    int quantity2=5;
  	    if(product2.getInStock() >= quantity2){
  		  orderline2.setQuantity(2);
  		  product2.setInStock(product2.getInStock() - quantity2);
  		  productService.save(product2);
  		  orderlines1.add(orderline2);
  	    }
  	    order1.setOrderLines(orderlines1);
  	    orderService.save(order1);
   	  }
	}
	