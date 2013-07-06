package org.dieschnittstelle.jee.esa.erp.entities;

import org.codehaus.jackson.annotate.JsonCreator;

public enum ProductType {

	BREAD, ROLL, PASTRY;
	
	@JsonCreator
	public static ProductType deserialise(String pt) {	
		return ProductType.valueOf(ProductType.class,pt);
	}
	
	public static String toReadableString(ProductType type){
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
	
	public static ProductType fromReadableString (String str){
		if (str.equals("Brot")){
			return BREAD;
		}else if (str.equals("Brötchen")){
			return ROLL;
		}else if (str.equals("Kuchen")){
			return PASTRY;
		}else{
			return null;
		}
	}
}
