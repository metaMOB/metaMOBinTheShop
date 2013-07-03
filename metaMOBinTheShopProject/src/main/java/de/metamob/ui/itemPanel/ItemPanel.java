package de.metamob.ui.itemPanel;

import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.List;

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

public class ItemPanel extends Panel {
	
	private IMainPageItemCallback iMainPageItemCallback;

	public ItemPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public ItemPanel(String id, IMainPageItemCallback itemPanelCallback) {
		super(id);
		this.iMainPageItemCallback = itemPanelCallback;
		// TODO Auto-generated constructor stub
		addItemPanelModule();
	}
	
	private List<StockItem> generateTestProducts(){
    	String [] names = {"Stulle", "Brotchen", "Kuchen", "Zimtstern", "Mohngebaeck"};
    	int [] ids = {991, 992, 993, 994, 995};
    	int [] pos = {101, 101, 102, 103, 103};
    	int [] price = {199, 99, 599, 250, 50};
    	ProductType [] type = {ProductType.BREAD, ProductType.ROLL, ProductType.PASTRY, ProductType.PASTRY, ProductType.ROLL};
    	
    	List<StockItem> myList = new ArrayList<StockItem>();
    	
    	for (int i=0; i<names.length; i++){
    		IndividualisedProductItem tempIndividualProduct = new IndividualisedProductItem(names[i], type[i], 10 ,10);
    		tempIndividualProduct.setId(ids[i]);
    		PointOfSale tempPointOfSale = new PointOfSale();
    		tempPointOfSale.setId(pos[i]);
    		
    		StockItem tempStockItem = new StockItem(tempIndividualProduct, tempPointOfSale, 10);
    		tempStockItem.setPrice(price[i]);
    		myList.add(tempStockItem);
    	}
    	return myList;
    }
	
	private void addItemPanelModule(){
		// CRUD
		List<IndividualisedProductItem> myList = null;// generateTestProducts();
		// CRUD
		
		ListView<IndividualisedProductItem> items = new ListView<IndividualisedProductItem>("items", myList){
			@Override
			protected void populateItem(final ListItem<IndividualisedProductItem> entry) {
				// TODO Auto-generated method stub
				AjaxLink<Void> link = new AjaxLink<Void>("itemLink"){
					@Override
					public void onClick(AjaxRequestTarget target) {
		                System.out.println("ITEM: "+ entry.getModelObject().getName());
		            }
				};
				entry.add(link);
				entry.add(new Label("itemName", entry.getModelObject().getName()));
				entry.add(new Label("itemDescription", entry.getModelObject().getProductType()));
				entry.add(new Label("itemPrice", new DecimalFormat("0.00").format(entry.getModelObject().getPrice()/100.0)));
				entry.add(new Image("itemImage", new ContextRelativeResource("images/products/example.jpg")));
			}
        };
        add(items);
	}
	
	@Override
	public void onBeforeRender(){
		super.onBeforeRender();
		addItemPanelModule();
	}
}
