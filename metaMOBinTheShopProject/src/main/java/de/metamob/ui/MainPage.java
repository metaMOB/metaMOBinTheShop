package de.metamob.ui;

import javax.ejb.EJB;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.AdressCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.TouchpointCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.ShoppingSessionFacadeLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDLocal;

import de.metamob.session.SessionUtil;
import de.metamob.ui.adminPanel.AdminPanel;
import de.metamob.ui.callbacks.IMainPageCallback;
import de.metamob.ui.loginPanel.LoginPanel;
import de.metamob.ui.mainPanel.MainPanel;

public class MainPage extends WebPage implements IMainPageCallback { // IMainPageItemCallback

	private static final long serialVersionUID = 1L;

	private final AjaxLink<Void> adminLink;

	private final AdminPanel adminPanel;

	@EJB(name="AdressCRUD")
	private AdressCRUDLocal adressCRUD;

	@EJB(name="CustomerCRUD")
	private CustomerCRUDLocal customerCRUD;

	private final AjaxLink<Void> itemDisplayLink;

	private final AjaxLink<Void> link;

	String loginLabelText = "Login";

	private final Label loginLinkLabel;
	private final Panel loginPanel;
	private final MainPanel mainPanel;
	private String mode = "main";
	private Label numOfItems;
	@EJB(name="PointOfSaleCRUD")
    private PointOfSaleCRUDLocal pointOfSaleCRUD;
	@EJB(name="ProductCRUD")
	private ProductCRUDLocal productCRUD;
	private final AjaxLink<Void> shoppingCartButton;
	@EJB(name="shoppingSystem")
	private ShoppingSessionFacadeLocal shoppingSessionFacade;
	@EJB(name="StockSystem")
	private StockSystemLocal stockSystem;


	@EJB(name="TouchpointCRUD")
    private TouchpointCRUDLocal touchpointCRUD;
	private final Label userNameLabel;

	String userNameText = "Gast";

	private Panel visiblePanel;

	public MainPage(final PageParameters parameters) {
		this.userNameLabel = new Label("userName", new PropertyModel<String>(this, "userNameText"));
		this.loginLinkLabel = new Label("loginLabel", new PropertyModel<String>(this, "loginLabelText"));

		this.add(this.userNameLabel);


		this.mainPanel = new MainPanel("contentPanel", this);
		this.loginPanel = new LoginPanel("contentPanel", this);
		this.adminPanel = new AdminPanel("contentPanel", this);

		this.loginPanel.setOutputMarkupId(true);
		this.mainPanel.setOutputMarkupId(true);
		this.adminPanel.setOutputMarkupId(true);


		this.setOutputMarkupId(true);

		this.visiblePanel = this.mainPanel;
		this.add(this.visiblePanel);

		this.numOfItems = new Label("numOfItems", SessionUtil.getShoppingCarts().getNumOfUnits());
		this.add(this.numOfItems);

		this.link = new AjaxLink<Void>("loginLink") {
			/**
			 *
			 */
			private static final long	serialVersionUID	= 4453699470346910589L;

			@Override
			public void onClick(final AjaxRequestTarget target) {
				System.out.println("LOGIN/LOGOUT: CLICK");
				if (MainPage.this.loginLabelText.equals("Login")){
				if (MainPage.this.mode.equals("main")) {
					MainPage.this.visiblePanel.replaceWith(MainPage.this.loginPanel);
					MainPage.this.visiblePanel = MainPage.this.loginPanel;
					target.add(MainPage.this.loginPanel);
					MainPage.this.mode = "login";
				} else {
					MainPage.this.visiblePanel.replaceWith(MainPage.this.mainPanel);
					MainPage.this.visiblePanel = MainPage.this.mainPanel;
					MainPage.this.mainPanel.currentDisplay(target, "ITEMDISPLAY", null);
					target.add(MainPage.this.mainPanel);
					MainPage.this.mode = "main";
				}
				}
				else {
					MainPage.this.loginLabelText = "Login";
					MainPage.this.userNameText = "Gast";
					SessionUtil.logout();
					MainPage.this.mainPanel.currentDisplay(target, "ITEMDISPLAY", null);
					this.setResponsePage(this.getPage());
				}
			}
		};





		this.shoppingCartButton = new AjaxLink<Void>("shoppingCartLink") {
			/**
			 *
			 */
			private static final long	serialVersionUID	= 7935179210502761874L;

			@Override
			public void onClick(final AjaxRequestTarget target) {
				System.out.println("SHOPPINGCART: CLICK");
				System.out.println("CHANGE TO MAINPANEL");
				MainPage.this.visiblePanel.replaceWith(MainPage.this.mainPanel);
				MainPage.this.visiblePanel = MainPage.this.mainPanel;
				target.add(MainPage.this.mainPanel);
				MainPage.this.mainPanel.currentDisplay(target, "SHOPPINGCART", null);
				MainPage.this.mode = "main";
				this.setResponsePage(this.getPage());
			}
		};

		this.add(this.shoppingCartButton);

		this.itemDisplayLink= new AjaxLink<Void>("itemDisplayLink") {
			/**
			 *
			 */
			private static final long	serialVersionUID	= 2674592789426609609L;

			@Override
			public void onClick(final AjaxRequestTarget target) {
				System.out.println("ITEMDISPLAY: CLICK");
				MainPage.this.visiblePanel.replaceWith(MainPage.this.mainPanel);
				MainPage.this.visiblePanel = MainPage.this.mainPanel;
				target.add(MainPage.this.mainPanel);
				MainPage.this.mainPanel.currentDisplay(target, "ITEMDISPLAY", null);
				MainPage.this.mode = "main";
				this.setResponsePage(this.getPage());
			}
		};
		this.add(this.itemDisplayLink);

		this.adminLink = new AjaxLink<Void>("admin") {
			/**
			 *
			 */
			private static final long	serialVersionUID	= 5622954561146482378L;

			@Override
			public void onClick(final AjaxRequestTarget target) {
				System.out.println("ADMIN: CLICK");
				if (MainPage.this.mode.equals("main")) {
					MainPage.this.visiblePanel.replaceWith(MainPage.this.adminPanel);
					MainPage.this.visiblePanel = MainPage.this.adminPanel;
					target.add(MainPage.this.adminPanel);
					MainPage.this.mode = "admin";
				} else {
					MainPage.this.visiblePanel.replaceWith(MainPage.this.mainPanel);
					MainPage.this.visiblePanel = MainPage.this.mainPanel;
					//mainPanel.updateData();
					MainPage.this.mainPanel.currentDisplay(target, "ITEMDISPLAY", null);
					target.add(MainPage.this.mainPanel);
					MainPage.this.mode = "main";
				}
			}
		};
		this.add(this.adminLink);

		this.link.add(this.loginLinkLabel);
		this.add(this.link);
	}

	public String getLoginLabelText() {
		return this.loginLabelText;
	}

	public String getUserNameText() {
		return this.userNameText;
	}
	public void setLoginLabelText(final String loginLabelText) {
		this.loginLabelText = loginLabelText;
	}

	public void setUserNameText(final String userNameText) {
		this.userNameText = userNameText;
	}

	@Override
	public void unitsChanged() {
		// TODO Auto-generated method stub
		System.out.println("NUM OF UNITS "+ SessionUtil.getShoppingCarts().getNumOfUnits());

		if (this.numOfItems!=null){
			this.remove(this.numOfItems);
		}
		this.numOfItems = new Label("numOfItems", SessionUtil.getShoppingCarts().getNumOfUnits());
		this.add(this.numOfItems);
		this.setResponsePage(this.getPage());
	}


	@Override
	public void userLoggedIn() {
		this.userNameText = (SessionUtil.isLoggedIn())?SessionUtil.getCurrentUser().getFullName():"Gast";
		this.loginLabelText = "LogOut";

		this.visiblePanel.replaceWith(this.mainPanel);
		this.visiblePanel = this.mainPanel;
		this.add(this.mainPanel);
		this.mode = "main";

		this.setResponsePage(this.getPage());
	}
}