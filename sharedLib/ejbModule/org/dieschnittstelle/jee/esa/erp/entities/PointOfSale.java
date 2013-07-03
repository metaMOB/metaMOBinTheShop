package org.dieschnittstelle.jee.esa.erp.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PointOfSale implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3397160788902953608L;
	
	@Id
	@GeneratedValue
	private int id = -1;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
