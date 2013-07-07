package org.dieschnittstelle.jee.esa.erp.entities;

import org.codehaus.jackson.annotate.JsonCreator;

public enum ProductType {
	
	BREAD, PASTRY, ROLL;
	
	@JsonCreator
	public static ProductType deserialise(final String pt) {
		return Enum.valueOf(ProductType.class, pt);
	}
	
	public static ProductType fromReadableString(final String str) {
		if (str.equals("Brot")) {
			return BREAD;
		} else if (str.equals("Brötchen")) {
			return ROLL;
		} else if (str.equals("Kuchen")) {
			return PASTRY;
		} else {
			return null;
		}
	}
	
	public static String toReadableString(final ProductType type) {
		switch (type) {
		case BREAD:
			return "Brot";
		case ROLL:
			return "Brötchen";
		case PASTRY:
			return "Kuchen";
		default:
			return "unbekannt";
		}
	}
}
