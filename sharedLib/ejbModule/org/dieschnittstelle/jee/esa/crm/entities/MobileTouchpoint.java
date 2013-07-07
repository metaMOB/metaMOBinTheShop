package org.dieschnittstelle.jee.esa.crm.entities;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.jboss.logging.Logger;

@Entity
@DiscriminatorValue("mobile")
public class MobileTouchpoint extends AbstractTouchpoint {
	
	protected static Logger				logger				= Logger.getLogger(MobileTouchpoint.class);
	
	/**
	 *
	 */
	private static final long			serialVersionUID	= -3020587110269172721L;
	
	@ElementCollection
	private final Collection<String>	mobilePhoneIds		= new HashSet<String>();
	
	public MobileTouchpoint() {
		logger.info("<constructor>");
	}
	
	public MobileTouchpoint(final String mobilePhoneId) {
		this.addMobilePhoneId(mobilePhoneId);
	}
	
	public void addMobilePhoneId(final String mobilePhoneId) {
		this.mobilePhoneIds.add(mobilePhoneId);
	}
	
	public Collection<String> getMobilePhoneIds() {
		return this.mobilePhoneIds;
	}
	
	@PostLoad
	public void onPostLoad() {
		logger.info("@PostLoad: " + this);
	}
	
	/*
	 * lifecycle logging
	 */
	
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
	
	@Override
	public String toString() {
		return "{MobileTouchpoint " + this.id + "/" + this.erpPointOfSaleId + " " + this.mobilePhoneIds + "}";
	}
	
}
