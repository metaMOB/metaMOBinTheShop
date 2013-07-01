package de.metamob.ui.mainPanel;

import java.util.ArrayList;


import java.util.List;

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
import org.apache.wicket.model.util.ListModel;


import de.metamob.ui.Category;
import de.metamob.ui.Item;
import de.metamob.ui.callbacks.IMainPageCallback;
import de.metamob.ui.callbacks.IMainPageItemCallback;
import de.metamob.ui.itemPanel.ItemPanel;

import org.dieschnittstelle.jee.esa.crm.entities.*;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

public class MainPanel extends Panel implements IMainPageItemCallback {

	private String message = "Enter a message";
	private IMainPageCallback iMainPageCallback;
	
	public MainPanel(String id, IMainPageCallback mainPageCallback) {
		super(id);
		this.iMainPageCallback = mainPageCallback;
		// TODO Auto-generated constructor stub
		PropertyModel<String> messageModel = 
		         new PropertyModel<String>(this, "message");
		 	add(new Label("msg", messageModel));
	        Form<?> form = new Form("form");
	        form.add(new TextField<String>("msgInput", messageModel));
	        add(form);
	        
	        addAllSelectors();
	        addCategoryModule();  
	        addTouchpointModule();
	        addItemModule();
	}

	public MainPanel(String id, IModel<?> model) {
		super(id, model);
		// TODO Auto-generated constructor stub
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
						ProductType temp = (ProductType) entry.getModel().getObject();
						System.out.println("CATEGORY: "+ temp);
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
    
    private void addTouchpointModule() {
    	
    	List<AbstractTouchpoint> myList = generateTestTouchpoints();
    	
        ListView<AbstractTouchpoint> touchpoints = new ListView<AbstractTouchpoint>("touchpoints", myList){
			
        	@Override
			protected void populateItem(final ListItem<AbstractTouchpoint> entry) {
				// TODO Auto-generated method stub
				AjaxLink<Void> link = new AjaxLink<Void>("touchpointLink"){
					@Override
					public void onClick(AjaxRequestTarget target) {
						AbstractTouchpoint temp = (AbstractTouchpoint) entry.getModel().getObject();
						System.out.println("TOUCHPOINT: "+ temp.getName()+ " "+temp.getId());						
		            }
				};
				
				link.add(new Label("touchpointName", "TOUCHPOINT: "+entry.getModel().getObject().getName()));
				entry.add(link);
			}       	
        };
 
        add(touchpoints);
    }
    
    private List<AbstractTouchpoint> generateTestTouchpoints(){
    	String [] names = {"TP 1", "TP 2", "TP 3", "TP 4", "TP 5"};
    	int [] ids = {101, 102, 103, 104, 105};
    	
    	List<AbstractTouchpoint> myList = new ArrayList<AbstractTouchpoint>();
    	
    	for (int i=0; i<names.length; i++){
    		MobileTouchpoint tempTP = new MobileTouchpoint();
    		tempTP.setName(names[i]);
    		tempTP.setId(ids[i]);
    		myList.add(tempTP);
    	}
    	return myList;
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
    		}
    	};
    	add(linkAllTouchpoints);
    	
    	AjaxLink<Void> linkAllCategories = new AjaxLink<Void>("categoryAllLink"){
    		@Override
    		public void onClick(AjaxRequestTarget target) {
    			System.out.println("NO CATEGORY");						
    		}
    	};
    	add(linkAllCategories);
    }
    
    
    
    
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void itemPanelClicked(StockItem item) {
		// TODO Auto-generated method stub
		//SessionUser session = (SessionUser)Session.get();
		//User thisUser = session.getUser();
		//Session a=Session.get();
		//System.out.println("Hallo "+a.getAttribute("test"));
		
		/*if (thisUser.hasAnyRole(new Roles("CUSTOMER"))){
			System.out.println("JUP!!! "+item.getName());
		} else {
			System.out.println("Nicht angemeldet!");
		}*/
	}

}
