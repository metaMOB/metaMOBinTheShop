package org.dieschnittstelle.jee.esa.crm.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.jboss.logging.Logger;

/*
 *
 */
@Entity
public class Customer implements Serializable {
	
	protected static Logger					logger				= Logger.getLogger(Customer.class);
	
	/**
	 *
	 */
	private static final long				serialVersionUID	= 7461272049473919251L;
	
	@ManyToOne(cascade = { CascadeType.ALL })
	private Address							address;
	
	private String							email;
	
	private String							firstName;
	
	private Gender							gender;
	
	@Id
	@GeneratedValue
	private int								id					= -1;
	
	private String							lastName;
	
	private String							mobilePhoneId;
	
	private String							passwordHash;
	
	@ManyToOne
	private AbstractTouchpoint				preferredTouchpoint;
	
	@ManyToMany(mappedBy = "customers")
	private Collection<AbstractTouchpoint>	touchpoints			= new HashSet<AbstractTouchpoint>();
	
	/*
	 * Ü1.2
	 */
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	// @OneToMany(mappedBy="customer", fetch=FetchType.EAGER)
	private Collection<CustomerTransaction>	transactions;
	
	public Customer() {
		logger.info("<constructor>");
	}
	
	public Customer(final int id) {
		this.id = id;
	}
	
	public Customer(final String firstName, final String lastName, final Gender gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
	}
	
	public Customer(final String firstName, final String lastName, final Gender gender, final String mobilePhoneId, final String eMail, final String password) {
		this(firstName, lastName, gender);
		this.mobilePhoneId = mobilePhoneId;
		this.setEmail(eMail);
		this.setPassword(password);
	}
	
	public void addTouchpoint(final AbstractTouchpoint touchpoint) {
		this.touchpoints.add(touchpoint);
	}
	
	@Override
	public boolean equals(final Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}
	
	public Address getAddress() {
		return this.address;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getFullName() {
		return this.getFirstName() + " " + this.getLastName();
	}
	
	public Gender getGender() {
		return this.gender;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getMobilePhoneId() {
		return this.mobilePhoneId;
	}
	
	public String getPasswordHash() {
		return this.passwordHash;
	}
	
	public AbstractTouchpoint getPreferredTouchpoint() {
		return this.preferredTouchpoint;
	}
	
	public Collection<AbstractTouchpoint> getTouchpoints() {
		return this.touchpoints;
	}
	
	public Collection<CustomerTransaction> getTransactions() {
		return this.transactions;
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
	
	public void setAddress(final Address address) {
		this.address = address;
	}
	
	public void setEmail(final String email) {
		this.email = email;
	}
	
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}
	
	public void setGender(final Gender gd) {
		this.gender = gd;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	/*
	 * lifecycle logging
	 */
	
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
	
	public void setMobilePhoneId(final String mobilePhoneID) {
		this.mobilePhoneId = mobilePhoneID;
	}
	
	public void setPassword(final String password) {
		this.passwordHash = DigestUtils.md5Hex(password);
		;
	}
	
	public void setPreferredTouchpoint(final AbstractTouchpoint preferredTouchpoint) {
		this.preferredTouchpoint = preferredTouchpoint;
	}
	
	public void setTouchpoints(final HashSet<AbstractTouchpoint> touchpoints) {
		this.touchpoints = touchpoints;
	}
	
	public void setTransactions(final Collection<CustomerTransaction> transactions) {
		this.transactions = transactions;
	}
	
	@Override
	public String toString() {
		return "{Customer " + this.id + " " + this.firstName + " " + this.lastName + " (" + this.gender + ") " + this.email + ", " + this.mobilePhoneId + ", " + this.address + "}";
	}
}
