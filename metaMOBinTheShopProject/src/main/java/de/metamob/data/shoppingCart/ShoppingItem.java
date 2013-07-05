package de.metamob.data.shoppingCart;

import java.io.Serializable;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

public class ShoppingItem implements Serializable {
	
	private AbstractProduct product;
	int units;
	int touchPoint;
	
	public int getTouchPoint() {
		return touchPoint;
	}

	public void setTouchPoint(int touchPoint) {
		this.touchPoint = touchPoint;
	}

	public ShoppingItem(AbstractProduct product){
		this.product = product;
		this.units = 1;
	}
	
	public AbstractProduct getProduct() {
		return product;
	}
	
	public void setProduct(AbstractProduct product) {
		this.product = product;
	}
	
	public int getUnits() {
		System.out.println("GET UNITS"+units);
		return units;
	}
	
	public void setUnits(int units) {
		System.out.println("SET UNITS"+units);
		this.units = units;
	}
	
	public void incUnits() {
		this.units++;
	}
	
	public void decUnits() {
		if (units>1){
			this.units--;
		}
	}
}
