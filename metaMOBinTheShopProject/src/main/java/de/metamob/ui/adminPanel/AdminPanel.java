package de.metamob.ui.adminPanel;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.TouchpointCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.Gender;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;
import org.dieschnittstelle.jee.esa.erp.entities.SortType;

import de.metamob.session.SessionUtil;
import de.metamob.ui.adminPanel.touchPointPanel.TouchPointPanel;
import de.metamob.ui.callbacks.IMainPageCallback;

public class AdminPanel extends Panel {

	/**
	 *
	 */
	private static final long	serialVersionUID	= -5584922750973973973L;

	@EJB(name="CustomerCRUD")
	private CustomerCRUDLocal customerCRUD;

	private final AjaxLink<Void> philsButton;

	@EJB(name="PointOfSaleCRUD")
    private PointOfSaleCRUDLocal pointOfSaleCRUD;

	@EJB(name="ProductCRUD")
	private ProductCRUDLocal productCRUD;

	@EJB(name="StockSystem")
	private StockSystemLocal stockSystem;

	@EJB(name="TouchpointCRUD")
    private TouchpointCRUDLocal touchpointCRUD;
	public AdminPanel(final String id, final IMainPageCallback itemPanelCallback) {
		super(id);
		this.philsButton = new AjaxLink<Void>("demPhilSeinButton") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				//customer

				Customer customer = new Customer("Hans", "Heimlich", Gender.M, "01234579", "hans@wurst.de", "test123");
				customer = AdminPanel.this.customerCRUD.createCustomer(customer);
				AdminPanel.this.customerCRUD.createCustomer(new Customer("Felix", "Helix", Gender.M, "01234579", "felix@helix.de", "test123"));
				AdminPanel.this.customerCRUD.createCustomer(new Customer("Philipp", "UelksMulks", Gender.M, "01234579", "moep@boep.de", "test123"));

				SessionUtil.login(customer);
				itemPanelCallback.userLoggedIn();


				//points of sale
				final PointOfSale pos1 = AdminPanel.this.pointOfSaleCRUD.createPointOfSale(new PointOfSale());
				final PointOfSale pos2 = AdminPanel.this.pointOfSaleCRUD.createPointOfSale(new PointOfSale());
				final PointOfSale pos3 = AdminPanel.this.pointOfSaleCRUD.createPointOfSale(new PointOfSale());

				StationaryTouchpoint sttp = new StationaryTouchpoint(pos1.getId());
				sttp.setLocation(new Address("Luxemburger Straße", "10", "DE-13353", "Berlin", 52.550136f,13.39585f));
				sttp.setName("Conys Backstube");
				AdminPanel.this.touchpointCRUD.createTouchpoint(sttp);

				sttp = new StationaryTouchpoint(pos2.getId());
				sttp.setLocation(new Address("Pettenkofer Straße", "13", "DE-10353", "Berlin", 52.54499f,13.35232f));
				sttp.setName("Der Kondidor");
				AdminPanel.this.touchpointCRUD.createTouchpoint(sttp);

				sttp = new StationaryTouchpoint(pos3.getId());
				sttp.setLocation(new Address("Evergreen Terance", "1", "DE-12345", "Springfield", 52.51499f,13.34232f));
				sttp.setName("Sack Mehl");
				AdminPanel.this.touchpointCRUD.createTouchpoint(sttp);

				final IndividualisedProductItem product = (IndividualisedProductItem) AdminPanel.this.productCRUD.createProduct(new IndividualisedProductItem("Schrippe",ProductType.ROLL,0, 30));
				final IndividualisedProductItem product2= (IndividualisedProductItem) AdminPanel.this.productCRUD.createProduct(new IndividualisedProductItem("Bauernbrot",ProductType.BREAD,0, 180));

				//StockItems
				AdminPanel.this.stockSystem.addToStock(product, pos1.getId(), 2);
				AdminPanel.this.stockSystem.addToStock(product2, pos1.getId(), 10000);
				AdminPanel.this.stockSystem.addToStock((IndividualisedProductItem) AdminPanel.this.productCRUD.createProduct(new IndividualisedProductItem("Mischbrot",ProductType.BREAD,0, 165)) , pos1.getId(), 10000);
				AdminPanel.this.stockSystem.addToStock((IndividualisedProductItem) AdminPanel.this.productCRUD.createProduct(new IndividualisedProductItem("Sandkuchen",ProductType.PASTRY,0, 200)), pos1.getId(), 10000);
				AdminPanel.this.stockSystem.addToStock((IndividualisedProductItem) AdminPanel.this.productCRUD.createProduct(new IndividualisedProductItem("Zimtstern",ProductType.PASTRY,0 , 35)), pos1.getId(), 10000);
				AdminPanel.this.stockSystem.addToStock((IndividualisedProductItem) AdminPanel.this.productCRUD.createProduct(new IndividualisedProductItem("Mohngebaeck",ProductType.PASTRY,0, 85)), pos1.getId(), 10000);
				AdminPanel.this.stockSystem.addToStock((IndividualisedProductItem) AdminPanel.this.productCRUD.createProduct(new IndividualisedProductItem("Kraftmeier",ProductType.ROLL,0, 65)), pos1.getId(), 10000);

				AdminPanel.this.stockSystem.addToStock(product, pos2.getId(), 5);
				AdminPanel.this.stockSystem.addToStock(product2, pos2.getId(), 10000);
				AdminPanel.this.stockSystem.addToStock((IndividualisedProductItem) AdminPanel.this.productCRUD.createProduct(new IndividualisedProductItem("Haferbrot",ProductType.BREAD,0, 160)), pos2.getId(), 10000);
				AdminPanel.this.stockSystem.addToStock((IndividualisedProductItem) AdminPanel.this.productCRUD.createProduct(new IndividualisedProductItem("Zuckerbrot",ProductType.BREAD,0, 240)), pos2.getId(), 10000);

				AdminPanel.this.stockSystem.addToStock(product, pos3.getId(), 3);

				SessionUtil.getUIUserConfiguration().setTouchpont(sttp);
			}
		};

		this.add(this.philsButton);
	}

	private void addTouchpointPanel(){

		final List<AbstractTouchpoint> allTouchpoints = this.touchpointCRUD.readAllTouchpoints();

		final ListView<AbstractTouchpoint> touchpoints = new ListView<AbstractTouchpoint>("touchpoints", allTouchpoints){
			@Override
			protected void populateItem(final ListItem<AbstractTouchpoint> entry) {

				final AbstractTouchpoint oneTouchpoint = entry.getModelObject();
				final List<IndividualisedProductItem> itemList = new ArrayList<IndividualisedProductItem>();

				for (final ProductType type:ProductType.values()){
					itemList.addAll(AdminPanel.this.stockSystem.getProductsOnStock(oneTouchpoint.getErpPointOfSaleId(), type, SortType.ASC));
				}


				final TouchPointPanel touchPointPanel = new TouchPointPanel("oneTouchpoint", itemList, oneTouchpoint);
				System.out.println("GENERATE TOUCHPOINTPANEL BY SHOPPINGCART");
				entry.add(touchPointPanel);
				}
		};
		if (touchpoints!=null){
        	this.remove(touchpoints);
        }
		this.add(touchpoints);
	}


	@Override
	public void onBeforeRender(){
		super.onBeforeRender();
		this.addTouchpointPanel();
	}
}
