package org.dieschnittstelle.jee.esa.erp.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.jboss.logging.Logger;

@Entity
@Table(name = "stock")
@IdClass(ProductAtPosPK.class)
public class StockItem implements Serializable {
	protected static Logger		logger				= Logger.getLogger(StockItem.class);
	
	private static final long	serialVersionUID	= -523570877265085869L;
	
	@Id
	@ManyToOne(cascade = CascadeType.MERGE)
	private PointOfSale			pos;
	
	private int					price;
	
	@Id
	@ManyToOne(cascade = CascadeType.MERGE)
	private AbstractProduct		product;
	
	private int					units;
	
	public StockItem() {
		
	}
	
	public StockItem(final AbstractProduct product, final PointOfSale pos, final int units) {
		this.product = product;
		this.pos = pos;
	}
	
	public PointOfSale getPos() {
		return this.pos;
	}
	
	public int getPrice() {
		return this.price;
	}
	
	public AbstractProduct getProduct() {
		return this.product;
	}
	
	public int getUnits() {
		return this.units;
	}
	
	@PostLoad
	public void onPostLoad() {
		logger.info("onPostLoad(): " + this);
	}
	
	@PostPersist
	public void onPostPersist() {
		logger.info("onPostPersist(): " + this);
	}
	
	/*
	 * the lifecycle log messages
	 */
	
	@PostRemove
	public void onPostRemove() {
		logger.info("onPostRemove(): " + this);
	}
	
	@PostUpdate
	public void onPostUpdate() {
		logger.info("onPostUpdate(): " + this);
	}
	
	@PrePersist
	public void onPrePersist() {
		logger.info("onPrePersist(): " + this);
	}
	
	@PreRemove
	public void onPreRemove() {
		logger.info("onPreRemove(): " + this);
	}
	
	@PreUpdate
	public void onPreUpdate() {
		logger.info("onPreUpdate(): " + this);
	}
	
	public void setPos(final PointOfSale pos) {
		this.pos = pos;
	}
	
	public void setPrice(final int price) {
		this.price = price;
	}
	
	public void setProduct(final AbstractProduct product) {
		this.product = product;
	}
	
	public void setUnits(final int units) {
		this.units = units;
	}
	
	@Override
	public String toString() {
		return "{StockItemEntity " + this.price + ", " + this.product + "@" + this.pos + "}";
	}
	
}
