package de.metamob.data.shoppingCart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;


public class UserShoppingCarts implements Serializable{
	
	Map<AbstractTouchpoint, UserShoppingCart> shoppingCards;
	
	public UserShoppingCarts(){
		shoppingCards = new HashMap<AbstractTouchpoint, UserShoppingCart>();
	}
	
	public Set<AbstractTouchpoint> getTouchpoints(){
		return shoppingCards.keySet();
	}
	
	public UserShoppingCart getShoppingCard(AbstractTouchpoint tochpoint){
		if(shoppingCards.containsKey(tochpoint)){
			return shoppingCards.get(tochpoint);
		}
		UserShoppingCart list = new UserShoppingCart();
		shoppingCards.put(tochpoint, list);
		return list;
	}
	
	public void removeShoppingCard(AbstractTouchpoint tochpoint){
		shoppingCards.remove(tochpoint);
		System.out.println("keySet: "+getTouchpoints().size());
	}
	
	public int getNumOfUnits(){
		int numOfUnits = 0;
		Collection<UserShoppingCart> allShoppingCarts = shoppingCards.values();
		for (UserShoppingCart tempCart: allShoppingCarts){
			for (ShoppingItem tempItem: tempCart){
				numOfUnits += tempItem.getUnits();
			}
		}
		return numOfUnits;
	}
}
