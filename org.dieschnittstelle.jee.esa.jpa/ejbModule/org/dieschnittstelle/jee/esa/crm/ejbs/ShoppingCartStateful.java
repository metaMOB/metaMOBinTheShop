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
public class ShoppingCartStateful implements ShoppingCartRemote, ShoppingCartLocal {

	protected static Logger logger = Logger.getLogger(ShoppingCartStateful.class);

	private final List<CrmProductBundle> productBundles = new ArrayList<CrmProductBundle>();

	public ShoppingCartStateful() {
		logger.info("<constructor>: " + this);
	}

	@PreDestroy
	public void abschluss() {
		logger.info("@PreDestroy");
	}

	@Override
	public void addProductBundle(final CrmProductBundle product) {
		logger.info("addProductBundle(): " + product);

		this.productBundles.add(product);
	}

	@PostActivate
	public void aktiviere() {
		logger.info("@PostActivate");
	}

	@PostConstruct
	public void beginn() {
		logger.info("@PostConstruct");
	}

	@Override
	public void clear() {
		this.productBundles.clear();
	}

	@Override
	public List<CrmProductBundle> getProductBundles() {
		logger.info("getProductBundles()");

		return this.productBundles;
	}

	@PrePassivate
	public void passiviere() {
		logger.info("@PrePassivate");
	}



}
