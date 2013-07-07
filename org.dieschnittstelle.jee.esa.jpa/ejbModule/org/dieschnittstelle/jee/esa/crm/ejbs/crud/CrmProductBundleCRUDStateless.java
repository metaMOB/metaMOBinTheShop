package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;

import org.jboss.logging.Logger;

@Stateless(name="CrmProductBundleCRUD")
public class CrmProductBundleCRUDStateless implements CrmProductBundleCRUDLocal, CrmProductBundleCRUDRemote {

	protected static Logger logger = Logger.getLogger(CustomerCRUDStateless.class);

	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;
	
	@Override
	public CrmProductBundle createCrmProductBundle(CrmProductBundle bundle) {
		em.persist(bundle);
		return bundle;
	}

	@Override
	public CrmProductBundle readCrmProductBundle(int id) {
		return em.find(CrmProductBundle.class, id);
	}

	@Override
	public CrmProductBundle updateCrmProductBundle(CrmProductBundle bundle) {
		return em.merge(bundle);
	}

	@Override
	public void CrmProductBundle(CrmProductBundle bundle) {
		em.remove(bundle);
	}
}
