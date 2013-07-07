package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;

/**
 * very rudimentary implementation without any logging...
 */
@Stateless
public class PointOfSaleCRUDStateless implements PointOfSaleCRUDRemote, PointOfSaleCRUDLocal {

	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;

	@Override
	public PointOfSale createPointOfSale(final PointOfSale pos) {
		this.em.persist(pos);
		return pos;
	}

	@Override
	public boolean deletePointOfSale(final int posId) {
		this.em.remove(this.em.find(PointOfSale.class,posId));
		return true;
	}

	@Override
	public PointOfSale readPointOfSale(final int posId) {
		return this.em.find(PointOfSale.class,posId);
	}

}
