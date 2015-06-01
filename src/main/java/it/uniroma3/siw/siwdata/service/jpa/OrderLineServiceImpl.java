package it.uniroma3.siw.siwdata.service.jpa;

import it.uniroma3.siw.siwdata.domain.Order;
import it.uniroma3.siw.siwdata.domain.OrderLine;
import it.uniroma3.siw.siwdata.service.OrderLineService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("jpaOrderLineService")
@Repository
@Transactional
public class OrderLineServiceImpl implements OrderLineService {
	
private Log log = LogFactory.getLog(OrderLineServiceImpl.class);	
	
	@PersistenceContext
	private EntityManager em;
	
	public OrderLine save(OrderLine orderLine) {
		if (orderLine.getId() == null) { // Insert Order
			em.persist(orderLine);
		} 
		else {         // Update Order	
			em.merge(orderLine);
			log.info("Updating existing order");
		}
		log.info("Order saved with id: " + orderLine.getId());
		return orderLine;
	}
	
	public void delete(Order order) {
		Order mergedOrder = em.merge(order);
		em.remove(mergedOrder);
		log.info("Order with id: " + order.getId() + " deleted successfully");
	}
	
	@Transactional(readOnly=true)
	public OrderLine findById(Long id) {
		TypedQuery<OrderLine> query = em.createNamedQuery("OrderLine.findById", OrderLine.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	public void delete(OrderLine orderLine) {
		OrderLine mergedOrderLine = em.merge(orderLine);
		em.remove(mergedOrderLine);
		log.info("Order with id: " + orderLine.getId() + " deleted successfully");
	}


}
