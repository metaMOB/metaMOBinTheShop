package org.dieschnittstelle.jee.esa.erp.ejbs;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.exceptions.ProductNotInStockException;

public interface ShoppingSessionFacadeInterface {
	public void addProduct(AbstractProduct product, int units);
	
	public void purchase() throws ProductNotInStockException;
	
	public void reset();
	
	public void setCustomer(Customer customer);
	
	public void setTouchpoint(AbstractTouchpoint touchpoint);
	
	public void verifyCampaigns();
}
