package de.metamob.ui.adminPanel.touchPointPanel;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.TouchpointCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.erp.ejbs.ShoppingSessionFacadeLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;
import org.dieschnittstelle.jee.esa.erp.exceptions.ProductNotInStockException;
import org.dieschnittstelle.jee.esa.erp.exceptions.ProductUnitCountToLowInStockException;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GMarker;
import org.wicketstuff.gmap.api.GMarkerOptions;
import org.wicketstuff.gmap.api.GLatLng;

import de.metamob.data.shoppingCart.ShoppingItem;
import de.metamob.data.shoppingCart.UserShoppingCart;
import de.metamob.data.shoppingCart.UserShoppingCarts;
import de.metamob.session.SessionUtil;
import de.metamob.session.UIUserConfiguration;
import de.metamob.ui.callbacks.IMainPageCallback;
import de.metamob.ui.callbacks.IMainPageItemCallback;
import de.metamob.ui.shoppingCartPanel.touchPointAlternatives.TouchPointAlternatives;

public class TouchPointPanel extends Panel {
	private static final long serialVersionUID = -2165796168630701367L;

	@EJB(name="shoppingSystem")
	private ShoppingSessionFacadeLocal shoppingSession;
	
	@EJB(name="TouchpointCRUD")
    private TouchpointCRUDLocal touchpointCRUD;
	
	@EJB(name="StockSystem")
	private StockSystemLocal stockSystem;
	
	@EJB(name="ProductCRUD")
	private ProductCRUDLocal productCRUD;
	
	private TouchPointPanel self;
	
	private String message;
	private List<IndividualisedProductItem> itemList;
	private AbstractTouchpoint tp;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public TouchPointPanel(String id) {
		super(id);
	}

	
	public TouchPointPanel(String id, List<IndividualisedProductItem> itemList, final AbstractTouchpoint tp) {
		super(id);
		
		self = this;	
		this.itemList = itemList;
		this.tp = tp;
		add(new Label("touchpointName", tp.getName()));
		
		ListView<IndividualisedProductItem> items = new ListView<IndividualisedProductItem>("items", new ArrayList<IndividualisedProductItem>(itemList)){
			
			@Override
			protected void populateItem(final ListItem<IndividualisedProductItem> entry) {
				// TODO Auto-generated method stub
				final IndividualisedProductItem temp = (IndividualisedProductItem) entry.getModelObject();
				
				final PropertyModel<Integer> priceModel;
				priceModel = new PropertyModel<Integer>(temp, "price");
				
				UnitManager units = new UnitManager(temp);				
				final PropertyModel<Integer> unitModel;
				unitModel = new PropertyModel<Integer>(units, "units");
				
				PropertyModel<Integer> modelUnits = new PropertyModel <Integer>(self, "numOfUnits");

				//numOfUnits = temp.getUnits();
							
				
				entry.add(new Label("itemName", temp.getName()));
				entry.add(new Label("itemCategory", temp.getProductType()));
				
				final TextField priceField = new TextField<Integer>("itemPrice", priceModel);
				priceField.add(new OnChangeAjaxBehavior(){
			        @Override
			        protected void onUpdate(final AjaxRequestTarget target){			        	
			        	System.out.println("CHANGED "+priceField.getModelObject());
			        	int value = (Integer) priceField.getModelObject();
			        	temp.setPrice(value);
			        	productCRUD.updateProduct(temp);
			        }
			    });
				entry.add(priceField);
				
				//entry.add(new Label("itemUnits", stockSystem.getUnitsOnStock(temp, tp.getErpPointOfSaleId())));
				final TextField itemUnits = new TextField("itemUnits", unitModel);
				itemUnits.setOutputMarkupId(true);
				itemUnits.add(new OnChangeAjaxBehavior(){
			        @Override
			        protected void onUpdate(final AjaxRequestTarget target){			        	
			        	System.out.println("CHANGED "+priceField.getModelObject());
			        	int value = (Integer) itemUnits.getModelObject();
			        	stockSystem.setUnitsOnStock(temp, tp.getErpPointOfSaleId(), value);
			        }
			    });
				entry.add(itemUnits);
				
				AjaxLink<Void> delete = new AjaxLink<Void>("itemDelete"){
					@Override
					public void onClick(AjaxRequestTarget target) {
						System.out.println("ITEMDELETE");
		                stockSystem.setUnitsOnStock(temp, tp.getErpPointOfSaleId(), 0);
		                target.add(itemUnits);
		            }
				};
								
				AjaxLink<Void> increase = new AjaxLink<Void>("itemUnitsInc"){
					@Override
					public void onClick(AjaxRequestTarget target) {
					    System.out.println("ITEMINCREASE");	
					    stockSystem.setUnitsOnStock(temp, tp.getErpPointOfSaleId(),  stockSystem.getUnitsOnStock(temp, tp.getErpPointOfSaleId())+1);
					    target.add(itemUnits);
					}
				};
							
				
				AjaxLink<Void> decrease = new AjaxLink<Void>("itemUnitsDec"){
					@Override
					public void onClick(AjaxRequestTarget target) {						
		                System.out.println("ITEMDECREASE");
		                stockSystem.setUnitsOnStock(temp, tp.getErpPointOfSaleId(),  Math.max(0, stockSystem.getUnitsOnStock(temp, tp.getErpPointOfSaleId())-1));	
		                target.add(itemUnits);
					}
				};			
				
				entry.add(delete);
				entry.add(increase);
				entry.add(decrease);
			}
        };
        add(items);        
	}	
	class UnitManager implements Serializable {
        private int units;

		public int getUnits() {
			return stockSystem.getUnitsOnStock(item, tp.getErpPointOfSaleId());
		}

		public void setUnits(int units) {
			stockSystem.setUnitsOnStock(item, tp.getErpPointOfSaleId(), units);
		}
		
		private IndividualisedProductItem item;
		
		public UnitManager (IndividualisedProductItem temp){
			item = temp;
		}
    }	
}
