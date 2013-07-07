package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateful;

import org.dieschnittstelle.jee.esa.crm.ejbs.CampaignTrackingLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.CustomerTrackingLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.ShoppingCartLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.ShoppingCartStateful;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.Campaign;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.exceptions.ProductNotInStockException;
import org.dieschnittstelle.jee.esa.erp.exceptions.ProductUnitCountToLowInStockException;
import org.jboss.logging.Logger;

@Stateful
public class ShoppingSessionFacade implements ShoppingSessionFacadeLocal, ShoppingSessionFacadeRemote {

	public class ProductCount{
		private final int count;
		private final AbstractProduct product;

		public ProductCount(final int count, final AbstractProduct product){
			this.count = count;
			this.product = product;
		}

		public int getCount() {
			return this.count;
		}

		public AbstractProduct getProduct() {
			return this.product;
		}
	}

	protected static Logger logger = Logger.getLogger(ShoppingCartStateful.class);
	//private CustomerTrackingRemote customerTracking;
	@EJB(beanName="campaignTrackingSystem")
	private CampaignTrackingLocal campaignTracking;
	private Customer customer;
	//private ShoppingCartRemote shoppingCart;
	@EJB(beanName="customerTrackingSystem")
	private CustomerTrackingLocal customerTracking;
	@EJB(beanName="ProductCRUD")
	private ProductCRUDLocal productCRUD;

	@EJB(beanName="shoppingCart")
	private ShoppingCartLocal shoppingCart;
	//private CampaignTrackingRemote campaignTracking;
	@EJB(beanName="StockSystem")
	private StockSystemLocal stockSystem;
    private AbstractTouchpoint touchpoint;
	/**
     * Default constructor.
     */
    public ShoppingSessionFacade() {
    	logger.info("<constructor>");
    }

	@PreDestroy
	public void abschluss() {

		logger.info("@PreDestroy");
	}

	@Override
	public void addProduct(final AbstractProduct product, final int units) {
		logger.info(" --- add product ("+product.getId()+") to shoppingCard");
		this.shoppingCart.addProductBundle(new CrmProductBundle(product.getId(), units, product instanceof Campaign));
	}

	private ProductCount checkStock(final CrmProductBundle productBundle, final List<IndividualisedProductItem> stockProducts) throws ProductNotInStockException{
		final int id = productBundle.getErpProductId();
		final int count = productBundle.getUnits();
		IndividualisedProductItem currentProduct = null;
		for (final IndividualisedProductItem ipi : stockProducts){
			if(ipi.getId()==id){
				currentProduct = ipi;
			}
		}
		if(currentProduct==null){
			// product not in stock:
			throw new ProductNotInStockException(this.productCRUD.readProduct(id),productBundle.getUnits());
			//throw new RuntimeException("purchase() failed because product (ID: "+id+") not in stock");
		}else if(this.stockSystem.getUnitsOnStock(currentProduct, this.touchpoint.getErpPointOfSaleId())<count){
			// prouct unit count to low:
			throw new ProductUnitCountToLowInStockException(this.productCRUD.readProduct(id),productBundle.getUnits());
		}else{
			return new ProductCount(count, currentProduct);
		}
	}

	@Override
	public void purchase() throws ProductNotInStockException{
		logger.info("commit()");
		if ((this.customer == null) || (this.touchpoint == null)) {
			throw new RuntimeException(
					"cannot commit shopping session! Either customer or touchpoint has not been set: "
							+ this.customer + "/" + this.touchpoint);
		}

		// verify the campaigns
		this.verifyCampaigns();

		// read out the products from the cart
		final List<CrmProductBundle> products = this.shoppingCart.getProductBundles();

		/*Überprüfe auf stockSystem, ob alle Produkte im
		ShoppingCart für den ausgewählen Verkaufsort noch vorr ¨ atig
		sind.*/
		final List<IndividualisedProductItem> stockProducts = this.stockSystem.getProductsOnStock(this.touchpoint.getErpPointOfSaleId(),null,null);

		//logger.info("items: " +stockProducts.size() + " on stock from "+touchpoint.getErpPointOfSaleId());

		final List<ProductCount> removeProducts = new ArrayList<ProductCount>();

		for (final CrmProductBundle productBundle : products) {
			removeProducts.add(this.checkStock(productBundle,stockProducts));
		}

		// iterate over the products and purchase the campaigns
		for (final CrmProductBundle productBundle : products) {
			if (productBundle.isCampaign()) {
				this.campaignTracking.purchaseCampaignAtTouchpoint(
						productBundle.getErpProductId(), this.touchpoint,
						productBundle.getUnits());
			}
		}

		/*Führe für alle Produkte removeFromStock() auf stockSystem aus. */
		for (final ProductCount productCount : removeProducts) {
			this.stockSystem.removeFromStock((IndividualisedProductItem) productCount.getProduct(), this.touchpoint.getErpPointOfSaleId(), productCount.getCount());
		}

		// then we add a new customer transaction for the current purchase
		final CustomerTransaction transaction = new CustomerTransaction(this.customer, this.touchpoint, products);
		transaction.setCompleted(true);
		this.customerTracking.createTransaction(transaction);
		products.clear();
		logger.info("commit(): done.");
	}

	@Override
	public void reset(){
		this.customer = null;
		this.touchpoint = null;
		this.shoppingCart.clear();
	}

	@Override
	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@Override
	public void setTouchpoint(final AbstractTouchpoint touchpoint) {
		this.touchpoint = touchpoint;
	}

	@Override
	public void verifyCampaigns() {
		if ((this.customer == null) || (this.touchpoint == null)) {
			throw new RuntimeException("cannot verify campaigns! No touchpoint has been set!");
		}

		for (final CrmProductBundle productBundle : this.shoppingCart
				.getProductBundles()) {
			if (productBundle.isCampaign()) {
				// we check whether we have sufficient campaign items available
				if (this.campaignTracking.existsValidCampaignExecutionAtTouchpoint(
								productBundle.getErpProductId(),this.touchpoint) < productBundle.getUnits()) {
					throw new RuntimeException(
					"verifyCampaigns() failed for productBundle " + productBundle + " at touchpoint " + this.touchpoint + "!");
				}
			}
		}
	}
}
