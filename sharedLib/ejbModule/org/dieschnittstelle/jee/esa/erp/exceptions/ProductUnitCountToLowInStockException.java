package org.dieschnittstelle.jee.esa.erp.exceptions;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

public class ProductUnitCountToLowInStockException extends ProductNotInStockException {
	private static final long serialVersionUID = -7062037140313705203L;
	
	public ProductUnitCountToLowInStockException(AbstractProduct product, int units){
		super(product,units);	
	}
}
