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
	public PointOfSale createPointOfSale(PointOfSale pos) {
		em.persist(pos);		
		return pos;
	}

	@Override
	public PointOfSale readPointOfSale(int posId) {
		return em.find(PointOfSale.class,posId);
	}

	@Override
	public boolean deletePointOfSale(int posId) {
		em.remove(em.find(PointOfSale.class,posId));
		return true;
	}

}
