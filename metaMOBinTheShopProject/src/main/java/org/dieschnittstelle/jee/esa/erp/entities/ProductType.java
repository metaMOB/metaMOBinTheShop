package org.dieschnittstelle.jee.esa.erp.entities;

import org.codehaus.jackson.annotate.JsonCreator;

public enum ProductType {

	BREAD, ROLL, PASTRY;
	
	@JsonCreator
	public static ProductType deserialise(String pt) {	
		return ProductType.valueOf(ProductType.class,pt);
	}
}
