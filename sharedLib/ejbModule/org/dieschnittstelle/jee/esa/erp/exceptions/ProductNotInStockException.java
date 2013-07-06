package org.dieschnittstelle.jee.esa.erp.exceptions;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

public class ProductNotInStockException extends Exception {
	private static final long serialVersionUID = 1195345974206741542L;
	
	private AbstractProduct product;
	private int units;
	
	public ProductNotInStockException(AbstractProduct product, int units) {
		this.product = product;
		this.units = units;
	}

	public AbstractProduct getProduct() {
		return product;
	}
	
	public int getUnits() {
		return units;
	}
}
