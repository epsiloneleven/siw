package it.uniroma3.siw.siwdata.service.jpa;

import it.uniroma3.siw.siwdata.domain.Product;
import it.uniroma3.siw.siwdata.domain.Provider;
import it.uniroma3.siw.siwdata.service.ProductService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("jpaProductService")
@Repository
@Transactional
public class ProductServicceImpl implements ProductService {
	
	
	@PersistenceContext
	private EntityManager em;

	private Log log = LogFactory.getLog(CustomerServiceImpl.class);	

	
	public Product save(Product product) {
		if (product.getId() == null) { // Insert Product
			log.info("Inserting new product");
			em.persist(product);
		} else {                       // Update Product
			em.merge(product);
			log.info("Updating existing product");
		}
		log.info("Product saved with id: " + product.getId());
		return product;
	}

	public void delete(Product product) {
		Product mergedProduct = em.merge(product);
		em.remove(mergedProduct);
		log.info("Product with id: " + product.getId() + " deleted successfully");
	}
	
	
	
	@Transactional(readOnly=true)
	public Product findById(Long id) {
		TypedQuery<Product> query = em.createNamedQuery("Product.findById", Product.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	
	@Transactional(readOnly=true)
	public List<Product> findAll() {
		List<Product> products = em.createNamedQuery("Product.findAllProducts", Product.class).getResultList();
		return products;
	}
	
	@Transactional(readOnly=true)
	public List<Provider> findProviders(Long id) {
		Query query = em.createNamedQuery("Product.findProviders");
		query.setParameter("id", id);
		List<Provider> providers = query.getResultList();
		return providers;
	}
}
