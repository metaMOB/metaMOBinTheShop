package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;

@Local
public interface CustomerTransactionCRUDLocal {
	
	public boolean createTransaction(CustomerTransaction transaction);

	public Collection<CustomerTransaction> readAllTransactionsForTouchpoint(AbstractTouchpoint touchpoint);

	public Collection<CustomerTransaction> readAllTransactionsForCustomer(Customer customer);

	public Collection<CustomerTransaction> readAllTransactionsForTouchpointAndCustomer(AbstractTouchpoint touchpoint,Customer customer);

}
