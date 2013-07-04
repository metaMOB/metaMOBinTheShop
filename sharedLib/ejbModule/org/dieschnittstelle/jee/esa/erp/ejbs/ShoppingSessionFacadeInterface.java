package org.dieschnittstelle.jee.esa.erp.ejbs;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

public interface ShoppingSessionFacadeInterface {
	public void setTouchpoint(AbstractTouchpoint touchpoint);
	public void setCustomer(Customer customer);
	public void addProduct(AbstractProduct product, int units);
	public void verifyCampaigns();
	public void purchase() throws Exception;
}
