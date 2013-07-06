package org.dieschnittstelle.jee.esa.crm.ejbs;

import java.util.List;

import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;

public interface ShoppingCartInterface {
	public void addProductBundle(CrmProductBundle product);
	public List<CrmProductBundle> getProductBundles();
	public void clear();
}
