package de.metamob.data.shoppingCart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

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

	@EJB(name="ProductCRUD")
	private ProductCRUDLocal productCRUD;
	private final List<ShoppingItem> products;
	private final AbstractTouchpoint touchpoint;

	public UserTransaction(){
		this.date = new Date();
		this.touchpoint = new StationaryTouchpoint(0, "Test TP", null);
		this.products = new ArrayList<ShoppingItem>();
		this.products.add(new ShoppingItem(new IndividualisedProductItem("Bla", ProductType.BREAD, 0, 100)));
		this.products.add(new ShoppingItem(new IndividualisedProductItem("BlaBlub", ProductType.BREAD, 0, 100)));
		this.date = new Date();
	}

	public UserTransaction(final CustomerTransaction customerTransaction) {
		this.date = customerTransaction.getDate();
		this.touchpoint = customerTransaction.getTouchpoint();
		this.products = new ArrayList<ShoppingItem>();
		final List<CrmProductBundle> productBundles = customerTransaction.getProducts();
		for(final CrmProductBundle productBundle: productBundles){
			final ShoppingItem item = new ShoppingItem(this.productCRUD.readProduct(productBundle.getErpProductId()));
			item.setUnits(productBundle.getUnits());
			this.products.add(item);
		}
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
