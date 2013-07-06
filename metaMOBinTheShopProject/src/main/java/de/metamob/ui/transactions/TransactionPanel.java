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
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;
import org.dieschnittstelle.jee.esa.crm.entities.MobileTouchpoint;

import de.metamob.data.shoppingCart.ShoppingItem;
import de.metamob.data.shoppingCart.UserShoppingCart;
import de.metamob.data.shoppingCart.UserShoppingCarts;
import de.metamob.data.shoppingCart.UserTransaction;
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
	private int priceTotal = 0;
	
	public TransactionPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public TransactionPanel(String id, IMainPageItemCallback itemPanelCallback, UserTransaction transaction) {
		super(id);
		//this.iMainPageItemCallback = itemPanelCallback;
		// TODO Auto-generated constructor stub
		addTransactionModule(transaction);
	}
	
	public void addTransactionModule(UserTransaction transaction) {
		
		// TODO Auto-generated constructor stub
		add(new Label("touchpointName", transaction.getTouchpoint().getName()));
		add(new Label("orderDate", transaction.getDate()));
		
		ListView<ShoppingItem> items = new ListView<ShoppingItem>("items", transaction.getProducts()){
			
			@Override
			protected void populateItem(final ListItem<ShoppingItem> entry) {
				// TODO Auto-generated method stub
				
				final ShoppingItem temp = (ShoppingItem) entry.getModelObject();
								
				entry.add(new Label("itemName", temp.getProduct().getName()));
				entry.add(new Label("itemPrice", new DecimalFormat("0.00").format(temp.getProduct().getPrice()/100.0)));
				entry.add(new Label("itemUnits", temp.getUnits()));
			}
        };
        
       
    	for (ShoppingItem item:transaction.getProducts()){
    		priceTotal = priceTotal + item.getUnits() * item.getProduct().getPrice();
    	}
    	add(new Label("priceTotal",  new DecimalFormat("0.00").format(priceTotal/100.0)));
        add(items);
	}
	
	@Override
	public void onBeforeRender(){
		super.onBeforeRender();	

	}
}
