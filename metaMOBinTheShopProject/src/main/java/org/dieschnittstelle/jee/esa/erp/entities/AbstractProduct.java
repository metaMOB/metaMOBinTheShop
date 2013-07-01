package org.dieschnittstelle.jee.esa.erp.entities;

import java.io.Serializable;


public abstract class AbstractProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6940403029597060153L;

	private int id;

	private String name;

	public AbstractProduct() {

	}

	public AbstractProduct(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
