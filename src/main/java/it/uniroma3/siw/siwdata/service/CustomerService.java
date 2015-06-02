package it.uniroma3.siw.siwdata.service;

import it.uniroma3.siw.siwdata.domain.Customer;

import java.util.List;

public interface CustomerService {

	// Insert or update a contact	
	public Customer save(Customer contact);
	
	// Delete a contact	
	public void delete(Customer contact);
	
	public Customer findById(Long id);
	
	public List<Customer> findByLastName(String lastName);
	
	// Find all contacts
	public List<Customer> findAll();
	
	public List<Customer> findAllWithDetails();
	
	public Customer findByUserName(String userName);
	

}
