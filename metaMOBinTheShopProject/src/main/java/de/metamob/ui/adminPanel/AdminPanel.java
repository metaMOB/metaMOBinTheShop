package de.metamob.ui.adminPanel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;

import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.TouchpointCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.Gender;
import org.dieschnittstelle.jee.esa.crm.entities.MobileTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;

import de.metamob.data.shoppingCart.ShoppingItem;
import de.metamob.data.shoppingCart.UserShoppingCarts;
import de.metamob.session.SessionUtil;
import de.metamob.ui.Item;
import de.metamob.ui.callbacks.IMainPageCallback;
import de.metamob.ui.callbacks.IMainPageItemCallback;
import de.metamob.ui.adminPanel.touchPointPanel.TouchPointPanel;

import org.apache.wicket.markup.html.form.NumberTextField;

import java.text.DecimalFormat;

import javax.ejb.EJB;

import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.*;

public class AdminPanel extends Panel {
	
	@EJB(name="StockSystem")
	private StockSystemLocal stockSystem;
	
	@EJB(name="ProductCRUD")
	private ProductCRUDLocal productCRUD;
	
	@EJB(name="PointOfSaleCRUD")
    private PointOfSaleCRUDLocal pointOfSaleCRUD;
	
	@EJB(name="TouchpointCRUD")
    private TouchpointCRUDLocal touchpointCRUD;
	
	@EJB(name="CustomerCRUD")
	private CustomerCRUDLocal customerCRUD;
	
	private AjaxLink<Void> philsButton;
	private IMainPageCallback itemPanelCallback;
	
	public AdminPanel(String id, final IMainPageCallback itemPanelCallback) {
		super(id);
		this.itemPanelCallback = itemPanelCallback;
		// TODO Auto-generated constructor stub
		
		philsButton = new AjaxLink<Void>("demPhilSeinButton") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				//customer
				
				Customer customer = new Customer("Hans", "Heimlich", Gender.M, "01234579", "hans@wurst.de", "test123");
				customer = customerCRUD.createCustomer(customer);
				customerCRUD.createCustomer(new Customer("Felix", "Helix", Gender.M, "01234579", "felix@helix.de", "test123"));
				customerCRUD.createCustomer(new Customer("Philipp", "UelksMulks", Gender.M, "01234579", "moep@boep.de", "test123"));
				
				SessionUtil.login(customer);
				itemPanelCallback.userLoggedIn();
				
				
				//points of sale
				PointOfSale pos1 = pointOfSaleCRUD.createPointOfSale(new PointOfSale());
				PointOfSale pos2 = pointOfSaleCRUD.createPointOfSale(new PointOfSale());
				PointOfSale pos3 = pointOfSaleCRUD.createPointOfSale(new PointOfSale());
				
				StationaryTouchpoint sttp = new StationaryTouchpoint(pos1.getId());
				sttp.setLocation(new Address("Luxemburger Straße", "10", "DE-13353", "Berlin", 52.550136f,13.39585f));
				sttp.setName("Conys Backstube");
				touchpointCRUD.createTouchpoint(sttp);
				
				sttp = new StationaryTouchpoint(pos2.getId());
				sttp.setLocation(new Address("Pettenkofer Straße", "13", "DE-10353", "Berlin", 52.54499f,13.35232f));
				sttp.setName("Der Kondidor");
				touchpointCRUD.createTouchpoint(sttp);
				
				sttp = new StationaryTouchpoint(pos3.getId());
				sttp.setLocation(new Address("Evergreen Terance", "1", "DE-12345", "Springfield", 52.51499f,13.34232f));
				sttp.setName("Sack Mehl");
				touchpointCRUD.createTouchpoint(sttp);
				
				IndividualisedProductItem product = (IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Schrippe",ProductType.ROLL,0, 30));
				IndividualisedProductItem product2= (IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Bauernbrot",ProductType.BREAD,0, 180));
				
				//StockItems
				stockSystem.addToStock(product, pos1.getId(), 2);
				stockSystem.addToStock(product2, pos1.getId(), 10000);
				stockSystem.addToStock((IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Mischbrot",ProductType.BREAD,0, 165)) , pos1.getId(), 10000);
				stockSystem.addToStock((IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Sandkuchen",ProductType.PASTRY,0, 200)), pos1.getId(), 10000);
				stockSystem.addToStock((IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Zimtstern",ProductType.PASTRY,0 , 35)), pos1.getId(), 10000);
				stockSystem.addToStock((IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Mohngebaeck",ProductType.PASTRY,0, 85)), pos1.getId(), 10000);
				stockSystem.addToStock((IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Kraftmeier",ProductType.ROLL,0, 65)), pos1.getId(), 10000);

				stockSystem.addToStock(product, pos2.getId(), 5);
				stockSystem.addToStock(product2, pos2.getId(), 10000);
				stockSystem.addToStock((IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Haferbrot",ProductType.BREAD,0, 160)), pos2.getId(), 10000);
				stockSystem.addToStock((IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Zuckerbrot",ProductType.BREAD,0, 240)), pos2.getId(), 10000);
				
				stockSystem.addToStock(product, pos3.getId(), 3);
				
				SessionUtil.getUIUserConfiguration().setTouchpont(sttp);
				//setResponsePage(getPage());	
			}
		};
		
		add(philsButton);
	}
	
	private boolean once = false;
	
	private void addTouchpointPanel(){
	
		List<AbstractTouchpoint> allTouchpoints = touchpointCRUD.readAllTouchpoints();
				
		ListView<AbstractTouchpoint> touchpoints = new ListView<AbstractTouchpoint>("touchpoints", allTouchpoints){
			@Override
			protected void populateItem(ListItem<AbstractTouchpoint> entry) {
				
				AbstractTouchpoint oneTouchpoint = entry.getModelObject();
				List<IndividualisedProductItem> itemList = new ArrayList<IndividualisedProductItem>();
				
				for (ProductType type:ProductType.values()){
					itemList.addAll(stockSystem.getProductsOnStock(oneTouchpoint.getErpPointOfSaleId(), type, SortType.ASC));
				}
				
				
				TouchPointPanel touchPointPanel = new TouchPointPanel("oneTouchpoint", itemList, oneTouchpoint);
				System.out.println("GENERATE TOUCHPOINTPANEL BY SHOPPINGCART");
				entry.add(touchPointPanel);
				}			
		};
		if (touchpoints!=null){
        	remove(touchpoints);
        }
		add(touchpoints);
	}
	
	
	@Override
	public void onBeforeRender(){
		super.onBeforeRender();
		addTouchpointPanel();
	}
}
