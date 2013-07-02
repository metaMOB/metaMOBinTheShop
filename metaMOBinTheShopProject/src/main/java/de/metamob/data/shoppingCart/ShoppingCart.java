package de.metamob.data.shoppingCart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.markup.html.WebPage;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

public class ShoppingCart extends WebPage{
	
	HashMap <Integer, Object> myShoppingList = new HashMap<Integer, Object>();
	
	public HashMap<Integer, Object> getMyShoppingList() {
		return myShoppingList;
	}

	public void setMyShoppingList(HashMap<Integer, Object> myShoppingList) {
		this.myShoppingList = myShoppingList;
	}

	public ShoppingCart(){
		
	}
	
	public void addToCart (StockItem stockItem){
		int tempID = stockItem.getProduct().getId();
		System.out.println ("ID: "+tempID);
		if (myShoppingList.containsKey(tempID)){
			System.out.println ("IN LIST");
			((ShoppingItem)myShoppingList.get(tempID)).increment();
			//myShoppingList.get(tempID).increment();
		} else {
			System.out.println ("NOT IN LIST");
			ShoppingItem tempItem = new ShoppingItem(stockItem);
			System.out.println ("NOT IN  1");
			
			myShoppingList.put(tempID, tempItem);
			System.out.println ("NOT IN  2");
			
		}
	}
	
	public void removeFromCart (StockItem stockItem){
		int tempID = stockItem.getProduct().getId();
		if (myShoppingList.containsKey(tempID)){
			myShoppingList.remove(tempID);
		} 
	}
	
	public void setPieces (StockItem stockItem, int pieces){
		int tempID = stockItem.getProduct().getId();
		if (myShoppingList.containsKey(tempID)){
			//myShoppingList.get(tempID).setPieces(pieces);
		}
	}
	
	@Override
	public String toString(){
		String result = "";
		Iterator iter = myShoppingList.entrySet().iterator();
		while (iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			result += entry.getKey()+" = "+ ((ShoppingItem)entry.getValue()).getPieces()+", ";
		}
		return result;	
	}
	
}
