package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.jboss.logging.Logger;

@Stateless
public class AdressCRUDStateless implements AdressCRUDLocal, AdressCRUDRemote  {

protected static Logger logger = Logger.getLogger(CustomerCRUDStateless.class);

	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;

	@Override
	public Address createAddress(final Address adress) {
		this.em.persist(adress);
		return adress;
	}

	@Override
	public void deleteAddress(final Address adress) {
		this.em.remove(adress);
	}

	@Override
	public Address readAddress(final int id) {
		return this.em.find(Address.class, id);
	}

	@Override
	public Address updateAddress(final Address adress) {
		return this.em.merge(adress);
	}
}
