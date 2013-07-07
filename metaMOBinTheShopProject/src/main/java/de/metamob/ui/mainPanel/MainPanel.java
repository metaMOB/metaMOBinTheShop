package de.metamob.ui.mainPanel;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CrmProductBundleCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerTransactionCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.TouchpointCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;

import de.metamob.data.shoppingCart.ShoppingItem;
import de.metamob.data.shoppingCart.UserTransaction;
import de.metamob.session.SessionUtil;
import de.metamob.session.UIUserConfiguration;
import de.metamob.ui.callbacks.IMainPageCallback;
import de.metamob.ui.callbacks.IMainPageItemCallback;
import de.metamob.ui.itemPanel.ItemPanel;
import de.metamob.ui.shoppingCartPanel.ShoppingCartPanel;
import de.metamob.ui.transactions.TransactionPanel;

public class MainPanel extends Panel implements IMainPageItemCallback {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 2109163316925684079L;
	@EJB(name="CustomerTransactionCRUD")
    private CustomerTransactionCRUDLocal customerTransactionCRUDRemote;
	
	@EJB(name="ProductCRUD")
	private ProductCRUDLocal productCRUD;
	
	@EJB(name="TouchpointCRUD")
    private TouchpointCRUDLocal touchpointCRUDRemote;
	
	private IMainPageCallback iMainPageCallback;
	private Panel itemPanel = new ItemPanel("itemPanel", this);
	private AjaxLink<Void> linkAllCategories;
	ListView<CustomerTransaction> orders = null;
	private MainPanel self;

	private Panel shoppingCartPanel;

	

	ListView<AbstractTouchpoint> touchpoints = null;

	private Panel transactionPanel;

	private Panel visiblePanel = this.itemPanel;

	public MainPanel(final String id, final IMainPageCallback mainPageCallback) {
		super(id);
		this.self = this;
		this.iMainPageCallback = mainPageCallback;
		// TODO Auto-generated constructor stub

	        this.addCategoryModule();

	        this.add (this.itemPanel);
	}

    public MainPanel(final String id, final IModel<?> model) {
		super(id, model);
		// TODO Auto-generated constructor stub
	}

    private void addAllSelectors(){
    	this.linkAllCategories = new AjaxLink<Void>("categoryAllLink"){
    		@Override
    		public void onClick(final AjaxRequestTarget target) {
    			System.out.println("NO CATEGORY");
    			MainPanel.this.currentDisplay(target, "ITEMDISPLAY", null);
    			final UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
				uiuc.setProductType(null);
				SessionUtil.setUIUserConfiguration(uiuc);

				this.setResponsePage(this.getPage());
    		}
    	};
    	if (SessionUtil.getUIUserConfiguration().getProductType()==null){
    		this.linkAllCategories.add(new AttributeAppender("class", new Model<String>("aktiv")));
    	}
    	if (this.linkAllCategories != null){
    		this.remove(this.linkAllCategories);
    	}
    	this.add(this.linkAllCategories);
    }



    private void addCategoryModule() {
    	final List<String> myList = this.generateProductTypes();

        final ListView<String> categories = new ListView<String>("categories", myList){

			@Override
			protected void populateItem(final ListItem<String> entry) {
				// TODO Auto-generated method stub
				final AjaxLink<Void> link = new AjaxLink<Void>("categoryLink"){
					@Override
					public void onClick(final AjaxRequestTarget target) {
						MainPanel.this.currentDisplay(target, "ITEMDISPLAY", null);
						final UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
						uiuc.setProductType(ProductType.fromReadableString(entry.getModel().getObject()));
						SessionUtil.setUIUserConfiguration(uiuc);

						SessionUtil.setCurrentPage(0);
						this.setResponsePage(this.getPage());
		            }
				};
				if (SessionUtil.getUIUserConfiguration().getProductType()==ProductType.fromReadableString(entry.getModel().getObject())){
					link.add(new AttributeAppender("class", new Model<String>("aktiv")));
		    	}
				link.add(new Label("categoryName", entry.getModel().getObject()));
				entry.add(link);
			}

        };

        this.add(categories);
    }

    private void addLastOrders(){
		List<CustomerTransaction> myList = new ArrayList<CustomerTransaction>();
		if (SessionUtil.isLoggedIn()){
			myList = (List<CustomerTransaction>) this.customerTransactionCRUDRemote.readAllTransactionsForCustomer(SessionUtil.getCurrentUser());
		}
			if (myList.size()>5){
				myList = myList.subList(myList.size()-6, myList.size()-1);
			}
	        this.orders = new ListView<CustomerTransaction>("lastOrders", myList){

	        	@Override
				protected void populateItem(final ListItem<CustomerTransaction> entry) {
	        		
	        		CustomerTransaction ct = entry.getModelObject();
	        		
	        		final List<ShoppingItem> shoppingItems = new ArrayList<ShoppingItem>();
	        		final List<CrmProductBundle> productBundles = customerTransactionCRUDRemote.readCrmProductBundle(ct); //customerTransaction.getProducts();
	        		for(final CrmProductBundle productBundle: productBundles){
	        			final ShoppingItem item = new ShoppingItem(productCRUD.readProduct(productBundle.getErpProductId()));
	        			item.setUnits(productBundle.getUnits());
	        			shoppingItems.add(item);
	        		}
	        		
	        		final UserTransaction tempOrder = new UserTransaction(ct, shoppingItems);
	        		// TODO Auto-generated method stub
					final AjaxLink<Void> link = new AjaxLink<Void>("lastOrderLink"){
						@Override
						public void onClick(final AjaxRequestTarget target) {
							MainPanel.this.currentDisplay(target, "TRANSACTIONDISPLAY", tempOrder);
							this.setResponsePage(this.getPage());
			            }
					};

					link.add(new Label("lastOrderName", "ORDER: "+tempOrder.getDate()));
					entry.add(link);
				}
	        };

	        if (this.orders!=null){
	        	this.remove(this.orders);
	        }

	        this.add(this.orders);
	    }

    private void addTouchpointModule() {

    	final List<AbstractTouchpoint> myList = this.touchpointCRUDRemote.readAllTouchpoints();

        this.touchpoints = new ListView<AbstractTouchpoint>("touchpoints", myList){

        	@Override
			protected void populateItem(final ListItem<AbstractTouchpoint> entry) {
				// TODO Auto-generated method stub
				final AjaxLink<Void> link = new AjaxLink<Void>("touchpointLink"){
					@Override
					public void onClick(final AjaxRequestTarget target) {
						final AbstractTouchpoint temp = entry.getModel().getObject();
						System.out.println("TOUCHPOINT: "+ temp.getName()+ " "+temp.getId());
						MainPanel.this.currentDisplay(target, "ITEMDISPLAY", null);
						final UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
						uiuc.setTouchpont(temp);
						SessionUtil.setUIUserConfiguration(uiuc);

						SessionUtil.setCurrentPage(0);
						this.setResponsePage(this.getPage());
		            }
				};

				link.add(new Label("touchpointName", entry.getModel().getObject().getName()));
				
				
				if (SessionUtil.getUIUserConfiguration().getTouchpont().getId()==entry.getModel().getObject().getId()){
					link.add(new AttributeAppender("class", new Model<String>("aktiv")));
		    	}
				entry.add(link);
			}
        };

        if (this.touchpoints!=null){
        	this.remove(this.touchpoints);
        }

        this.add(this.touchpoints);
    }

	public void currentDisplay(final AjaxRequestTarget target, final String view, final Object object){
		if (view.equals("ITEMDISPLAY")){
			System.out.println("CHANGE TO ITEMPANEL");
			this.itemPanel = new ItemPanel("itemPanel", this);
			this.visiblePanel.replaceWith(this.itemPanel);
			this.visiblePanel = this.itemPanel;

			this.add(this.visiblePanel);
			this.setResponsePage(this.getPage());
		}
		else if (view.equals("SHOPPINGCART")){
			System.out.println("CHANGE TO SHOPPINGCART");
			this.shoppingCartPanel = new ShoppingCartPanel("itemPanel", this);
			this.visiblePanel.replaceWith(this.shoppingCartPanel);
			this.visiblePanel = this.shoppingCartPanel;

			this.add(this.visiblePanel);
			this.setResponsePage(this.getPage());
		}
		else if (view.equals("TRANSACTIONDISPLAY")){
			System.out.println("CHANGE TO TRANSACTIONDISPLAY");
			this.transactionPanel = new TransactionPanel("itemPanel", this, (UserTransaction) object);
			this.visiblePanel.replaceWith(this.transactionPanel);
			this.visiblePanel = this.transactionPanel;
			this.add(this.visiblePanel);
			this.setResponsePage(this.getPage());
		}
	}

	private List<String> generateProductTypes(){
    	final List<String> myList = new ArrayList<String>();
    	myList.add(ProductType.toReadableString(ProductType.BREAD));
    	myList.add(ProductType.toReadableString(ProductType.PASTRY));
    	myList.add(ProductType.toReadableString(ProductType.ROLL));
    	return myList;
    }

	@Override
	public void itemPanelClicked() {
		this.iMainPageCallback.unitsChanged();
	}

	@Override
	public void onBeforeRender(){
		super.onBeforeRender();
		this.addTouchpointModule();
		this.addLastOrders();
		this.addAllSelectors();
	}
}
