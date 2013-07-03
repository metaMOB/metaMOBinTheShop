package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import java.util.Collection;
import java.util.List;

import javax.ejb.Remote;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;

@Remote
public interface CustomerTransactionCRUDRemote {
	
	public Collection<CustomerTransaction> readAllTransactionsForTouchpoint(AbstractTouchpoint touchpoint);

	public Collection<CustomerTransaction> readAllTransactionsForCustomer(Customer customer);

	public Collection<CustomerTransaction> readAllTransactionsForTouchpointAndCustomer(AbstractTouchpoint touchpoint,Customer customer);

}
