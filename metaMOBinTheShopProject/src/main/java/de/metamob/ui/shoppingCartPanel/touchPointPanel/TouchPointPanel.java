package de.metamob.ui.shoppingCartPanel.touchPointPanel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

import de.metamob.data.shoppingCart.ShoppingItem;
import de.metamob.session.SessionUtil;
import de.metamob.session.UIUserConfiguration;

public class TouchPointPanel extends Panel {
	
	
	private TouchPointPanel self;
	private int priceTotal = 0;	
	
	public TouchPointPanel(String id) {
		super(id);
	}

	public TouchPointPanel(String id, List<?> model, final AbstractTouchpoint tp) {
		super(id);
		// TODO Auto-generated constructor stub
		self = this;
		add(new Label("touchpointName", tp.getName()));
		
		ListView<ShoppingItem> items = new ListView<ShoppingItem>("items", (List<ShoppingItem>) model){
			
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
		                temp.setUnits(0);
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
    			System.out.println("ORDER");
    		}
    	};
    	
    	add(new Label("priceTotal", priceTotal/100.0));
    	add(linkOrder);
        add(items);
	}

}
