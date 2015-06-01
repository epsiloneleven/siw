package it.uniroma3.siw.siwdata.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="orders")
@NamedQueries({
	@NamedQuery(name = "Order.findAllOrders", query = "SELECT o FROM Order o"),
	@NamedQuery(name="Order.findById", query="select distinct o from Order o where o.id = :id"),
	@NamedQuery(name="Order.findByCustomerId", query="select distinct o from Order o where o.customer.id = :id")
	})
public class Order {
	  
	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getId() {
		return id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column ( nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationdate;
	
	@ManyToOne
	Customer customer;


	@Column (nullable=false)
	private int state;
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "orders_id")
	private List<OrderLine> orderLines;

	
	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	
}
