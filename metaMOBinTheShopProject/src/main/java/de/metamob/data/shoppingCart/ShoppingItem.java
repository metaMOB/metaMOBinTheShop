package de.metamob.data.shoppingCart;

import java.io.Serializable;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

public class ShoppingItem implements Serializable {

	/**
	 *
	 */
	private static final long	serialVersionUID	= -3775231088549708620L;
	private AbstractProduct product;
	int units;
	//int touchPoint;

	/*public int getTouchPoint() {
		return touchPoint;
	}*/

	/*public void setTouchPoint(int touchPoint) {
		this.touchPoint = touchPoint;
	}*/

	public ShoppingItem(final AbstractProduct product){
		this.product = product;
		this.units = 1;
	}

	public void decUnits() {
		if (this.units>1){
			this.units--;
		}
	}

	public AbstractProduct getProduct() {
		return this.product;
	}

	public int getUnits() {
		System.out.println("GET UNITS"+this.units);
		return this.units;
	}

	public void incUnits() {
		this.units++;
	}

	public void setProduct(final AbstractProduct product) {
		this.product = product;
	}

	public void setUnits(final int units) {
		System.out.println("SET UNITS"+units);
		this.units = units;
	}
}
