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
	        
	        addCategoryModule();     
	        addItemModule();
	}

	public MainPanel(String id, IModel<?> model) {
		super(id, model);
		// TODO Auto-generated constructor stub
	}
	
    private void addCategoryModule() {
    	
    	List<Category> myList = new ArrayList<Category>();
    	myList.add(new Category("Abel"));
    	myList.add(new Category("Bebel"));
    	myList.add(new Category("Cebel"));
    	
        ListView<Category> categories = new ListView<Category>("categories", myList){

			@Override
			protected void populateItem(final ListItem<Category> entry) {
				// TODO Auto-generated method stub
				AjaxLink<Void> link = new AjaxLink<Void>("categoryLink"){
					@Override
					public void onClick(AjaxRequestTarget target) {
						Category temp = (Category) entry.getModel().getObject();
						
		                System.out.println("JUP "+ temp.getCategoryName());
		            }
				};
				link.add(new Label("categoryName", entry.getModel()));
				entry.add(link);
				
				
			}
        	
        };
 
        add(categories);
    }
    
    
    private void addItemModule() {
    	
    	List<Item> myList = new ArrayList<Item>();
    	myList.add(new Item("0001", "Broetchen", "Bal bal blub... juhu das funzt", (float) 10.99, "images/products/example.jpg"));
      	myList.add(new Item("0002", "Stulle", "Bal bal blub... juhu das funzt", (float) 1.99, "images/products/example.jpg"));
      	myList.add(new Item("0003", "Brot", "Bal bal blub... juhu das funzt", (float) 2.99, "images/products/example.jpg"));
      	myList.add(new Item("0004", "Hoernchen", "Bal bal blub... juhu das funzt", (float) 2.50, "images/products/example.jpg"));
      	myList.add(new Item("0005", "Frankfurter Kranz", "Bal bal blub... juhu das funzt", (float) 15.00, "images/products/example.jpg"));
      	myList.add(new Item("0006", "Mohnbroetchen", "Bal bal blub... juhu das funzt", (float) 0.99, "images/products/example.jpg"));
    
      	add (new ItemPanel("itemPanel", new ListModel<Item>(myList), this));
    }

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void itemPanelClicked(Item item) {
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
