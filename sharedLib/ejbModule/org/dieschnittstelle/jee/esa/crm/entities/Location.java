package org.dieschnittstelle.jee.esa.crm.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jboss.logging.Logger;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Location implements Serializable {
	
	protected static Logger		logger				= Logger.getLogger(Location.class);
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= -131090102062445239L;
	
	private float				geoLat;
	
	private float				geoLong;
	
	private int					id					= -1;
	
	@Override
	public boolean equals(final Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}
	
	public float getGeoLat() {
		return this.geoLat;
	}
	
	public float getGeoLong() {
		return this.geoLong;
	}
	
	@Id
	@GeneratedValue
	public int getId() {
		return this.id;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
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
	
	/*
	 * lifecycle logging
	 */
	
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
	
	public void setGeoLat(final float geoLat) {
		this.geoLat = geoLat;
	}
	
	public void setGeoLong(final float geoLong) {
		this.geoLong = geoLong;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
}
