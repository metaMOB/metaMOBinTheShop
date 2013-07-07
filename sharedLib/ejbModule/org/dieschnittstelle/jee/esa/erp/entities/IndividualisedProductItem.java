package org.dieschnittstelle.jee.esa.erp.entities;

import java.io.Serializable;

import javax.persistence.Entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class IndividualisedProductItem extends AbstractProduct implements Serializable {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= 5109263395081656350L;
	
	private int					expirationAfterStocked;
	
	private ProductType			productType;
	
	public IndividualisedProductItem() {
		
	}
	
	public IndividualisedProductItem(final String name, final ProductType type, final int expirationAfterStocked, final int price) {
		super(name);
		this.productType = type;
		this.expirationAfterStocked = expirationAfterStocked;
		this.setPrice(price);
	}
	
	@Override
	public boolean equals(final Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}
	
	public int getExpirationAfterStocked() {
		return this.expirationAfterStocked;
	}
	
	public ProductType getProductType() {
		return this.productType;
	}
	
	@Override
	public int hashCode() {
		final int code = HashCodeBuilder.reflectionHashCode(this);
		return code;
	}
	
	public void setExpirationAfterStocked(final int expirationAfterStocked) {
		this.expirationAfterStocked = expirationAfterStocked;
	}
	
	public void setProductType(final ProductType productType) {
		this.productType = productType;
	}
	
	@Override
	public String toString() {
		return "{IProductItem " + this.getId() + ", " + this.getName() + ", " + this.productType + "}";
	}
	
}
