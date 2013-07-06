package de.metamob.ui.mainPanel;

import java.util.ArrayList;


import java.util.List;

import javax.ejb.EJB;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import de.metamob.data.shoppingCart.UserTransaction;
import de.metamob.session.SessionUtil;
import de.metamob.session.UIUserConfiguration;
import de.metamob.ui.Item;
import de.metamob.ui.callbacks.IMainPageCallback;
import de.metamob.ui.callbacks.IMainPageItemCallback;
import de.metamob.ui.itemPanel.ItemPanel;
import de.metamob.ui.shoppingCartPanel.ShoppingCartPanel;
import de.metamob.ui.transactions.TransactionPanel;

import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerTransactionCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.TouchpointCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.entities.*;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

public class MainPanel extends Panel implements IMainPageItemCallback {

	//private String message = "Enter a message";
	private IMainPageCallback iMainPageCallback;
	private String mode = "ITEMDISPLAY";
	
	private Panel itemPanel = new ItemPanel("itemPanel", this);
	private Panel shoppingCartPanel;
	private Panel visiblePanel = itemPanel;
	private Panel transactionPanel;
	private MainPanel self;

	private AjaxLink<Void> linkAllCategories;
	
	@EJB(name="TouchpointCRUD")
    private TouchpointCRUDLocal touchpointCRUDRemote;
	
	@EJB(name="CustomerTransactionCRUD")
    private CustomerTransactionCRUDLocal customerTransactionCRUDRemote;
	
	public MainPanel(String id, IMainPageCallback mainPageCallback) {
		super(id);
		self = this;
		this.iMainPageCallback = mainPageCallback;
		// TODO Auto-generated constructor stub
		    
	        addCategoryModule();  
	        
	        add (itemPanel);
	        //addItemModule();
	}

	public MainPanel(String id, IModel<?> model) {
		super(id, model);
		// TODO Auto-generated constructor stub
	}
	
	private void updateData(){
		addTouchpointModule();
	}
	
	@Override
	public void onBeforeRender(){
		super.onBeforeRender();
		addTouchpointModule();
		addLastOrders();
		addAllSelectors();
	}
	
    private void addCategoryModule() {
    	List<String> myList = generateProductTypes();
    	
        ListView<String> categories = new ListView<String>("categories", myList){

			@Override
			protected void populateItem(final ListItem<String> entry) {
				// TODO Auto-generated method stub
				AjaxLink<Void> link = new AjaxLink<Void>("categoryLink"){
					@Override
					public void onClick(AjaxRequestTarget target) {
						self.mode = "ITEMDISPLAY";
						currentDisplay(target, "ITEMDISPLAY", null);
						UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
						uiuc.setProductType(ProductType.fromReadableString((String) entry.getModel().getObject()));
						SessionUtil.setUIUserConfiguration(uiuc);
						
						SessionUtil.setCurrentPage(0);
						setResponsePage(getPage());	
		            }
				};
				if (SessionUtil.getUIUserConfiguration().getProductType()==ProductType.fromReadableString((String) entry.getModel().getObject())){
					link.add(new AttributeAppender("class", new Model<String>("aktiv")));
		    	}
				link.add(new Label("categoryName", entry.getModel().getObject()));
				entry.add(link);	
			}
        	
        };
 
        add(categories);
    }
    
    private List<String> generateProductTypes(){
    	List<String> myList = new ArrayList<String>();
    	myList.add(ProductType.toReadableString(ProductType.BREAD));
    	myList.add(ProductType.toReadableString(ProductType.PASTRY));
    	myList.add(ProductType.toReadableString(ProductType.ROLL));
    	return myList;
    }
    
    
    
    ListView<AbstractTouchpoint> touchpoints = null;
    
    private void addTouchpointModule() {
    	
    	List<AbstractTouchpoint> myList = touchpointCRUDRemote.readAllTouchpoints();
    	
        touchpoints = new ListView<AbstractTouchpoint>("touchpoints", myList){
			
        	@Override
			protected void populateItem(final ListItem<AbstractTouchpoint> entry) {
				// TODO Auto-generated method stub
				AjaxLink<Void> link = new AjaxLink<Void>("touchpointLink"){
					@Override
					public void onClick(AjaxRequestTarget target) {
						AbstractTouchpoint temp = (AbstractTouchpoint) entry.getModel().getObject();
						System.out.println("TOUCHPOINT: "+ temp.getName()+ " "+temp.getId());	
						self.mode = "ITEMDISPLAY";
						currentDisplay(target, "ITEMDISPLAY", null);
						//setSelTouchPoint
						UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
						uiuc.setTouchpont(temp);
						SessionUtil.setUIUserConfiguration(uiuc);
						
						SessionUtil.setCurrentPage(0);
						setResponsePage(getPage());	
		            }
				};
				
				link.add(new Label("touchpointName", entry.getModel().getObject().getName()));
				if (SessionUtil.getUIUserConfiguration().getTouchpont().getId()==entry.getModel().getObject().getId()){
					link.add(new AttributeAppender("class", new Model<String>("aktiv")));
		    	}				
				entry.add(link);
			}       	
        };
        
        if (touchpoints!=null){
        	remove(touchpoints);
        }
        
        add(touchpoints);
    }
    
    private void addAllSelectors(){
    	linkAllCategories = new AjaxLink<Void>("categoryAllLink"){
    		@Override
    		public void onClick(AjaxRequestTarget target) {
    			System.out.println("NO CATEGORY");
    			UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
				uiuc.setProductType(null);
				SessionUtil.setUIUserConfiguration(uiuc);
				
				setResponsePage(getPage());	
    		}
    	};
    	if (SessionUtil.getUIUserConfiguration().getProductType()==null){
    		linkAllCategories.add(new AttributeAppender("class", new Model<String>("aktiv")));
    	}
    	if (linkAllCategories != null){
    		remove(linkAllCategories);
    	}
    	add(linkAllCategories);
    }
    
  /*  public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}*/
	
	public void currentDisplay(AjaxRequestTarget target, String view, Object object){
		if (view.equals("ITEMDISPLAY")){
			System.out.println("CHANGE TO ITEMPANEL");
			itemPanel = new ItemPanel("itemPanel", this); 
			visiblePanel.replaceWith(itemPanel);
			visiblePanel = itemPanel;
			
			//visiblePanel.setOutputMarkupId(true);
			
			add(visiblePanel);
			setResponsePage(getPage());	
		}
		else if (view.equals("SHOPPINGCART")){
			System.out.println("CHANGE TO SHOPPINGCART");
			shoppingCartPanel = new ShoppingCartPanel("itemPanel", this); 
			visiblePanel.replaceWith(shoppingCartPanel);
			visiblePanel = shoppingCartPanel;
			
			add(visiblePanel);
			setResponsePage(getPage());	
		}
		else if (view.equals("TRANSACTIONDISPLAY")){
			System.out.println("CHANGE TO TRANSACTIONDISPLAY");
			transactionPanel = new TransactionPanel("itemPanel", this, (UserTransaction) object);
			visiblePanel.replaceWith(transactionPanel);
			visiblePanel = transactionPanel;
			add(visiblePanel);
			setResponsePage(getPage());	
		}
	}
	
	ListView<CustomerTransaction> orders = null;
	 
	private void addLastOrders(){
		//List<CustomerTransaction> myList = (List<CustomerTransaction>) customerTransactionCRUDRemote.readAllTransactionsForCustomer(SessionUtil.getCurrentUser());
		List<CustomerTransaction> myList = new ArrayList<CustomerTransaction>();
		//DUMMYDATEN
		/*for (int i=0; i<10; i++){
			myList.add(new CustomerTransaction());
		}*/
		//DUMMYDATEN
		if (SessionUtil.isLoggedIn()){
			myList = (List<CustomerTransaction>) customerTransactionCRUDRemote.readAllTransactionsForCustomer(SessionUtil.getCurrentUser());
		} 
			////myList = myList.subList(myList.size()-6, myList.size()-1);
    	
	        orders = new ListView<CustomerTransaction>("lastOrders", myList){
				
	        	@Override
				protected void populateItem(final ListItem<CustomerTransaction> entry) {
	        		
	        		final UserTransaction tempOrder = new UserTransaction(entry.getModelObject());
	        		//final UserTransaction tempOrder = new UserTransaction();
					// TODO Auto-generated method stub	        		
					AjaxLink<Void> link = new AjaxLink<Void>("lastOrderLink"){
						@Override
						public void onClick(AjaxRequestTarget target) {							
							currentDisplay(target, "TRANSACTIONDISPLAY", tempOrder);
							setResponsePage(getPage());	
			            }
					};
					
					link.add(new Label("lastOrderName", "ORDER: "+tempOrder.getDate()));
					entry.add(link);
				}       	
	        };
	        
	        if (orders!=null){
	        	remove(orders);
	        }
	        
	        add(orders);
	    }

	@Override
	public void itemPanelClicked() {
		iMainPageCallback.unitsChanged();
	}
}
