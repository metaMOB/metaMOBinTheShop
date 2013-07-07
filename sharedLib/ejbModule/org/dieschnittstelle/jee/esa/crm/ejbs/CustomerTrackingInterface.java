package org.dieschnittstelle.jee.esa.crm.ejbs;

import java.util.List;

import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;

public interface CustomerTrackingInterface {
	public void createTransaction(CustomerTransaction transaction);
	
	public List<CustomerTransaction> readAllTransactions();
}
