package it.uniroma3.siw.siwdata.service;

import it.uniroma3.siw.siwdata.domain.Order;

public interface OrderService {

	public Order save(Order order);
	
	public void delete(Order order);
	
	public Order findById(Long id);
	

	
}
