package org.dieschnittstelle.jee.esa.crm.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * this is an abstraction over different touchpoints (with pos being the most
 * prominent one, others may be a service center, website, appsite, etc.)
 * 
 * @author kreutel
 * 
 */
// jaxb annotations
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://dieschnittstelle.org/jee/esa/crm/model")
@XmlSeeAlso(StationaryTouchpoint.class)
// jpa annotations
@Entity
// inheritance
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "touchpointType", discriminatorType = DiscriminatorType.STRING)
// @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS);
// @Inheritance(strategy=InheritanceType.JOINED);
@SequenceGenerator(name = "touchpoint_sequence", sequenceName = "touchpoint_id_sequence")
public abstract class AbstractTouchpoint implements Serializable {
	
	/**
	 *
	 */
	private static final long				serialVersionUID	= 5207353251688141788L;
	
	@XmlTransient
	@ManyToMany
	private Collection<Customer>			customers			= new HashSet<Customer>();
	
	/**
	 * the id of the PointOfSale from the erp data
	 */
	protected int							erpPointOfSaleId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "touchpoint_sequence")
	protected int							id					= -1;
	
	/**
	 * the name of the touchpoint
	 */
	protected String						name;
	
	@OneToMany(mappedBy = "touchpoint")
	private Collection<CustomerTransaction>	transactions;
	
	public AbstractTouchpoint() {
		
	}
	
	public void addCustomer(final Customer customer) {
		this.customers.add(customer);
	}
	
	@Override
	public boolean equals(final Object obj) {
		
		if ((obj == null) || !(obj instanceof AbstractTouchpoint)) {
			return false;
		}
		
		return this.getId() == ((AbstractTouchpoint) obj).getId();
	}
	
	public Collection<Customer> getCustomers() {
		return this.customers;
	}
	
	public int getErpPointOfSaleId() {
		return this.erpPointOfSaleId;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Collection<CustomerTransaction> getTransactions() {
		return this.transactions;
	}
	
	@Override
	public int hashCode() {
		return (this.name + this.id).hashCode();
	}
	
	public void setCustomers(final HashSet<Customer> customers) {
		this.customers = customers;
	}
	
	public void setErpPointOfSaleId(final int erpPointOfSaleId) {
		this.erpPointOfSaleId = erpPointOfSaleId;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public void setTransactions(final Collection<CustomerTransaction> transactions) {
		this.transactions = transactions;
	}
	
}
