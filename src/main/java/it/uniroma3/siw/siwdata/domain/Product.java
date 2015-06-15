package it.uniroma3.siw.siwdata.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

	@Entity
	@NamedQueries({
	@NamedQuery(name = "Product.findAllProducts", query = "SELECT p FROM Product p"),
	@NamedQuery(name="Product.findById", query="select distinct p from Product p where p.id = :id"),
	@NamedQuery(name="Product.findProviders", query="select distinct p.providers from Product p where p.id = :id")})
	public class Product {
        
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message="{validation.name.NotEmpty.message}")
	@Size(min=3, max=60, message="{validation.name.Size.message}")	
	@Column(nullable = false)
	private String name;
	
	private Float price;
	
	@NotEmpty(message="{validation.description.NotEmpty.message}") 
	@Column(length = 2000)
	private String description;

	@NotEmpty(message="{validation.code.NotEmpty.message}") 
	@Size(min=3, max=6, message="{validation.code.Size.message}")	
	@Column(nullable = false , unique=true)
	private String code;
	
	@ManyToMany(mappedBy="products")
    private List<Provider> providers;
	
	@Version
	private long version;
	
	@Column(nullable = false)
	private int inStock;
	
	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public List<Provider> getProviders() {
		return providers;
	}

	public void setProviders(List<Provider> providers) {
		this.providers = providers;
	}

	public Product() {
    }

	public Product(String name, Float price, String description, String code) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.code = code;
}

    //          Getters & Setters        
    
   public Long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
	
    public boolean equals(Object obj) {
        Product product = (Product)obj;
        return this.getCode().equals(product.getCode());
    }

    public int hashCode() {
         return this.code.hashCode();
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Product"); 
        sb.append("{id=").append(id); 
        sb.append(", name='").append(name); 
        sb.append(", price=").append(price); 
        sb.append(", description='").append(description); 
        sb.append(", code='").append(code);
        sb.append("}\n");
        return sb.toString();
    }
	
}