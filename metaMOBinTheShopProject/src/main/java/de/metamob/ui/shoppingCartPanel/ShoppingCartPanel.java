package de.metamob.ui.shoppingCartPanel;

import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.MobileTouchpoint;

import de.metamob.ui.Item;
import de.metamob.ui.callbacks.IMainPageItemCallback;

import org.apache.wicket.markup.html.form.NumberTextField;

import java.text.DecimalFormat;

import org.dieschnittstelle.jee.esa.erp.entities.*;

public class ShoppingCartPanel extends Panel {
	
	private IMainPageItemCallback iMainPageItemCallback;

	public ShoppingCartPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public ShoppingCartPanel(String id, IMainPageItemCallback itemPanelCallback) {
		super(id);
		this.iMainPageItemCallback = itemPanelCallback;
		// TODO Auto-generated constructor stub
		
		List<StockItem> myList = generateTestProducts();
		
		ListView<StockItem> items = new ListView<StockItem>("items", myList){

			@Override
			protected void populateItem(final ListItem<StockItem> entry) {
				// TODO Auto-generated method stub
				
				StockItem temp = (StockItem) entry.getModel().getObject();
								
				entry.add(new Label("itemName", temp.getProduct().getName()));
				entry.add(new Label("itemPrice", new DecimalFormat("0.00").format(temp.getPrice()/100.0)));
				
				AjaxLink<Void> delete = new AjaxLink<Void>("itemDelete"){
					@Override
					public void onClick(AjaxRequestTarget target) {
						
		                System.out.println("ITEMDELETE");
		            }
				};
				
				NumberTextField<Integer> number = new NumberTextField<Integer>("itemNumber");
				number.add(new AjaxEventBehavior("onclick") {
				    @Override
				    protected void onEvent(AjaxRequestTarget target) {
				    	System.out.println("NUMBER");
				    }
				});
				
				entry.add(delete);
				entry.add(number);
			}
        };
        add(items);
	}
	
	private List<StockItem> generateTestProducts(){
    	String [] names = {"Stulle", "Brotchen", "Kuchen", "Zimtstern", "Mohngebaeck"};
    	int [] ids = {991, 992, 993, 994, 995};
    	int [] pos = {101, 101, 102, 103, 103};
    	int [] price = {199, 99, 599, 250, 50};
    	ProductType [] type = {ProductType.BREAD, ProductType.ROLL, ProductType.PASTRY, ProductType.PASTRY, ProductType.ROLL};
    	
    	List<StockItem> myList = new ArrayList<StockItem>();
    	
    	for (int i=0; i<names.length; i++){
    		IndividualisedProductItem tempIndividualProduct = new IndividualisedProductItem(names[i], type[i], 10);
    		tempIndividualProduct.setId(ids[i]);
    		PointOfSale tempPointOfSale = new PointOfSale();
    		tempPointOfSale.setId(pos[i]);
    		
    		StockItem tempStockItem = new StockItem(tempIndividualProduct, tempPointOfSale, 10);
    		tempStockItem.setPrice(price[i]);
    		myList.add(tempStockItem);
    	}
    	return myList;
    }
}
