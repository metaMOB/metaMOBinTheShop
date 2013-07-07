package org.dieschnittstelle.jee.esa.erp.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProductAtPosPK implements Serializable {
	
	/**
	 *
	 */
	
	private static final long	serialVersionUID	= 9113210426279286629L;
	
	@Id
	@ManyToOne(cascade = CascadeType.MERGE)
	private PointOfSale			pos;
	
	@Id
	@ManyToOne(cascade = CascadeType.MERGE)
	private AbstractProduct		product;
	
	public ProductAtPosPK() {
	}
	
	public ProductAtPosPK(final AbstractProduct product, final PointOfSale pos) {
		this.product = product;
		this.pos = pos;
	}
	
	@Override
	public boolean equals(final Object obj) {
		
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		
		return (this.product.getId() == ((ProductAtPosPK) obj).getProduct().getId()) && (this.pos.getId() == ((ProductAtPosPK) obj).getPos().getId());
	}
	
	public PointOfSale getPos() {
		return this.pos;
	}
	
	public AbstractProduct getProduct() {
		return this.product;
	}
	
	// see http://uaihebert.com/?p=42
	@Override
	public int hashCode() {
		return this.product.hashCode() + this.pos.hashCode();
	}
	
	public void setPos(final PointOfSale pos) {
		this.pos = pos;
	}
	
	public void setProduct(final AbstractProduct product) {
		this.product = product;
	}
	
}
