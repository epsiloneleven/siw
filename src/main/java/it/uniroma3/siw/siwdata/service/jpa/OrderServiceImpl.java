package it.uniroma3.siw.siwdata.service.jpa;

import it.uniroma3.siw.siwdata.domain.Order;
import it.uniroma3.siw.siwdata.domain.OrderLine;
import it.uniroma3.siw.siwdata.service.OrderService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("jpaOrderService")
@Repository
@Transactional
public class OrderServiceImpl implements OrderService {
	
private Log log = LogFactory.getLog(OrderServiceImpl.class);	
	
	@PersistenceContext
	private EntityManager em;
	
	public Order save(Order order) {
		if (order.getId() == null) { // Insert Order
			log.info("Inserting new order");
			
			List<OrderLine> orderlines=order.getOrderLines();
			for (OrderLine orderline : orderlines) {
				em.persist(orderline);
			}
			
			em.persist(order);
		} else {         // Update Order
			
			List<OrderLine> orderlines=order.getOrderLines();
			for (OrderLine orderline : orderlines) {
				em.persist(orderline);
			}
			
			em.merge(order);
			log.info("Updating existing order");
		}
		log.info("Order saved with id: " + order.getId());
		return order;
	}
	
	public void delete(Order order) {
		Order mergedOrder = em.merge(order);
		em.remove(mergedOrder);
		log.info("Order with id: " + order.getId() + " deleted successfully");
	}
	
	@Transactional(readOnly=true)
	public Order findById(Long id) {
		TypedQuery<Order> query = em.createNamedQuery("Order.findById", Order.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	
	@Transactional(readOnly=true)
	public List<Order> findByCustomerId(Long id) {
		TypedQuery<Order> query = em.createNamedQuery("Order.findByCustomerId", Order.class);
		List<Order> orders=null;
		query.setParameter("id", id);
		try {
			orders= query.getResultList();
		}
		catch (Exception e) {
			System.out.println("Cannot find any order for such customer.");
		}
		return orders;
	}
	@Transactional(readOnly=true)
	public Order findOpenByCustomerId(Long id) {
		TypedQuery<Order> query = em.createNamedQuery("Order.findOpenByCustomerId", Order.class);
		Order order=null;
		query.setParameter("id", id);
		try {
		order=query.getSingleResult();
		}
		catch (Exception e) {
			System.out.println("Cannot find any order for such customer.");
		}
		return order;
	}


	@Transactional(readOnly=true)
	public List<Order> findAll() {
		List<Order> orders = em.createNamedQuery("Order.findAllOrders", Order.class).getResultList();
		return orders;
	}

}
