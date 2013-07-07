package org.dieschnittstelle.jee.esa.erp.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class ProductBundle implements Serializable {
	
	/**
	 *
	 */
	private static final long			serialVersionUID	= 1501911067906145681L;
	
	@Id
	@GeneratedValue
	private Integer						id;
	
	@ManyToOne
	private IndividualisedProductItem	product;
	
	private int							units;
	
	public ProductBundle() {
	}
	
	public ProductBundle(final IndividualisedProductItem product, final int units) {
		this.product = product;
		this.units = units;
	}
	
	@Override
	public boolean equals(final Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public IndividualisedProductItem getProduct() {
		return this.product;
	}
	
	public int getUnits() {
		return this.units;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, new String[] { "id" });
	}
	
	public void setId(final Integer id) {
		this.id = id;
	}
	
	public void setProduct(final IndividualisedProductItem product) {
		this.product = product;
	}
	
	public void setUnits(final int units) {
		this.units = units;
	}
	
	@Override
	public String toString() {
		return "{" + this.product + " (" + this.units + ")}";
	}
	
}
