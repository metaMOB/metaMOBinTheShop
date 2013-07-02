package de.metamob.data.shoppingCart;

import java.io.Serializable;

import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

public class ShoppingItem implements Serializable {
	private StockItem stockItem;
	private int pieces = 1;
	
	public StockItem getStockItem() {
		return stockItem;
	}

	public void setStockItem(StockItem stockItem) {
		this.stockItem = stockItem;
	}
	
	public int getPieces() {
		return pieces;
	}

	public void setPieces(int pieces) {
		if (pieces >=0 ){
			this.pieces = pieces;
		}
	}

	public ShoppingItem (StockItem stockItem){
		this.stockItem = stockItem;		
	}
	
	public void increment(){
		this.pieces++;
	}
}
