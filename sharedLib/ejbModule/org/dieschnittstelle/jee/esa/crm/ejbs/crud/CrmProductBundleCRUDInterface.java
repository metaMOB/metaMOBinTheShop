package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import java.util.List;

import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;

public interface CrmProductBundleCRUDInterface {
	public CrmProductBundle createCrmProductBundle(CrmProductBundle bundle);
	public CrmProductBundle readCrmProductBundle(int id);
	public CrmProductBundle updateCrmProductBundle(CrmProductBundle bundle);
	public void CrmProductBundle(CrmProductBundle bundle);
}
