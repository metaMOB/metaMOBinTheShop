package org.dieschnittstelle.jee.esa.crm.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.jboss.logging.Logger;

/**
 * @author kreutel
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(namespace = "http://dieschnittstelle.org/jee/esa/crm/model")
@Entity
@DiscriminatorValue("stationary")
public class StationaryTouchpoint extends AbstractTouchpoint  implements Serializable {
	
	protected static Logger logger = Logger.getLogger(StationaryTouchpoint.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6123798695442913993L;
	
	/**
	 * we assume a onetoone assoc
	 */
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private Address location;
	
	public StationaryTouchpoint() {
		logger.info("<constructor>");
	}
	
	public StationaryTouchpoint(int erpPointOfSaleId) {
		this.setErpPointOfSaleId(erpPointOfSaleId);
	}

	public StationaryTouchpoint(int erpPointOfSaleId,String name,Address address) {
		super.setErpPointOfSaleId(erpPointOfSaleId);
		super.setName(name);
		this.setLocation(address);
	}
	
	public String toString() {
		return "{StationaryTouchpoint " + this.id + "/" + this.erpPointOfSaleId + " " + this.name + " " + this.location + "}";
	}
	
	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}
	
	/*
	 * lifecycle logging
	 */
	
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
	
}
