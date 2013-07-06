package de.metamob.ui;

import javax.ejb.EJB;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;

import org.dieschnittstelle.jee.esa.crm.entities.Gender;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.ejbs.ShoppingSessionFacadeLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDLocal;

import de.metamob.data.shoppingCart.ShoppingItem;
import de.metamob.data.shoppingCart.UserShoppingCart;
import de.metamob.data.shoppingCart.UserShoppingCarts;
import de.metamob.session.SessionUtil;
import de.metamob.ui.callbacks.IMainPageCallback;
import de.metamob.ui.callbacks.IMainPageItemCallback;
import de.metamob.ui.loginPanel.LoginPanel;
import de.metamob.ui.mainPanel.MainPanel;

import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.behavior.Behavior;
import org.dieschnittstelle.jee.esa.crm.ejbs.UserCheckLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.AdressCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.TouchpointCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

public class MainPage extends WebPage implements IMainPageCallback { // IMainPageItemCallback
	
	@EJB(name="TouchpointCRUD")
    private TouchpointCRUDLocal touchpointCRUD;
	
	@EJB(name="PointOfSaleCRUD")
    private PointOfSaleCRUDLocal pointOfSaleCRUD;
	
	@EJB(name="CustomerCRUD")
	private CustomerCRUDLocal customerCRUD;
	
	@EJB(name="ProductCRUD")
	private ProductCRUDLocal productCRUD;
	
	@EJB(name="AdressCRUD")
	private AdressCRUDLocal adressCRUD;
	
	@EJB(name="StockSystem")
	private StockSystemLocal stockSystem;
	
	@EJB(name="shoppingSystem")
	private ShoppingSessionFacadeLocal shoppingSessionFacade;
	
	private static final long serialVersionUID = 1L;
	
	private Panel visiblePanel;
	private Panel loginPanel;
	private MainPanel mainPanel;
	private String mode = "main";
	private AjaxLink<Void> link;	
	private AjaxLink<Void> philsButton;
	private AjaxLink<Void> shoppingCartButton;
	
	//private ShoppingCart shoppingCart = new ShoppingCart(); 
	
	String userNameText = "Gast";
	public String getUserNameText() {
		return userNameText;
	}

	public void setUserNameText(String userNameText) {
		this.userNameText = userNameText;
	}
	
	String loginLabelText = "Login";

	public String getLoginLabelText() {
		return loginLabelText;
	}

	public void setLoginLabelText(String loginLabelText) {
		this.loginLabelText = loginLabelText;
	}

	//PropertyModel<String> userNameModel = new PropertyModel<String>(this, "userNameText");
	
	private Label userNameLabel;
	private Label loginLinkLabel;

	public MainPage(final PageParameters parameters) {
		userNameLabel = new Label("userName", new PropertyModel<String>(this, "userNameText"));
		loginLinkLabel = new Label("loginLabel", new PropertyModel<String>(this, "loginLabelText"));
		
		add(userNameLabel);
		
		
		mainPanel = new MainPanel("contentPanel", this);
		loginPanel = new LoginPanel("contentPanel", this);
		
		loginPanel.setOutputMarkupId(true);
		mainPanel.setOutputMarkupId(true);
		//mainPanel.updateData();
		
		
		this.setOutputMarkupId(true);

		visiblePanel = mainPanel;
		add(visiblePanel);

		
		
		link = new AjaxLink<Void>("loginLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				System.out.println("LOGIN/LOGOUT: CLICK");
				if (loginLabelText.equals("Login")){
				if (mode.equals("main")) {
					visiblePanel.replaceWith(loginPanel);
					visiblePanel = loginPanel;
					target.add(loginPanel);
					mode = "login";
				} else {
					visiblePanel.replaceWith(mainPanel);
					visiblePanel = mainPanel;
					//mainPanel.updateData();
					target.add(mainPanel);
					mode = "main";
				}
				}
				else {
					loginLabelText = "Login";
					userNameText = "Gast"; 
					SessionUtil.logout();
					setResponsePage(getPage());	
				}
			}
		};
		
		
		
		philsButton = new AjaxLink<Void>("demPhilSeinButton") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				//customer
				
				Customer customer = new Customer("Hans", "Heimlich", Gender.M, "01234579", "hans@wurst.de", "test123");
				customerCRUD.createCustomer(customer);
				customerCRUD.createCustomer(new Customer("Felix", "Helix", Gender.M, "01234579", "felix@helix.de", "test123"));
				customerCRUD.createCustomer(new Customer("Philipp", "UelksMulks", Gender.M, "01234579", "moep@boep.de", "test123"));
				
				//points of sale
				PointOfSale pos1 = pointOfSaleCRUD.createPointOfSale(new PointOfSale());
				PointOfSale pos2 = pointOfSaleCRUD.createPointOfSale(new PointOfSale());
				
				StationaryTouchpoint sttp = new StationaryTouchpoint(pos1.getId());
				sttp.setLocation(new Address("Luxemburger Stra�e", "10", "DE-13353", "Berlin", 50.920602f,6.932407f));
				sttp.setName("Conys Backstube");
				touchpointCRUD.createTouchpoint(sttp);
				
				sttp = new StationaryTouchpoint(pos2.getId());
				sttp.setLocation(new Address("Pettenkofer Stra�e", "13", "DE-10353", "Berlin", 51.920602f,5.932407f));
				sttp.setName("Der Kondidor");
				touchpointCRUD.createTouchpoint(sttp);
				
				IndividualisedProductItem product = (IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Schrippe",ProductType.ROLL,0, 30));
				IndividualisedProductItem product2= (IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Bauernbrot",ProductType.BREAD,0, 180));
				
				//StockItems
				stockSystem.addToStock(product, pos1.getId(), 10000);
				stockSystem.addToStock(product2, pos1.getId(), 10000);
				stockSystem.addToStock((IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Mischbrot",ProductType.BREAD,0, 165)) , pos1.getId(), 10000);
				stockSystem.addToStock((IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Sandkuchen",ProductType.PASTRY,0, 200)), pos1.getId(), 10000);
				stockSystem.addToStock((IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Zimtstern",ProductType.PASTRY,0 , 35)), pos1.getId(), 10000);
				stockSystem.addToStock((IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Mohngebaeck",ProductType.PASTRY,0, 85)), pos1.getId(), 10000);
				stockSystem.addToStock((IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Kraftmeier",ProductType.ROLL,0, 65)), pos1.getId(), 10000);

				stockSystem.addToStock(product, pos2.getId(), 10000);
				stockSystem.addToStock(product2, pos2.getId(), 10000);
				stockSystem.addToStock((IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Haferbrot",ProductType.BREAD,0, 160)), pos2.getId(), 10000);
				stockSystem.addToStock((IndividualisedProductItem) productCRUD.createProduct(new IndividualisedProductItem("Zuckerbrot",ProductType.BREAD,0, 240)), pos2.getId(), 10000);
				
				//Erste shopping Action
				shoppingSessionFacade.setCustomer(customer);
				shoppingSessionFacade.setTouchpoint(sttp);
				shoppingSessionFacade.addProduct(product, 10);
				shoppingSessionFacade.addProduct(product2, 3);
				
				try {
					shoppingSessionFacade.purchase();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			/*	//Zweite shopping Action
				shoppingSessionFacade.setCustomer(customer);
				shoppingSessionFacade.setTouchpoint(sttp);
				shoppingSessionFacade.addProduct(product, 10);
				shoppingSessionFacade.addProduct(product2, 3);
				
				try {
					shoppingSessionFacade.purchase();
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				
				SessionUtil.getUIUserConfiguration().setTouchpont(sttp);
				setResponsePage(getPage());	
			}
		};
		
		add(philsButton);
		
		shoppingCartButton = new AjaxLink<Void>("shoppingCartLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				System.out.println("SHOPPINGCART: CLICK");
				
				((MainPanel) mainPanel).currentDisplay(target);
				if (!mode.equals("main")){
					System.out.println("CHANGE TO MAINPANEL");
					visiblePanel.replaceWith(mainPanel);
					visiblePanel = mainPanel;
					target.add(mainPanel);
					mode = "main";
				}
			}
		};		
		
		add(shoppingCartButton);
		
		link.add(loginLinkLabel);
		add(link);
	}

	@Override
	public void userLoggedIn() {
		userNameText = (SessionUtil.isLoggedIn())?SessionUtil.getCurrentUser().getFullName():"Gast"; 
		loginLabelText = "LogOut";
		
		visiblePanel.replaceWith(mainPanel);
		visiblePanel = mainPanel;
		add(mainPanel);
		mode = "main";
		
		setResponsePage(getPage());		
	}	
}