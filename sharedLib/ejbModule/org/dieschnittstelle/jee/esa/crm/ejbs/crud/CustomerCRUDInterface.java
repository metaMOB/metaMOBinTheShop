package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import org.dieschnittstelle.jee.esa.crm.entities.Customer;

public interface CustomerCRUDInterface {
	public Customer createCustomer(Customer customer);
	public Customer readCustomer(int id);
	public Customer updateCustomer(Customer customer);
	public boolean deleteCustomer(int id);
}
