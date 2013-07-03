package de.metamob.ui;

import java.awt.Point;
import java.util.ArrayList;

import java.util.List;

import javax.ejb.EJB;
import javax.management.relation.Role;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
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


import de.metamob.data.shoppingCart.ShoppingCart;
import de.metamob.ui.callbacks.IMainPageCallback;
import de.metamob.ui.callbacks.IMainPageItemCallback;
import de.metamob.ui.loginPanel.LoginPanel;
import de.metamob.ui.mainPanel.MainPanel;
import de.metamob.usermanagement.UserManager;

import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.TouchpointCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

public class MainPage extends WebPage implements IMainPageCallback { // IMainPageItemCallback
	
	@EJB(name="TouchpointCRUD")
    private TouchpointCRUDLocal touchpointCRUDRemote;
	@EJB(name="PointOfSaleCRUD")
    private PointOfSaleCRUDLocal pointOfSaleCRUDRemote;

	private static final long serialVersionUID = 1L;
	private UserManager userManager = new UserManager();
	private Panel visiblePanel;
	private Panel loginPanel;
	private Panel mainPanel;
	private String mode = "main";
	private AjaxLink<Void> link;	
	private AjaxLink<Void> philsButton;
	private AjaxLink<Void> shoppingCartButton;
	
	private ShoppingCart shoppingCart = new ShoppingCart(); 
	
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
	
	private Label userNameLabel = new Label("userName", new PropertyModel<String>(this, "userNameText"));
	private Label loginLinkLabel = new Label("loginLabel", new PropertyModel<String>(this, "loginLabelText"));

	public MainPage(final PageParameters parameters) {
		
		add(userNameLabel);
		
		
		mainPanel = new MainPanel("contentPanel", this);
		loginPanel = new LoginPanel("contentPanel", this);
		
		loginPanel.setOutputMarkupId(true);
		mainPanel.setOutputMarkupId(true);
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
					target.add(mainPanel);
					mode = "main";
				}
				}
				else {
					loginLabelText = "Login";
					userNameText = "Gast"; 
					userManager.logOut();
					setResponsePage(getPage());	
				}
			}
		};
		
		
		
		philsButton = new AjaxLink<Void>("demPhilSeinButton") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				PointOfSale pos1 = pointOfSaleCRUDRemote.createPointOfSale(new PointOfSale());
				PointOfSale pos2 = pointOfSaleCRUDRemote.createPointOfSale(new PointOfSale());
				
				StationaryTouchpoint sttp = new StationaryTouchpoint(pos1.getId());
				sttp.setName("Test Touchpoint1");
				touchpointCRUDRemote.createTouchpoint(sttp);
				
				sttp = new StationaryTouchpoint(pos2.getId());
				sttp.setName("Test Touchpoint2");
				touchpointCRUDRemote.createTouchpoint(sttp);
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
	public void itemPanelClicked(StockItem stockItem) {
		shoppingCart.addToCart(stockItem);
		System.out.println("SHOPPING CART "+shoppingCart);
	}

	@Override
	public void userLoggedIn() {
		// TODO Auto-generated method stub
		
		userNameText = userManager.getName(); 
		loginLabelText = "LogOut";
		//userNameLabel.render();
		
		//add(userNameLabel);
		
		visiblePanel.replaceWith(mainPanel);
		visiblePanel = mainPanel;
		add(mainPanel);
		mode = "main";
		
		setResponsePage(getPage());		
	}	
}