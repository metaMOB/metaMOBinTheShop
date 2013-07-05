package de.metamob.data.shoppingCart;

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

public class UserTransaction {
	
	@EJB(name="ProductCRUD")
	private ProductCRUDLocal productCRUD;
	
	private Date date;
	private AbstractTouchpoint touchpoint;
	private List<ShoppingItem> products;
	
	public UserTransaction(){
		this.date = new Date();
		this.touchpoint = new StationaryTouchpoint(0, "Test TP", null);
		this.products.add(new ShoppingItem(new IndividualisedProductItem("Bla", ProductType.BREAD, 0, 100)));
		this.products.add(new ShoppingItem(new IndividualisedProductItem("BlaBlub", ProductType.BREAD, 0, 100)));
	}
	
	public UserTransaction(CustomerTransaction customerTransaction) {
		this.date = customerTransaction.getDate();
		this.touchpoint = customerTransaction.getTouchpoint();
		
		products = new ArrayList<ShoppingItem>();
		List<CrmProductBundle> productBundles = customerTransaction.getProducts();
		for(CrmProductBundle productBundle: productBundles){
			ShoppingItem item = new ShoppingItem(productCRUD.readProduct(productBundle.getErpProductId()));
			item.setUnits(productBundle.getUnits());
			products.add(item);
		}
		
	}
	
	public Date getDate() {
		return date;
	}
	
	public AbstractTouchpoint getTouchpoint() {
		return touchpoint;
	}
	
	public List<ShoppingItem> getProducts() {
		return products;
	}
}
