package de.metamob.ui.shoppingCartPanel.touchPointPanel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.dieschnittstelle.jee.esa.crm.ejbs.crud.TouchpointCRUDLocal;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.erp.ejbs.ShoppingSessionFacadeLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.exceptions.ProductNotInStockException;
import org.dieschnittstelle.jee.esa.erp.exceptions.ProductUnitCountToLowInStockException;

import de.metamob.data.shoppingCart.ShoppingItem;
import de.metamob.data.shoppingCart.UserShoppingCart;
import de.metamob.session.SessionUtil;
import de.metamob.ui.callbacks.IMainPageItemCallback;
import de.metamob.ui.shoppingCartPanel.touchPointAlternatives.TouchPointAlternatives;

public class TouchPointPanel extends Panel {
	private static final long serialVersionUID = -2165796168630701367L;

	private final String htmlPraefix = "<span class=\"warning\">!</span>";

	IMainPageItemCallback itemPanelCallback;

	private String message;

	private Label messageLabel;
	private PropertyModel<String> messageModel;
	private int priceTotal = 0;
	private TouchPointPanel self;
	@EJB(name="shoppingSystem")
	private ShoppingSessionFacadeLocal shoppingSession;

	@EJB(name="StockSystem")
	private StockSystemLocal stockSystem;
	private TouchPointAlternatives touchPointAlternatives;

	@EJB(name="TouchpointCRUD")
    private TouchpointCRUDLocal touchpointCRUD;

	public TouchPointPanel(final String id) {
		super(id);
	}

	public TouchPointPanel(final String id, final UserShoppingCart model, final AbstractTouchpoint tp, final IMainPageItemCallback itemPanelCallback) {
		super(id);
		this.messageModel = new PropertyModel<String>(this, "message");
		this.itemPanelCallback = itemPanelCallback;

		// TODO Auto-generated constructor stub
		this.self = this;

		this.add(new Label("touchpointName", tp.getName()));

		if (SessionUtil.isLoggedIn()){
			this.messageModel.setObject("");
		} else {
			this.messageModel.setObject(this.htmlPraefix+"Bestellung erst nach Anmeldung möglich!");
		}
		this.messageLabel = new Label("message",this.messageModel);
		this.messageLabel.setOutputMarkupId(true);
		this.messageLabel.setEscapeModelStrings(false);
		this.add(this.messageLabel);

		if (this.touchPointAlternatives == null){
			this.touchPointAlternatives = new TouchPointAlternatives("alternatives",new ArrayList<AbstractTouchpoint>(), "");
		}
		this.touchPointAlternatives.setOutputMarkupId(true);
		this.add(this.touchPointAlternatives);
		
		final ListView<ShoppingItem> items = new ListView<ShoppingItem>("items", new ArrayList<ShoppingItem>(model)){

			@Override
			protected void populateItem(final ListItem<ShoppingItem> entry) {
				// TODO Auto-generated method stub

				final ShoppingItem temp = entry.getModelObject();
				new PropertyModel <Integer>(TouchPointPanel.this.self, "numOfUnits");

				entry.add(new Label("itemName", temp.getProduct().getName()));
				entry.add(new Label("itemPrice", new DecimalFormat("0.00").format(temp.getProduct().getPrice()/100.0)));

				System.out.println("PRICETOT "+TouchPointPanel.this.priceTotal);

				final AjaxLink<Void> delete = new AjaxLink<Void>("itemDelete"){
					@Override
					public void onClick(final AjaxRequestTarget target) {

		                System.out.println("ITEMDELETE");
		                // FEHLT NOCH
		                model.remove(temp);
		                itemPanelCallback.itemPanelClicked();
		                this.setResponsePage(this.getPage());
		            }
				};

				final AjaxLink<Void> increase = new AjaxLink<Void>("itemUnitsInc"){
					@Override
					public void onClick(final AjaxRequestTarget target) {
					    System.out.println("ITEMINCREASE");
					    temp.incUnits();
					    itemPanelCallback.itemPanelClicked();
					    this.setResponsePage(this.getPage());
		           }
				};

				entry.add(new Label("itemUnits", temp.getUnits()));

				final AjaxLink<Void> decrease = new AjaxLink<Void>("itemUnitsDec"){
					@Override
					public void onClick(final AjaxRequestTarget target) {
		                System.out.println("ITEMDECREASE");
		                temp.decUnits();
		                itemPanelCallback.itemPanelClicked();
		                this.setResponsePage(this.getPage());
		            }
				};


				entry.add(delete);
				entry.add(increase);
				entry.add(decrease);
			}
        };

        final AjaxLink<Void> linkOrder = new AjaxLink<Void>("order"){
    		@Override
    		public void onClick(final AjaxRequestTarget target) {

    			if(SessionUtil.isLoggedIn()){
    				TouchPointPanel.this.shoppingSession.reset();
    				final UserShoppingCart userShoppingCart = SessionUtil.getShoppingCarts().getShoppingCard(tp);
    				TouchPointPanel.this.shoppingSession.setCustomer(SessionUtil.getCurrentUser());
    				TouchPointPanel.this.shoppingSession.setTouchpoint(tp);

    				for (final ShoppingItem item : userShoppingCart){
        				TouchPointPanel.this.shoppingSession.addProduct(item.getProduct(), item.getUnits());
        				// MAP
        				System.out.println("PRODUCTS "+item.getProduct().getName()+" "+item.getUnits());

    				}
    				try {
						TouchPointPanel.this.shoppingSession.purchase();
						SessionUtil.getShoppingCarts().removeShoppingCard(tp);
						this.remove(TouchPointPanel.this.touchPointAlternatives);
						itemPanelCallback.itemPanelClicked();
						this.setResponsePage(this.getPage());

					} catch (final ProductNotInStockException e) {
						final AbstractProduct product = e.getProduct();
						String errorMSG = "Product '"+product.getName()+"' befindet sich nicht im Lager des Touchpoints.";
						if (e instanceof ProductUnitCountToLowInStockException) {
							errorMSG = "Touchpoint verfügt nicht über genügend Einheiten des Produktes '"+product.getName()+"'";
						}
						System.out.println(errorMSG);
						final List<AbstractTouchpoint> lst = TouchPointPanel.this.touchpointCRUD.readTouchpoins(TouchPointPanel.this.stockSystem.getPointsOfSale((IndividualisedProductItem) product,e.getUnits()));
						TouchPointPanel.this.touchPointAlternatives.updateData(lst, product.getName());
						TouchPointPanel.this.touchPointAlternatives.add(new AttributeAppender("style", new Model<String>("height:auto; overflow:visible; margin:15px 0 0 0; padding:10px;")));
						if (lst.size() < 1){
							System.out.println("NICHT DA");
							TouchPointPanel.this.messageModel.setObject(TouchPointPanel.this.htmlPraefix+"Der Artikel "+product.getName()+" ist leider nicht verfügbar!");
							target.add(TouchPointPanel.this.messageLabel);
						}

						target.add(TouchPointPanel.this.touchPointAlternatives);
					} catch (final Exception e) {

						e.printStackTrace();
					}


    			}else{
    				System.out.println("!!!!! USER NOT LOGGED IN !!!!!!");    				
    			}
    		}
    	};

    	for (final ShoppingItem item:model){
    		this.priceTotal = this.priceTotal + (item.getUnits() * item.getProduct().getPrice());
    	}
    	this.add(new Label("priceTotal",  new DecimalFormat("0.00").format(this.priceTotal/100.0)));

    	this.add(linkOrder);
        this.add(items);
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}


}
