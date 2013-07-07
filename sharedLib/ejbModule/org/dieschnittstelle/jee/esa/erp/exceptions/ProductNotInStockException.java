package org.dieschnittstelle.jee.esa.erp.exceptions;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

public class ProductNotInStockException extends Exception {
	private static final long		serialVersionUID	= 1195345974206741542L;
	
	private final AbstractProduct	product;
	private final int				units;
	
	public ProductNotInStockException(final AbstractProduct product, final int units) {
		this.product = product;
		this.units = units;
	}
	
	public AbstractProduct getProduct() {
		return this.product;
	}
	
	public int getUnits() {
		return this.units;
	}
}
