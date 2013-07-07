package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.jboss.logging.Logger;

@Stateless(name="TouchpointCRUD")
public class TouchpointCRUDStateless implements TouchpointCRUDRemote, TouchpointCRUDLocal {

protected static Logger logger = Logger.getLogger(TouchpointCRUDStateless.class);
	
	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;
	
	@Override
	public AbstractTouchpoint createTouchpoint(AbstractTouchpoint touchpoint) {
		logger.info("createTouchpoint(): before persist(): " + touchpoint);
		em.persist(touchpoint);
		logger.info("createdtouchpoint(): after persist(): " + touchpoint);

		return touchpoint;
	}

	@Override
	public AbstractTouchpoint readTouchpoint(int id) {
		logger.info("readTouchpoint(): " + id);

		AbstractTouchpoint touchpoint = em.find(AbstractTouchpoint.class, id);

		logger.info("readTouchpoint(): " + touchpoint);
		
		return touchpoint;
	}

	@Override
	public AbstractTouchpoint updateTouchpoint(AbstractTouchpoint touchpoint) {
		logger.info("updateTouchpoint(): before merge(): " + touchpoint);
		touchpoint = em.merge(touchpoint);
		
		logger.info("updateTouchpoint(): after merge(): " + touchpoint);
		return touchpoint;
	}

	@Override
	public boolean deleteTouchpoint(int id) {
		logger.info("deleteTouchpoint(): " + id);
		
		em.remove(em.find(AbstractTouchpoint.class,id));
				
		logger.info("deleteTouchpoint(): done");
		
		return true;
	}

	@Override
	public List<AbstractTouchpoint> readAllTouchpoints() {
		logger.info("readAllTouchpoint()");
		Query query = em.createQuery("FROM AbstractTouchpoint");
		List<AbstractTouchpoint> tps = (List<AbstractTouchpoint>) query.getResultList();
		logger.info("readAllTouchpoints(): " + tps);
		return tps;
	}

	@Override
	public List<AbstractTouchpoint> readTouchpoins(List<Integer> posIds) {
		if (posIds.size()==0){
			return new ArrayList<AbstractTouchpoint>();
		}
		Query query = em.createQuery("SELECT a FROM AbstractTouchpoint a WHERE a.erpPointOfSaleId IN :values",AbstractTouchpoint.class);
		query.setParameter("values", posIds);
		return (List<AbstractTouchpoint>) query.getResultList();
	}

}
