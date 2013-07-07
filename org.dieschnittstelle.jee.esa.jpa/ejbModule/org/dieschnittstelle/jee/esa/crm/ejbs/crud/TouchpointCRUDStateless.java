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
	public AbstractTouchpoint createTouchpoint(final AbstractTouchpoint touchpoint) {
		logger.info("createTouchpoint(): before persist(): " + touchpoint);
		this.em.persist(touchpoint);
		logger.info("createdtouchpoint(): after persist(): " + touchpoint);

		return touchpoint;
	}

	@Override
	public boolean deleteTouchpoint(final int id) {
		logger.info("deleteTouchpoint(): " + id);

		this.em.remove(this.em.find(AbstractTouchpoint.class,id));

		logger.info("deleteTouchpoint(): done");

		return true;
	}

	@Override
	public List<AbstractTouchpoint> readAllTouchpoints() {
		logger.info("readAllTouchpoint()");
		final Query query = this.em.createQuery("FROM AbstractTouchpoint");
		final List<AbstractTouchpoint> tps = query.getResultList();
		logger.info("readAllTouchpoints(): " + tps);
		return tps;
	}

	@Override
	public List<AbstractTouchpoint> readTouchpoins(final List<Integer> posIds) {
		if (posIds.size()==0){
			return new ArrayList<AbstractTouchpoint>();
		}
		final Query query = this.em.createQuery("SELECT a FROM AbstractTouchpoint a WHERE a.erpPointOfSaleId IN :values",AbstractTouchpoint.class);
		query.setParameter("values", posIds);
		return query.getResultList();
	}

	@Override
	public AbstractTouchpoint readTouchpoint(final int id) {
		logger.info("readTouchpoint(): " + id);

		final AbstractTouchpoint touchpoint = this.em.find(AbstractTouchpoint.class, id);

		logger.info("readTouchpoint(): " + touchpoint);

		return touchpoint;
	}

	@Override
	public AbstractTouchpoint updateTouchpoint(AbstractTouchpoint touchpoint) {
		logger.info("updateTouchpoint(): before merge(): " + touchpoint);
		touchpoint = this.em.merge(touchpoint);

		logger.info("updateTouchpoint(): after merge(): " + touchpoint);
		return touchpoint;
	}

}
