package de.metamob.data.shoppingCart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CrmProductBundleCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;

public class UserTransaction implements Serializable {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 478707976370399493L;

	private Date date;
	
	private final List<ShoppingItem> products;
	private final AbstractTouchpoint touchpoint;

	public UserTransaction(final CustomerTransaction customerTransaction,  List<ShoppingItem> products) {
		this.date = customerTransaction.getDate();
		this.touchpoint = customerTransaction.getTouchpoint();
		this.products = products;
	}

	public Date getDate() {
		return this.date;
	}

	public List<ShoppingItem> getProducts() {
		return this.products;
	}

	public AbstractTouchpoint getTouchpoint() {
		return this.touchpoint;
	}
}
