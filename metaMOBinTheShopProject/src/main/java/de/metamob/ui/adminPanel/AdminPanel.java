package de.metamob.ui.adminPanel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;

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
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.TouchpointCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.MobileTouchpoint;

import de.metamob.data.shoppingCart.ShoppingItem;
import de.metamob.data.shoppingCart.UserShoppingCarts;
import de.metamob.session.SessionUtil;
import de.metamob.ui.Item;
import de.metamob.ui.callbacks.IMainPageItemCallback;
import de.metamob.ui.adminPanel.touchPointPanel.TouchPointPanel;

import org.apache.wicket.markup.html.form.NumberTextField;

import java.text.DecimalFormat;

import javax.ejb.EJB;

import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.*;

public class AdminPanel extends Panel {
	
	@EJB(name="StockSystem")
	private StockSystemLocal stockSystem;
	
	@EJB(name="ProductCRUD")
	private ProductCRUDLocal productCRUD;
	
	@EJB(name="PointOfSaleCRUD")
    private PointOfSaleCRUDLocal pointOfSaleCRUD;
	
	@EJB(name="TouchpointCRUD")
    private TouchpointCRUDLocal touchpointCRUD;
	
	public AdminPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	private boolean once = false;
	
	private void addTouchpointPanel(){
	
		List<AbstractTouchpoint> allTouchpoints = touchpointCRUD.readAllTouchpoints();
				
		ListView<AbstractTouchpoint> touchpoints = new ListView<AbstractTouchpoint>("touchpoints", allTouchpoints){
			@Override
			protected void populateItem(ListItem<AbstractTouchpoint> entry) {
				
				AbstractTouchpoint oneTouchpoint = entry.getModelObject();
				List<IndividualisedProductItem> itemList = new ArrayList<IndividualisedProductItem>();
				
				for (ProductType type:ProductType.values()){
					itemList.addAll(stockSystem.getProductsOnStock(oneTouchpoint.getErpPointOfSaleId(), type, SortType.ASC));
				}
				
				/*if (!once){
				once = true;
					for (IndividualisedProductItem t: itemList){
					t.setPrice(999);
					stockSystem.addToStock(t, oneTouchpoint.getErpPointOfSaleId(), 0);
				}
				}*/
				
				
				TouchPointPanel touchPointPanel = new TouchPointPanel("oneTouchpoint", itemList, oneTouchpoint);
				System.out.println("GENERATE TOUCHPOINTPANEL BY SHOPPINGCART");
				entry.add(touchPointPanel);
				}			
		};
		if (touchpoints!=null){
        	remove(touchpoints);
        }
		add(touchpoints);
	}
	
	
	@Override
	public void onBeforeRender(){
		super.onBeforeRender();
		addTouchpointPanel();
	}
}
