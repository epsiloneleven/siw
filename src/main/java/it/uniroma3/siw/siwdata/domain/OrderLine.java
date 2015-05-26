package it.uniroma3.siw.siwdata.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity

public class OrderLine {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String item;
	
	@Column(nullable=false)
	private Integer quantity;
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	//public Double getUnitPrice() {
	//	return unitPrice;
	//}

	//public void setUnitPrice(Double unitPrice) {
	//	this.unitPrice = unitPrice;
	//}

	public Long getId() {
		return id;
	}

	//@Column(nullable=true)
	//private Double unitPrice;
	
	
	@OneToOne
	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
