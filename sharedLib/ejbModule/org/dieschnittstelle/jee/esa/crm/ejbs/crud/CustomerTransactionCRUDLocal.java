package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import java.util.List;

import javax.ejb.Local;

import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;

@Local
public interface CustomerTransactionCRUDLocal extends CustomerTransactionCRUDInterface {
	public boolean createTransaction(CustomerTransaction transaction);
	public List<CrmProductBundle> readCrmProductBundle(CustomerTransaction transaction);
}
