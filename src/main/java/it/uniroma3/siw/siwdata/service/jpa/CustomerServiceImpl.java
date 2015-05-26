package it.uniroma3.siw.siwdata.service.jpa;

import it.uniroma3.siw.siwdata.domain.Customer;
import it.uniroma3.siw.siwdata.service.CustomerService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("jpaCustomerService")
@Repository
@Transactional
public class CustomerServiceImpl implements CustomerService{

private Log log = LogFactory.getLog(CustomerServiceImpl.class);	
	
	@PersistenceContext
	private EntityManager em;

	public Customer save(Customer contact) {
		if (contact.getId() == null) { // Insert Customer
			log.info("Inserting new contact");
			em.persist(contact);
		} else {                       // Update Customer
			em.merge(contact);
			log.info("Updating existing contact");
		}
		log.info("Customer saved with id: " + contact.getId());
		return contact;
	}

	public void delete(Customer contact) {
		Customer mergedCustomer = em.merge(contact);
		em.remove(mergedCustomer);
		log.info("Customer with id: " + contact.getId() + " deleted successfully");
	}
	
	
	
	@Transactional(readOnly=true)
	public Customer findById(Long id) {
		TypedQuery<Customer> query = em.createNamedQuery("Customer.findById", Customer.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	@Transactional(readOnly=true)
	public List<Customer> findByLastName(String lastName) {
		TypedQuery<Customer> query = em.createNamedQuery("Customer.findByLastName", Customer.class);
		query.setParameter("lastname", lastName);
		return query.getResultList();
	}
	
	
	@Transactional(readOnly=true)
	public List<Customer> findAll() {
		List<Customer> customers = em.createNamedQuery("Customer.findAll", Customer.class).getResultList();
		return customers;
	}
	
	@Transactional(readOnly=true)
	public List<Customer> findAllWithDetails() {
		List<Customer> customers = em.createNamedQuery("Customer.findAllWithDetails", Customer.class).getResultList();
		return customers;
	}
    
}
