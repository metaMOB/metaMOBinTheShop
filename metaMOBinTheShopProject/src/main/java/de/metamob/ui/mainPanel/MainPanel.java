package de.metamob.ui.mainPanel;

import java.util.ArrayList;


import java.util.List;

import javax.ejb.EJB;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import de.metamob.data.shoppingCart.UserTransaction;
import de.metamob.session.SessionUtil;
import de.metamob.session.UIUserConfiguration;
import de.metamob.ui.Item;
import de.metamob.ui.callbacks.IMainPageCallback;
import de.metamob.ui.callbacks.IMainPageItemCallback;
import de.metamob.ui.itemPanel.ItemPanel;
import de.metamob.ui.shoppingCartPanel.ShoppingCartPanel;

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
	private MainPanel self;

	@EJB(name="TouchpointCRUD")
    private TouchpointCRUDLocal touchpointCRUDRemote;
	
	@EJB(name="CustomerTransactionCRUD")
    private CustomerTransactionCRUDLocal customerTransactionCRUDRemote;
	
	public MainPanel(String id, IMainPageCallback mainPageCallback) {
		super(id);
		self = this;
		this.iMainPageCallback = mainPageCallback;
		// TODO Auto-generated constructor stub
		
		/*PropertyModel<String> messageModel = new PropertyModel<String>(this, "message");
		 	
		add(new Label("msg", messageModel));
	    Form<?> form = new Form("form");
	    form.add(new TextField<String>("msgInput", messageModel));
	    add(form);*/
	        
	        //addAllSelectors();
	        addCategoryModule();  
	        addLastOrders();
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
	}
	
    private void addCategoryModule() {
    	List<ProductType> myList = generateProductTypes();
    	
        ListView<ProductType> categories = new ListView<ProductType>("categories", myList){

			@Override
			protected void populateItem(final ListItem<ProductType> entry) {
				// TODO Auto-generated method stub
				AjaxLink<Void> link = new AjaxLink<Void>("categoryLink"){
					@Override
					public void onClick(AjaxRequestTarget target) {
						self.mode = "SHOPPINGCART";
						currentDisplay(target);
						UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
						uiuc.setProductType((ProductType) entry.getModel().getObject());
						SessionUtil.setUIUserConfiguration(uiuc);
						
						SessionUtil.setCurrentPage(0);
						setResponsePage(getPage());	
		            }
				};
				
				link.add(new Label("categoryName", entry.getModel().getObject().name()));
				entry.add(link);	
			}
        	
        };
 
        add(categories);
    }
    
    private List<ProductType> generateProductTypes(){
    	List<ProductType> myList = new ArrayList<ProductType>();
    	myList.add(ProductType.BREAD);
    	myList.add(ProductType.PASTRY);
    	myList.add(ProductType.ROLL);
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
						self.mode = "SHOPPINGCART";
						currentDisplay(target);
						//setSelTouchPoint
						UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
						uiuc.setTouchpont(temp);
						SessionUtil.setUIUserConfiguration(uiuc);
						
						SessionUtil.setCurrentPage(0);
						setResponsePage(getPage());	
		            }
				};
				
				link.add(new Label("touchpointName", "TOUCHPOINT: "+entry.getModel().getObject().getName()));
				entry.add(link);
			}       	
        };
        
        if (touchpoints!=null){
        	remove(touchpoints);
        }
        
        add(touchpoints);
    }
    

    
    private void addItemModule() {
    	
    	List<Item> myList = new ArrayList<Item>();
    	myList.add(new Item("0001", "Broetchen", "Bal bal blub... juhu das funzt", (float) 10.99, "images/products/example.jpg"));
      	myList.add(new Item("0002", "Stulle", "Bal bal blub... juhu das funzt", (float) 1.99, "images/products/example.jpg"));
      	myList.add(new Item("0003", "Brot", "Bal bal blub... juhu das funzt", (float) 2.99, "images/products/example.jpg"));
      	myList.add(new Item("0004", "Hoernchen", "Bal bal blub... juhu das funzt", (float) 2.50, "images/products/example.jpg"));
      	myList.add(new Item("0005", "Frankfurter Kranz", "Bal bal blub... juhu das funzt", (float) 15.00, "images/products/example.jpg"));
      	myList.add(new Item("0006", "Mohnbroetchen", "Bal bal blub... juhu das funzt", (float) 0.99, "images/products/example.jpg"));
    
      	add (new ItemPanel("itemPanel", this));
    }

    private void addAllSelectors(){
    	AjaxLink<Void> linkAllTouchpoints = new AjaxLink<Void>("touchpointAllLink"){
    		@Override
    		public void onClick(AjaxRequestTarget target) {
    			System.out.println("NO TOUCHPOINT");
    			//setSelTouchPoint
    			
				UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
				uiuc.setTouchpont(null);
				SessionUtil.setUIUserConfiguration(uiuc);
				
				setResponsePage(getPage());	
    		}
    	};
    	add(linkAllTouchpoints);
    	
    	AjaxLink<Void> linkAllCategories = new AjaxLink<Void>("categoryAllLink"){
    		@Override
    		public void onClick(AjaxRequestTarget target) {
    			System.out.println("NO CATEGORY");
    			UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
				uiuc.setProductType(null);
				SessionUtil.setUIUserConfiguration(uiuc);
				setResponsePage(getPage());	
    		}
    	};
    	add(linkAllCategories);
    }
    
  /*  public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}*/
	
	public void currentDisplay(AjaxRequestTarget target){
		if (mode.equals("SHOPPINGCART")){
			System.out.println("CHANGE TO ITEMPANEL");
			itemPanel = new ItemPanel("itemPanel", this); 
			visiblePanel.replaceWith(itemPanel);
			visiblePanel = itemPanel;
			
			//visiblePanel.setOutputMarkupId(true);
			
			add(visiblePanel);
			this.mode = "ITEMDISPLAY";
			setResponsePage(getPage());	
		}
		else if (mode.equals("ITEMDISPLAY")){
			System.out.println("CHANGE TO SHOPPINGCART");
			shoppingCartPanel = new ShoppingCartPanel("itemPanel", this); 
			visiblePanel.replaceWith(shoppingCartPanel);
			visiblePanel = shoppingCartPanel;
			
			//visiblePanel.setOutputMarkupId(true);
			
			add(visiblePanel);
			this.mode = "SHOPPINGCART";
			setResponsePage(getPage());	
		}
	}
	
	ListView<CustomerTransaction> orders = null;
	 
	private void addLastOrders(){
		//List<CustomerTransaction> myList = (List<CustomerTransaction>) customerTransactionCRUDRemote.readAllTransactionsForCustomer(SessionUtil.getCurrentUser());
		List<CustomerTransaction> myList = new ArrayList<CustomerTransaction>();
		//DUMMYDATEN
		for (int i=0; i<10; i++){
			myList.add(new CustomerTransaction());
		}
		//DUMMYDATEN
		
		myList = myList.subList(myList.size()-6, myList.size()-1);
    	
	        orders = new ListView<CustomerTransaction>("lastOrders", myList){
				
	        	@Override
				protected void populateItem(final ListItem<CustomerTransaction> entry) {
	        		
	        		//UserTransaction tempOrd = new UserTransaction(entry.getModelObject());
	        		UserTransaction tempOrd = new UserTransaction();
					// TODO Auto-generated method stub	        		
					AjaxLink<Void> link = new AjaxLink<Void>("lastOrderLink"){
						@Override
						public void onClick(AjaxRequestTarget target) {
							/*AbstractTouchpoint temp = (AbstractTouchpoint) entry.getModel().getObject();
							System.out.println("TOUCHPOINT: "+ temp.getName()+ " "+temp.getId());	
							self.mode = "SHOPPINGCART";
							currentDisplay(target);
							//setSelTouchPoint
							UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
							uiuc.setTouchpont(temp);
							SessionUtil.setUIUserConfiguration(uiuc);
							
							SessionUtil.setCurrentPage(0);
							setResponsePage(getPage());	*/
			            }
					};
					
					link.add(new Label("lastOrderName", "ORDER: "+tempOrd.getDate()));
					entry.add(link);
				}       	
	        };
	        
	        if (orders!=null){
	        	remove(orders);
	        }
	        
	        add(orders);
	    }

	@Override
	public void itemPanelClicked(StockItem stockItem) {
		
	}
}
