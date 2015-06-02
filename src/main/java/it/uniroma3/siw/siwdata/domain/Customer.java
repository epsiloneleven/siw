package it.uniroma3.siw.siwdata.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;



@Entity
@Table(name = "customer")
@NamedQueries({
@NamedQuery(name="Customer.findById", query="select distinct c from Customer c where c.id = :id"),
@NamedQuery(name="Customer.findByLastName", query="select distinct c from Customer c where c.lastName = :lastname"),
@NamedQuery(name="Customer.findByUserName", query="select distinct c from Customer c where c.userName = :username"),
@NamedQuery(name="Customer.findAllWithDetails", query="select distinct c from Customer c left join c.address"),
@NamedQuery(name="Customer.findAll",query="select c from Customer c")})
public class Customer {
//SELECT c.name, p.name FROM Country c JOIN c.capital p

		  @Id
		  @GeneratedValue(strategy = IDENTITY)
		  @Column(name = "ID")
		  private Long id;
		  
		  @NotEmpty(message="{validation.firstname.NotEmpty.message}") 
		  @Size(min=3, max=60, message="{validation.firstname.Size.message}")	
		  @Column(nullable = false)
		  private String firstName;
		  
		  @NotEmpty(message="{validation.lastname.NotEmpty.message}") 
		  @Size(min=2, max=40, message="{validation.lastname.Size.message}")
		  @Column(nullable = false)
		  private String lastName;
		  
		  private String email;

		  private String userName;
		  
		  private String password;
		  
		  private boolean enabled;
		  
		  public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime") 
		  @DateTimeFormat(iso=ISO.DATE)
		  private DateTime dateOfBirth;
	
		  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
		  private Address address;

		  public Long getId() {
			return id;
		  }

		  public String getFirstName() {
			return firstName;
		  }

		  public void setFirstName(String firstName) {
			this.firstName = firstName;
		  }
			
		  public String getLastName() {
				return lastName;
			}

		  public void setLastName(String lastName) {
				this.lastName = lastName;
			}
			
		  public String getEmail() {
				return email;
			}
			
		  public void setEmail(String email) {
				this.email = email;
			}
			
		  public DateTime getDateOfBirth() {
				return dateOfBirth;
			}
			
		  public void setDateOfBirth(DateTime dateOfBirth) {
				this.dateOfBirth = dateOfBirth;
			}
			
		  public Address getAddress() {
					return address;
			}

		  public void setAddress(Address address) {
					this.address = address;
			}
		  public String toString() {		
				return "Customer - Id: " + id + ", First name: " + firstName 
						+ ", Last name: " + lastName + " Address - Street: "+this.address.getStreet() + " City:" +this.address.getCity() + " Country:" + this.address.getCountry();
			}	
		  

}
