package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import javax.ejb.Local;

import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;

@Local
public interface CustomerTransactionCRUDLocal extends CustomerTransactionCRUDInterface{
	public boolean createTransaction(CustomerTransaction transaction);
}
