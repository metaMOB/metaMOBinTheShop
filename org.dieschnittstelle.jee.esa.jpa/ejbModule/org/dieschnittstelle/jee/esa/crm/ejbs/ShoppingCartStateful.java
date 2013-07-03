package org.dieschnittstelle.jee.esa.crm.ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateful;


import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;
import org.jboss.logging.Logger;

/**
 * provides shopping cart functionality
 */
@Stateful
public class ShoppingCartStateful implements ShoppingCartRemote {
	
	protected static Logger logger = Logger.getLogger(ShoppingCartStateful.class);

	private List<CrmProductBundle> productBundles = new ArrayList<CrmProductBundle>();
	
	public ShoppingCartStateful() {
		logger.info("<constructor>: " + this);
	}
	
	public void addProductBundle(CrmProductBundle product) {
		logger.info("addProductBundle(): " + product);

		this.productBundles.add(product);
	}
	
	public List<CrmProductBundle> getProductBundles() {
		logger.info("getProductBundles()");

		return this.productBundles;
	}
	
	@PostConstruct
	public void beginn() {
		logger.info("@PostConstruct");
	}

	@PreDestroy
	public void abschluss() {
		logger.info("@PreDestroy");
	}

	@PrePassivate
	public void passiviere() {
		logger.info("@PrePassivate");
	}

	@PostActivate
	public void aktiviere() {
		logger.info("@PostActivate");
	}

}
