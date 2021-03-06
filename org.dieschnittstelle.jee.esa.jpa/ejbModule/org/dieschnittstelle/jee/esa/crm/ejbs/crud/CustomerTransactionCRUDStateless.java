package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;
import org.jboss.logging.Logger;

@Stateless(name = "CustomerTransactionCRUD")
public class CustomerTransactionCRUDStateless implements CustomerTransactionCRUDLocal, CustomerTransactionCRUDRemote {
	
	protected static Logger	logger	= Logger.getLogger(CustomerTransactionCRUDStateless.class);
	
	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager	em;
	
	@Override
	public boolean createTransaction(final CustomerTransaction transaction) {
		logger.info("createTransaction(): " + transaction);
		
		// check whether the transaction fields are detached or not
		logger.info("createTransaction(): customer attached (before): " + this.em.contains(transaction.getCustomer()));
		logger.info("createTransaction(): touchpoint attached (before): " + this.em.contains(transaction.getTouchpoint()));
		/*
		 * Ü1.1
		 */
		// persist each bundle
		/*
		 * for (final CrmProductBundle bundle : transaction.getProducts()) {
		 * logger.info("createTransaction(): will manually persist bundle: " +
		 * bundle); this.em.persist(bundle);
		 * logger.info("createTransaction(): persisted bundle: " + bundle); }
		 */
		
		// persit the transaction
		this.em.persist(transaction);
		
		logger.info("createTransaction(): done.");
		
		return true;
	}
	
	@Override
	public Collection<CustomerTransaction> readAllTransactionsForCustomer(final Customer customer) {
		logger.info("readAllTransactionsForCustomer(): " + customer);
		
		final Query query = this.em.createQuery("SELECT t FROM CustomerTransaction t WHERE t.customer = " + customer.getId());
		logger.info("readAllTransactionsForCustomer(): created query: " + query);
		
		final List<CustomerTransaction> trans = query.getResultList();
		logger.info("readAllTransactionsForCustomer(): " + trans);
		logger.info("readAllTransactionsForCustomer(): class is: " + (trans == null ? "<null pointer>" : String.valueOf(trans.getClass())));
		
		return trans;
	}
	
	@Override
	public Collection<CustomerTransaction> readAllTransactionsForTouchpoint(AbstractTouchpoint touchpoint) {
		logger.info("readAllTransactionsForTouchpoint(): " + touchpoint);
		// check the transactions on the touchpoint
		logger.info("readAllTransactionsForTouchpoint(): before merge transactions are: " + touchpoint.getTransactions());
		
		touchpoint = this.em.find(AbstractTouchpoint.class, touchpoint.getId());
		logger.info("touchpoint queried.");
		
		// now read out the transactions
		final Collection<CustomerTransaction> trans = touchpoint.getTransactions();
		logger.info("readAllTransactionsForTouchpoint(): transactions are: " + trans);
		logger.info("readAllTransactionsForTouchpoint(): class is: " + (trans == null ? "<null pointer>" : String.valueOf(trans.getClass())));
		
		return trans;
	}
	
	@Override
	public List<CustomerTransaction> readAllTransactionsForTouchpointAndCustomer(final AbstractTouchpoint touchpoint, final Customer customer) {
		logger.info("readAllTransactionsForTouchpointAndCustomer(): " + touchpoint + " / " + customer);
		
		final Query query = this.em.createQuery("SELECT t FROM CustomerTransaction t WHERE t.customer = " + customer.getId() + " AND t.touchpoint = " + touchpoint.getId());
		logger.info("readAllTransactionsForTouchpointAndCustomer(): created query: " + query);
		
		final List<CustomerTransaction> trans = query.getResultList();
		logger.info("readAllTransactionsForTouchpointAndCustomer(): " + trans);
		logger.info("readAllTransactionsForTouchpointAndCustomer(): class is: " + (trans == null ? "<null pointer>" : String.valueOf(trans.getClass())));
		
		return trans;
	}
	
	@Override
	public List<CrmProductBundle> readCrmProductBundle(CustomerTransaction transaction) {
		return this.em.createQuery("select a from CrmProductBundle a where a.customerTransaction.id = " + transaction.getId()).getResultList();
	}
}
