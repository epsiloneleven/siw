package it.uniroma3.siw.siwdata.service;

import it.uniroma3.siw.siwdata.domain.Product;
import it.uniroma3.siw.siwdata.domain.Provider;

import java.util.List;

public interface ProductService {

	// Insert or update a contact	
		public Product save(Product product);
		
		// Delete a contact	
		public void delete(Product product);
		
		public Product findById(Long id);
	
		public List<Product> findAll();
		
		public List<Provider> findProviders(Long id);
}
