package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.jboss.logging.Logger;

@Stateless
public class CustomerCRUDStateless implements CustomerCRUDRemote, CustomerCRUDLocal {

	protected static Logger logger = Logger.getLogger(CustomerCRUDStateless.class);

	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;

	@Override
	public Customer createCustomer(final Customer customer) {
		logger.info("createCustomer(): before persist(): " + customer);
		this.em.persist(customer);

		logger.info("createdCustomer(): after persist(): " + customer);

		return customer;
	}

	@Override
	public boolean deleteCustomer(final int id) {
		logger.info("deleteCustomer(): " + id);

		this.em.remove(this.em.find(Customer.class,id));

		logger.info("deleteCustomer(): done");

		return true;
	}

	@Override
	public Customer readCustomer(final int id) {
		logger.info("readCustomer(): " + id);

		final Customer customer = this.em.find(Customer.class, id);

		logger.info("readCustomer(): " + customer);

		return customer;
	}

	@Override
	public Customer readCustomer(final String email, final String passwordHash) {
		if ((email==null) || (passwordHash==null)){
			return null;
		}
		final String queryString = "SELECT a FROM Customer a WHERE a.email = '" + email + "' AND a.passwordHash = '" + passwordHash+"'";
		logger.info("check: login: "+email+" password-hash: " +passwordHash);
    	final List<Customer> lst = this.em.createQuery(queryString).getResultList();
		if(lst.size()==0){
    		return null;
    	}else{
    		return lst.get(0);
    	}
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		logger.info("updateCustomer(): before merge(): " + customer);
		customer = this.em.merge(customer);

		logger.info("updateCustomer(): after merge(): " + customer);
		return customer;
	}

	@Override
	public Customer updateCustomerWithSleep(Customer customer,final long sleep) {
		logger.info("sleep" + sleep + "@" + this + ": entity manager is: " + this.em);
		logger.info("sleep" + sleep + "@" + this + ": before merge(): got remote: " + customer);
		// we read out the customer using the provided method
		logger.info("sleep" + sleep + "@" + this + ": before merge(): got local: " + this.readCustomer(customer.getId()));

		customer = this.em.merge(customer);
		logger.info("sleep" + sleep + "@" + this + ": after merge(): " + customer);

		try {
			Thread.sleep(sleep);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("sleep" + sleep + "@" + this + ": after sleep(): " + customer);

		return customer;
	}
}
