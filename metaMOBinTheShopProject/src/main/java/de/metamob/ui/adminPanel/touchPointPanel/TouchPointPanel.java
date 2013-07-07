package de.metamob.ui.adminPanel.touchPointPanel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.TouchpointCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.erp.ejbs.ShoppingSessionFacadeLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;

public class TouchPointPanel extends Panel {
	class UnitManager implements Serializable {
        /**
		 *
		 */
		private static final long	serialVersionUID	= 6967972067632694877L;

		private final IndividualisedProductItem item;

		public UnitManager (final IndividualisedProductItem temp){
			this.item = temp;
		}

		public int getUnits() {
			return TouchPointPanel.this.stockSystem.getUnitsOnStock(this.item, TouchPointPanel.this.tp.getErpPointOfSaleId());
		}

		public void setUnits(final int units) {
			TouchPointPanel.this.stockSystem.setUnitsOnStock(this.item, TouchPointPanel.this.tp.getErpPointOfSaleId(), units);
		}
    }

	private static final long serialVersionUID = -2165796168630701367L;

	private String message;

	@EJB(name="ProductCRUD")
	private ProductCRUDLocal productCRUD;

	private TouchPointPanel self;

	@EJB(name="shoppingSystem")
	private ShoppingSessionFacadeLocal shoppingSession;

	@EJB(name="StockSystem")
	private StockSystemLocal stockSystem;
	@EJB(name="TouchpointCRUD")
    private TouchpointCRUDLocal touchpointCRUD;

	private AbstractTouchpoint tp;

	public TouchPointPanel(final String id) {
		super(id);
	}


	public TouchPointPanel(final String id, final List<IndividualisedProductItem> itemList, final AbstractTouchpoint tp) {
		super(id);

		this.self = this;
		this.tp = tp;
		this.add(new Label("touchpointName", tp.getName()));

		final ListView<IndividualisedProductItem> items = new ListView<IndividualisedProductItem>("items", new ArrayList<IndividualisedProductItem>(itemList)){

			@Override
			protected void populateItem(final ListItem<IndividualisedProductItem> entry) {
				// TODO Auto-generated method stub
				final IndividualisedProductItem temp = entry.getModelObject();

				final PropertyModel<Integer> priceModel;
				priceModel = new PropertyModel<Integer>(temp, "price");

				final UnitManager units = new UnitManager(temp);
				final PropertyModel<Integer> unitModel;
				unitModel = new PropertyModel<Integer>(units, "units");

				new PropertyModel <Integer>(TouchPointPanel.this.self, "numOfUnits");

				//numOfUnits = temp.getUnits();


				entry.add(new Label("itemName", temp.getName()));
				entry.add(new Label("itemCategory", temp.getProductType()));

				final TextField priceField = new TextField<Integer>("itemPrice", priceModel);
				priceField.add(new OnChangeAjaxBehavior(){
			        @Override
			        protected void onUpdate(final AjaxRequestTarget target){
			        	System.out.println("CHANGED "+priceField.getModelObject());
			        	final int value = (Integer) priceField.getModelObject();
			        	temp.setPrice(value);
			        	TouchPointPanel.this.productCRUD.updateProduct(temp);
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
			        	final int value = (Integer) itemUnits.getModelObject();
			        	TouchPointPanel.this.stockSystem.setUnitsOnStock(temp, tp.getErpPointOfSaleId(), value);
			        }
			    });
				entry.add(itemUnits);

				final AjaxLink<Void> delete = new AjaxLink<Void>("itemDelete"){
					@Override
					public void onClick(final AjaxRequestTarget target) {
						System.out.println("ITEMDELETE");
		                TouchPointPanel.this.stockSystem.setUnitsOnStock(temp, tp.getErpPointOfSaleId(), 0);
		                target.add(itemUnits);
		            }
				};

				final AjaxLink<Void> increase = new AjaxLink<Void>("itemUnitsInc"){
					@Override
					public void onClick(final AjaxRequestTarget target) {
					    System.out.println("ITEMINCREASE");
					    TouchPointPanel.this.stockSystem.setUnitsOnStock(temp, tp.getErpPointOfSaleId(),  TouchPointPanel.this.stockSystem.getUnitsOnStock(temp, tp.getErpPointOfSaleId())+1);
					    target.add(itemUnits);
					}
				};


				final AjaxLink<Void> decrease = new AjaxLink<Void>("itemUnitsDec"){
					@Override
					public void onClick(final AjaxRequestTarget target) {
		                System.out.println("ITEMDECREASE");
		                TouchPointPanel.this.stockSystem.setUnitsOnStock(temp, tp.getErpPointOfSaleId(),  Math.max(0, TouchPointPanel.this.stockSystem.getUnitsOnStock(temp, tp.getErpPointOfSaleId())-1));
		                target.add(itemUnits);
					}
				};

				entry.add(delete);
				entry.add(increase);
				entry.add(decrease);
			}
        };
        this.add(items);
	}


	public String getMessage() {
		return this.message;
	}
	public void setMessage(final String message) {
		this.message = message;
	}
}
