package org.dieschnittstelle.jee.esa.erp.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractProduct implements Serializable {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= 6940403029597060153L;
	
	@Id
	@GeneratedValue
	private int					id;
	
	private String				name;
	
	private int					price;
	
	private String 				imgURL;
	
	public AbstractProduct() {
		
	}
	
	public AbstractProduct(final String name, final String imgURL) {
		this.name = name;
		this.imgURL = imgURL;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getPrice() {
		return this.price;
	}
	
	@Override
	public int hashCode() {
		return (this.name + this.id).hashCode();
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public void setPrice(final int price) {
		this.price = price;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
}
