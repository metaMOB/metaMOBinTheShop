package de.metamob.data.shoppingCart;

import java.io.Serializable;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

public class ShoppingItem implements Serializable {
	
	private AbstractProduct product;
	int units;
	
	public ShoppingItem(AbstractProduct product){
		this.product = product;
	}
	
	public AbstractProduct getProduct() {
		return product;
	}
	
	public void setProduct(AbstractProduct product) {
		this.product = product;
	}
	
	public int getUnits() {
		return units;
	}
	
	public void setUnits(int units) {
		this.units = units;
	}
	
	public void incUnits() {
		this.units++;
	}
}
