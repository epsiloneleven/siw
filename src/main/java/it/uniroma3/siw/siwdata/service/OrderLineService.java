package it.uniroma3.siw.siwdata.service;

import it.uniroma3.siw.siwdata.domain.OrderLine;

public interface OrderLineService {

	public OrderLine save(OrderLine orderLine);
	
	public void delete(OrderLine orderLine);
	
	public OrderLine findById(Long id);
	
	
}
