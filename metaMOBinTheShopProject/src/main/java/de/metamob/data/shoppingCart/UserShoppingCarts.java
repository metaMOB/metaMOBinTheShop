package de.metamob.data.shoppingCart;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;


public class UserShoppingCarts implements Serializable{

	/**
	 *
	 */
	private static final long	serialVersionUID	= -5254848485388621500L;
	Map<AbstractTouchpoint, UserShoppingCart> shoppingCards;

	public UserShoppingCarts(){
		this.shoppingCards = new HashMap<AbstractTouchpoint, UserShoppingCart>();
	}

	public int getNumOfUnits(){
		int numOfUnits = 0;
		final Collection<UserShoppingCart> allShoppingCarts = this.shoppingCards.values();
		for (final UserShoppingCart tempCart: allShoppingCarts){
			for (final ShoppingItem tempItem: tempCart){
				numOfUnits += tempItem.getUnits();
			}
		}
		return numOfUnits;
	}

	public UserShoppingCart getShoppingCard(final AbstractTouchpoint tochpoint){
		if(this.shoppingCards.containsKey(tochpoint)){
			return this.shoppingCards.get(tochpoint);
		}
		final UserShoppingCart list = new UserShoppingCart();
		this.shoppingCards.put(tochpoint, list);
		return list;
	}

	public Set<AbstractTouchpoint> getTouchpoints(){
		return this.shoppingCards.keySet();
	}

	public void removeShoppingCard(final AbstractTouchpoint tochpoint){
		this.shoppingCards.remove(tochpoint);
		System.out.println("keySet: "+this.getTouchpoints().size());
	}
}
