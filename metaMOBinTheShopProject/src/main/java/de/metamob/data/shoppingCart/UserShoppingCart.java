package de.metamob.data.shoppingCart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

public class UserShoppingCart implements Set<ShoppingItem>, Serializable {

	private static final long serialVersionUID = 6294361823978122026L;

	Map<AbstractProduct, ShoppingItem> shoppingItemList;

	public UserShoppingCart(){
		this.shoppingItemList = new HashMap<AbstractProduct, ShoppingItem>();
	}

	@Override
	public boolean add(final ShoppingItem e) {
		final AbstractProduct ap = e.getProduct();
		if (this.shoppingItemList.containsKey(ap)){
			this.shoppingItemList.get(ap).incUnits();
			return true;
		}
		this.shoppingItemList.put(ap, e);
		return true;
	}


	@Override
	public boolean addAll(final Collection<? extends ShoppingItem> c) {
		for (final ShoppingItem si : c){
			this.add(si);
		}
		return true;
	}


	@Override
	public void clear() {
		this.shoppingItemList.clear();
	}


	@Override
	public boolean contains(final Object o) {
		return this.shoppingItemList.containsValue(0);
	}


	@Override
	public boolean containsAll(final Collection<?> c) {
		boolean b = true;
		for (final Object o:c){
			if(!this.contains(0)){
				b = false;
			}
		}
		return b;
	}

	private List<ShoppingItem> getAsList(){
		final List<ShoppingItem> lst = new ArrayList<ShoppingItem>();
		final Set<java.util.Map.Entry<AbstractProduct,ShoppingItem>> entrys = this.shoppingItemList.entrySet();
		for(final java.util.Map.Entry<AbstractProduct,ShoppingItem>  e: entrys){
			lst.add(e.getValue());
		}
		return lst;
	}

	@Override
	public boolean isEmpty() {
		return this.shoppingItemList.isEmpty();
	}


	@Override
	public Iterator<ShoppingItem> iterator() {
		return this.getAsList().iterator();
	}

	@Override
	public boolean remove(final Object o) {
		 this.shoppingItemList.remove(((ShoppingItem)o).getProduct());
		 return true;
	}


	@Override
	public boolean removeAll(final Collection<?> c) {
		for (final Object o:c){
			this.remove(o);
		}
		return true;
	}


	@Override
	public boolean retainAll(final Collection<?> c) {
		/*List<ShoppingItem> lst = getAsList();
		for (ShoppingItem item : lst){
			c.add(item);
		}*/

		return true;
	}


	@Override
	public int size() {
		this.shoppingItemList.size();
		return 0;
	}


	@Override
	public Object[] toArray() {
		return this.getAsList().toArray();
	}


	@Override
	public <T> T[] toArray(final T[] a) {
		return this.getAsList().toArray(a);
	}
}
