package de.metamob.data.shoppingCart;

import java.io.Serializable;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
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
		shoppingItemList = new HashMap<AbstractProduct, ShoppingItem>();
	}

	@Override
	public boolean add(ShoppingItem e) {
		AbstractProduct ap = e.getProduct();
		if (shoppingItemList.containsKey(ap)){
			shoppingItemList.get(ap).incUnits();
			return true;
		}
		shoppingItemList.put(ap, e);
		return true;
	}


	@Override
	public boolean addAll(Collection<? extends ShoppingItem> c) {
		for (ShoppingItem si : c){
			add(si);
		}
		return true;
	}


	@Override
	public void clear() {
		shoppingItemList.clear();
	}


	@Override
	public boolean contains(Object o) {
		return shoppingItemList.containsValue(0);
	}


	@Override
	public boolean containsAll(Collection<?> c) {
		boolean b = true;
		for (Object o:c){
			if(!contains(0)){
				b = false;
			}
		}
		return b;
	}

	@Override
	public boolean isEmpty() {
		return shoppingItemList.isEmpty();
	}
	
	private List<ShoppingItem> getAsList(){
		List<ShoppingItem> lst = new ArrayList<ShoppingItem>();
		Set<java.util.Map.Entry<AbstractProduct,ShoppingItem>> entrys = shoppingItemList.entrySet(); 
		for(java.util.Map.Entry<AbstractProduct,ShoppingItem>  e: entrys){
			lst.add((ShoppingItem)e.getValue());
		}
		return lst;
	}
	

	@Override
	public Iterator<ShoppingItem> iterator() {
		return getAsList().iterator();
	}

	@Override
	public boolean remove(Object o) {
		 shoppingItemList.remove(((ShoppingItem)o).getProduct());
		 return true;
	}


	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object o:c){
			remove(o);
		}
		return true;
	}


	@Override
	public boolean retainAll(Collection<?> c) {
		/*List<ShoppingItem> lst = getAsList();
		for (ShoppingItem item : lst){
			c.add(item);
		}*/
		
		return true;
	}


	@Override
	public int size() {
		shoppingItemList.size();
		return 0;
	}


	@Override
	public Object[] toArray() {
		return getAsList().toArray();
	}


	@Override
	public <T> T[] toArray(T[] a) {
		return getAsList().toArray(a);
	}	
}
