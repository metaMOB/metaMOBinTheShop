package org.dieschnittstelle.jee.esa.crm.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jboss.logging.Logger;

@Entity
public class CustomerTransaction implements Serializable {
	
	protected static Logger			logger				= Logger.getLogger(CustomerTransaction.class);
	
	/**
	 *
	 */
	private static final long		serialVersionUID	= -1251851309422364868L;
	
	private boolean					completed;
	
	@ManyToOne
	private Customer				customer;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date					date;
	
	@Id
	@GeneratedValue
	private int						id					= -1;
	
	/*
	 * Ü1.1
	 */
	// @OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
	@OneToMany
	private List<CrmProductBundle>	products			= new ArrayList<CrmProductBundle>();
	
	@ManyToOne
	private AbstractTouchpoint		touchpoint;
	
	private int						value;
	
	public CustomerTransaction() {
		logger.info("constructor");
	}
	
	public CustomerTransaction(final Customer customer, final AbstractTouchpoint tp, final List<CrmProductBundle> products) {
		this.customer = customer;
		this.touchpoint = tp;
		this.products = products;
		this.date = new Date();
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public int getId() {
		return this.id;
	}
	
	public List<CrmProductBundle> getProducts() {
		return this.products;
	}
	
	public AbstractTouchpoint getTouchpoint() {
		return this.touchpoint;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public boolean isCompleted() {
		return this.completed;
	}
	
	@PostLoad
	public void onPostLoad() {
		logger.info("@PostLoad: " + this);
	}
	
	@PostPersist
	public void onPostPersist() {
		logger.info("@PostPersist: " + this);
	}
	
	@PostRemove
	public void onPostRemove() {
		logger.info("@PostRemove: " + this);
	}
	
	@PostUpdate
	public void onPostUpdate() {
		logger.info("@PostUpdate: " + this);
	}
	
	@PrePersist
	public void onPrePersist() {
		logger.info("@PrePersist: " + this);
	}
	
	@PreRemove
	public void onPreRemove() {
		logger.info("@PreRemove: " + this);
	}
	
	@PreUpdate
	public void onPreUpdate() {
		logger.info("@PreUpdate: " + this);
	}
	
	public void setCompleted(final boolean completed) {
		this.completed = completed;
	}
	
	/*
	 * lifecycle logging
	 */
	
	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}
	
	public void setDate(final Date date) {
		this.date = date;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	public void setProducts(final List<CrmProductBundle> products) {
		this.products = products;
	}
	
	public void setTouchpoint(final AbstractTouchpoint touchpoint) {
		this.touchpoint = touchpoint;
	}
	
	public void setValue(final int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "{CustomerTransaction " + this.id + " " + this.customer + " " + this.touchpoint + ", " + this.products + "}";
	}
	
}
