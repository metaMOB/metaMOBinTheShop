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
import de.metamob.ui.adminPanel.AdminPanel;
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
	private AdminPanel adminPanel;
	private String mode = "main";
	private AjaxLink<Void> link;	
	private AjaxLink<Void> shoppingCartButton;
	private AjaxLink<Void> itemDisplayLink;
	private AjaxLink<Void> adminLink;
	private Label numOfItems;
	
	
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
		adminPanel = new AdminPanel("contentPanel", this);
		
		loginPanel.setOutputMarkupId(true);
		mainPanel.setOutputMarkupId(true);
		adminPanel.setOutputMarkupId(true);
		//mainPanel.updateData();
		
		
		this.setOutputMarkupId(true);

		visiblePanel = mainPanel;
		add(visiblePanel);

		numOfItems = new Label("numOfItems", SessionUtil.getShoppingCarts().getNumOfUnits());
		add(numOfItems);
		
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
					mainPanel.currentDisplay(target, "ITEMDISPLAY", null);
					target.add(mainPanel);
					mode = "main";
				}
				}
				else {
					loginLabelText = "Login";
					userNameText = "Gast"; 
					SessionUtil.logout();
					mainPanel.currentDisplay(target, "ITEMDISPLAY", null);
					setResponsePage(getPage());	
				}
			}
		};
		
		
		
		
		
		shoppingCartButton = new AjaxLink<Void>("shoppingCartLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				System.out.println("SHOPPINGCART: CLICK");
				
//				((MainPanel) mainPanel).currentDisplay(target, "SHOPPINGCART", null);
//				if (!mode.equals("main")){
					System.out.println("CHANGE TO MAINPANEL");
					visiblePanel.replaceWith(mainPanel);
					visiblePanel = mainPanel;
					target.add(mainPanel);
					mainPanel.currentDisplay(target, "SHOPPINGCART", null);
					mode = "main";
					setResponsePage(getPage());	
				//}
			}
		};

		add(shoppingCartButton);
		
		itemDisplayLink= new AjaxLink<Void>("itemDisplayLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				System.out.println("ITEMDISPLAY: CLICK");
				visiblePanel.replaceWith(mainPanel);
				visiblePanel = mainPanel;
				target.add(mainPanel);
				mainPanel.currentDisplay(target, "ITEMDISPLAY", null);
				mode = "main";
				setResponsePage(getPage());	
			}
		};
		add(itemDisplayLink);
		
		adminLink = new AjaxLink<Void>("admin") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				System.out.println("ADMIN: CLICK");
				if (mode.equals("main")) {
					visiblePanel.replaceWith(adminPanel);
					visiblePanel = adminPanel;
					target.add(adminPanel);
					mode = "admin";
				} else {
					visiblePanel.replaceWith(mainPanel);
					visiblePanel = mainPanel;
					//mainPanel.updateData();
					mainPanel.currentDisplay(target, "ITEMDISPLAY", null);
					target.add(mainPanel);
					mode = "main";
				}	
			}
		};
		add(adminLink);
		
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
	

	@Override
	public void unitsChanged() {
		// TODO Auto-generated method stub
		System.out.println("NUM OF UNITS "+ SessionUtil.getShoppingCarts().getNumOfUnits());
		
		if (numOfItems!=null){
			remove(numOfItems);
		}
		numOfItems = new Label("numOfItems", SessionUtil.getShoppingCarts().getNumOfUnits());
		add(numOfItems);
		setResponsePage(getPage());	
	}
}