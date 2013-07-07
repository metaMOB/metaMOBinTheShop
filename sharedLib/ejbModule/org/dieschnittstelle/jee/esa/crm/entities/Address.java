package org.dieschnittstelle.jee.esa.crm.entities;

import java.io.Serializable;

import javax.persistence.Entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jboss.logging.Logger;

@Entity
public class Address extends Location implements Serializable {
	
	protected static Logger		logger				= Logger.getLogger(Address.class);
	
	private static final long	serialVersionUID	= 1L;
	
	private String				city;
	
	private String				houseNr;
	
	private String				street;
	
	private String				zipCode;
	
	public Address() {
		logger.info("<constructor>");
	}
	
	public Address(final String street, final String houseNr, final String zipCode, final String city) {
		this.street = street;
		this.houseNr = houseNr;
		this.zipCode = zipCode;
		this.city = city;
	}
	
	public Address(final String street, final String houseNr, final String zipCode, final String city, final float geoLat, final float geoLong) {
		this(street, houseNr, zipCode, city);
		this.setGeoLat(geoLat);
		this.setGeoLong(geoLong);
	}
	
	@Override
	public boolean equals(final Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String getHouseNr() {
		return this.houseNr;
	}
	
	public String getStreet() {
		return this.street;
	}
	
	public String getZipCode() {
		return this.zipCode;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	public void setCity(final String city) {
		this.city = city;
	}
	
	public void setHouseNr(final String houseNr) {
		this.houseNr = houseNr;
	}
	
	public void setStreet(final String street) {
		this.street = street;
	}
	
	public void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Override
	public String toString() {
		return "{Address " + this.getId() + ", " + this.street + " " + this.houseNr + ", " + this.zipCode + " " + this.city + "}";
	}
}
