package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import javax.ejb.Remote;

import org.dieschnittstelle.jee.esa.crm.entities.Customer;

@Remote
public interface CustomerCRUDRemote extends CustomerCRUDInterface {	
	public Customer updateCustomerWithSleep(Customer customer,long sleep);
}
