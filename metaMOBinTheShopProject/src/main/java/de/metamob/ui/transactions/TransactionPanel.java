package de.metamob.ui.transactions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;

import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.MobileTouchpoint;

import de.metamob.data.shoppingCart.ShoppingItem;
import de.metamob.data.shoppingCart.UserShoppingCart;
import de.metamob.data.shoppingCart.UserShoppingCarts;
import de.metamob.session.SessionUtil;
import de.metamob.ui.Item;
import de.metamob.ui.callbacks.IMainPageItemCallback;
import de.metamob.ui.shoppingCartPanel.touchPointAlternatives.TouchPointAlternatives;
import de.metamob.ui.shoppingCartPanel.touchPointPanel.TouchPointPanel;

import org.apache.wicket.markup.html.form.NumberTextField;

import java.text.DecimalFormat;

import org.dieschnittstelle.jee.esa.erp.entities.*;

public class TransactionPanel extends Panel {
	
	private IMainPageItemCallback iMainPageItemCallback;

	public TransactionPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public TransactionPanel(String id, IMainPageItemCallback itemPanelCallback) {
		super(id);
		this.iMainPageItemCallback = itemPanelCallback;
		// TODO Auto-generated constructor stub
		addTransactionModule();
	}
	
	public void addTransactionModule() {
		System.out.println("NEW TPPANEL################################");
		/*
		
		// TODO Auto-generated constructor stub
		add(new Label("touchpointName", tp.getName()));
		touchPointAlternatives = new TouchPointAlternatives("alternatives",new ArrayList<AbstractTouchpoint>());
		touchPointAlternatives.setOutputMarkupId(true);
		touchPointAlternatives.add(new AttributeAppender("style", new Model<String>("height:0px; overflow:hidden; margin:0px; padding:0px;")));
			
		add(touchPointAlternatives);
		//add(new TouchPointAlternatives("alternatives", touchpointCRUDRemote.readAllTouchpoints()));

		ListView<ShoppingItem> items = new ListView<ShoppingItem>("items", model){
			
			@Override
			protected void populateItem(final ListItem<ShoppingItem> entry) {
				// TODO Auto-generated method stub
				
				final ShoppingItem temp = (ShoppingItem) entry.getModelObject();
				PropertyModel<Integer> modelUnits = new PropertyModel <Integer>(self, "numOfUnits");

				//numOfUnits = temp.getUnits();
							
				
				entry.add(new Label("itemName", temp.getProduct().getName()));
				entry.add(new Label("itemPrice", new DecimalFormat("0.00").format(temp.getProduct().getPrice()/100.0)));
				
				System.out.println("PRICETOT "+priceTotal);
				
				AjaxLink<Void> delete = new AjaxLink<Void>("itemDelete"){
					@Override
					public void onClick(AjaxRequestTarget target) {
						
		                System.out.println("ITEMDELETE");
		                // FEHLT NOCH
		                // temp.remove();
		                // FEHLT NOCH
		                setResponsePage(getPage());
		            }
				};
								
				AjaxLink<Void> increase = new AjaxLink<Void>("itemUnitsInc"){
					@Override
					public void onClick(AjaxRequestTarget target) {
					    System.out.println("ITEMINCREASE");	
					    temp.incUnits();
					    setResponsePage(getPage());	
		           }
				};
				
				entry.add(new Label("itemUnits", temp.getUnits()));
				
				AjaxLink<Void> decrease = new AjaxLink<Void>("itemUnitsDec"){
					@Override
					public void onClick(AjaxRequestTarget target) {						
		                System.out.println("ITEMDECREASE");
		                temp.decUnits();
		                setResponsePage(getPage());	
		            }
				};
				
				
				entry.add(delete);
				entry.add(increase);
				entry.add(decrease);
			}
        };
        
        AjaxLink<Void> linkOrder = new AjaxLink<Void>("order"){
    		@Override
    		public void onClick(AjaxRequestTarget target) {
    			
    			if(SessionUtil.isLoggedIn()){
    				UserShoppingCart userShoppingCart = SessionUtil.getShoppingCarts().getShoppingCard(tp);
    				shoppingSessionFacade.setCustomer(SessionUtil.getCurrentUser());
    				shoppingSessionFacade.setTouchpoint(tp);
    				for (ShoppingItem item : userShoppingCart){
        				shoppingSessionFacade.addProduct(item.getProduct(), item.getUnits());
        				// MAP
        				
    				}
    				
    				try {
						shoppingSessionFacade.purchase();	
						SessionUtil.getShoppingCarts().removeShoppingCard(tp);	
						remove(touchPointAlternatives);
        				touchPointAlternatives = new TouchPointAlternatives("alternatives", touchpointCRUDRemote.readAllTouchpoints());
        				add(touchPointAlternatives);
					} catch (Exception e) {
						System.out.println("!!!!! "+e.getMessage()+" !!!!!!");
						// MAP
						remove(touchPointAlternatives);
						touchPointAlternatives = new TouchPointAlternatives("alternatives", touchpointCRUDRemote.readAllTouchpoints());
						
						add(touchPointAlternatives);
					}
    				
    				setResponsePage(getPage());	
    			}else{
    				System.out.println("!!!!! USER NOT LOGGED IN !!!!!!");
    				//touchPointAlternatives = new TouchPointAlternatives("alternatives", touchpointCRUDRemote.readAllTouchpoints());
    				touchPointAlternatives.updateData(touchpointCRUDRemote.readAllTouchpoints());
    				touchPointAlternatives.add(new AttributeAppender("style", new Model<String>("height:auto; overflow:visible; margin:15px 0 0 0; padding:10px;")));
					target.add(touchPointAlternatives);
					//setResponsePage(getPage());	
    			}
    		}
    	};
    	
    	for (ShoppingItem item:model){
    		priceTotal = priceTotal + item.getUnits() * item.getProduct().getPrice();
    	}
    	add(new Label("priceTotal",  new DecimalFormat("0.00").format(priceTotal/100.0)));
    	
    	
    	//addTouchpointAlternatives(touchpointCRUDRemote.readAllTouchpoints());
    	add(linkOrder);
        add(items);
        */
	}
	
	@Override
	public void onBeforeRender(){
		super.onBeforeRender();	

	}
}
