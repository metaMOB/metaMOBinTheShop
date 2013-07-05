package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.jboss.logging.Logger;

@Stateless
public class AdressCRUDStateless implements AdressCRUDLocal, AdressCRUDRemote  {

protected static Logger logger = Logger.getLogger(CustomerCRUDStateless.class);
	
	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;
	
	@Override
	public Address createAddress(Address adress) {
		em.persist(adress);
		return adress;
	}

	@Override
	public Address readAddress(int id) {
		return em.find(Address.class, id);
	}

	@Override
	public Address updateAddress(Address adress) {
		return em.merge(adress);
	}

	@Override
	public void deleteAddress(Address adress) {
		em.remove(adress);
	}
}
