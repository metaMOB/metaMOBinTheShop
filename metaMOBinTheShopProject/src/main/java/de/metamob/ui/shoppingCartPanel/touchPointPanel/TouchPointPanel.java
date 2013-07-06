package de.metamob.ui.shoppingCartPanel.touchPointPanel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
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
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
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
import de.metamob.ui.shoppingCartPanel.touchPointAlternatives.TouchPointAlternatives;

public class TouchPointPanel extends Panel {
	private static final long serialVersionUID = -2165796168630701367L;

	@EJB(name="shoppingSystem")
	private ShoppingSessionFacadeLocal shoppingSession;
	
	@EJB(name="TouchpointCRUD")
    private TouchpointCRUDLocal touchpointCRUD;
	
	@EJB(name="StockSystem")
	private StockSystemLocal stockSystem;
	
	private TouchPointPanel self;
	private int priceTotal = 0;	
	private TouchPointAlternatives touchPointAlternatives;
	private Panel visiblePanel;
	
	public TouchPointPanel(String id) {
		super(id);
	}

	public TouchPointPanel(String id, final UserShoppingCart model, final AbstractTouchpoint tp) {
		super(id);
		System.out.println("NEW TPPANEL################################");
		
		
		// TODO Auto-generated constructor stub
		self = this;
		add(new Label("touchpointName", tp.getName()));
		
		if (touchPointAlternatives == null){
			touchPointAlternatives = new TouchPointAlternatives("alternatives",new ArrayList<AbstractTouchpoint>());
		}
		touchPointAlternatives.setOutputMarkupId(true);
		//touchPointAlternatives.add(new AttributeAppender("style", new Model<String>("height:0px; overflow:hidden; margin:0px; padding:0px;")));
			
		add(touchPointAlternatives);
		//add(new TouchPointAlternatives("alternatives", touchpointCRUDRemote.readAllTouchpoints()));

		ListView<ShoppingItem> items = new ListView<ShoppingItem>("items", new ArrayList<ShoppingItem>(model)){
			
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
		                model.remove(temp);
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
    				shoppingSession.reset();
    				UserShoppingCart userShoppingCart = SessionUtil.getShoppingCarts().getShoppingCard(tp);
    				shoppingSession.setCustomer(SessionUtil.getCurrentUser());
    				shoppingSession.setTouchpoint(tp);

    				for (ShoppingItem item : userShoppingCart){
        				shoppingSession.addProduct(item.getProduct(), item.getUnits());
        				// MAP
        				System.out.println("PRODUCTS "+item.getProduct().getName()+" "+item.getUnits());
        				
    				}
    				try {
						shoppingSession.purchase();							
						System.out.println("TP SIZE#######1!'!'!'!'!' "+	SessionUtil.getShoppingCarts().getTouchpoints().size());
						SessionUtil.getShoppingCarts().removeShoppingCard(tp);	
						System.out.println("TP SIZE#######2!'!'!'!'!' "+	SessionUtil.getShoppingCarts().getTouchpoints().size());
						remove(touchPointAlternatives);
						
						setResponsePage(getPage());
						
					} catch (ProductNotInStockException e) {
						AbstractProduct product = e.getProduct();
						String errorMSG = "Product '"+product.getName()+"' befindet sich nicht im Lager des Touchpoints.";
						if (e instanceof ProductUnitCountToLowInStockException) {
							errorMSG = "Touchpoint verfügt nicht über genügend einheiten des Products '"+product.getName()+"'";
						}
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						System.out.println(errorMSG);
						List<AbstractTouchpoint> lst = touchpointCRUD.readTouchpoins(stockSystem.getPointsOfSale((IndividualisedProductItem) product,e.getUnits()));
						//touchPointAlternatives = new TouchPointAlternatives("alternatives", lst);
						touchPointAlternatives.updateData(lst);
						touchPointAlternatives.add(new AttributeAppender("style", new Model<String>("height:auto; overflow:visible; margin:15px 0 0 0; padding:10px;")));
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + lst.size());
						
						//add(touchPointAlternatives);
						target.add(touchPointAlternatives);
        				//setResponsePage(getPage());
					} catch (Exception e) {
						
						e.printStackTrace();
					}
    				
    				//setResponsePage(getPage());	
    				
    			}else{
    				System.out.println("!!!!! USER NOT LOGGED IN !!!!!!");
    				//touchPointAlternatives = new TouchPointAlternatives("alternatives", touchpointCRUDRemote.readAllTouchpoints());
    				//touchPointAlternatives.updateData(touchpointCRUD.readAllTouchpoints());
    				//touchPointAlternatives.add(new AttributeAppender("style", new Model<String>("height:auto; overflow:visible; margin:15px 0 0 0; padding:10px;")));
					//target.add(touchPointAlternatives);
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
	}
	
	
}
