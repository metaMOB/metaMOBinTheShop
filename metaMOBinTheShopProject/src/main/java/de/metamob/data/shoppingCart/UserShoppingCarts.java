package de.metamob.data.shoppingCart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;


public class UserShoppingCarts implements Serializable{
	
	Map<AbstractTouchpoint, List<ShoppingItem>> shoppingCards;
	
	public UserShoppingCarts(){
		shoppingCards = new HashMap<AbstractTouchpoint, List<ShoppingItem>>();
	}
	
	public Set<AbstractTouchpoint> getTouchpoints(){
		return shoppingCards.keySet();
	}
	
	public List<ShoppingItem> getShoppingCard(AbstractTouchpoint tochpoint){
		if(shoppingCards.containsKey(tochpoint)){
			return shoppingCards.get(tochpoint);
		}
		List<ShoppingItem> list = new ArrayList<ShoppingItem>();
		shoppingCards.put(tochpoint, list);
		return list;
	}
}
