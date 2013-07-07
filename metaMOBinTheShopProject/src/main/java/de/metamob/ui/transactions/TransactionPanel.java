package de.metamob.ui.transactions;

import java.text.DecimalFormat;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import de.metamob.data.shoppingCart.ShoppingItem;
import de.metamob.data.shoppingCart.UserTransaction;
import de.metamob.ui.callbacks.IMainPageItemCallback;

public class TransactionPanel extends Panel {

	/**
	 *
	 */
	private static final long	serialVersionUID	= -5433685423052201811L;
	private int priceTotal = 0;

	public TransactionPanel(final String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public TransactionPanel(final String id, final IMainPageItemCallback itemPanelCallback, final UserTransaction transaction) {
		super(id);
		// TODO Auto-generated constructor stub
		this.addTransactionModule(transaction);
	}

	public void addTransactionModule(final UserTransaction transaction) {

		// TODO Auto-generated constructor stub
		this.add(new Label("touchpointName", transaction.getTouchpoint().getName()));
		this.add(new Label("orderDate", transaction.getDate()));

		final ListView<ShoppingItem> items = new ListView<ShoppingItem>("items", transaction.getProducts()){

			@Override
			protected void populateItem(final ListItem<ShoppingItem> entry) {
				// TODO Auto-generated method stub

				final ShoppingItem temp = entry.getModelObject();

				entry.add(new Label("itemName", temp.getProduct().getName()));
				entry.add(new Label("itemPrice", new DecimalFormat("0.00").format(temp.getProduct().getPrice()/100.0)));
				entry.add(new Label("itemUnits", temp.getUnits()));
			}
        };


    	for (final ShoppingItem item:transaction.getProducts()){
    		this.priceTotal = this.priceTotal + (item.getUnits() * item.getProduct().getPrice());
    	}
    	this.add(new Label("priceTotal",  new DecimalFormat("0.00").format(this.priceTotal/100.0)));
        this.add(items);
	}

	@Override
	public void onBeforeRender(){
		super.onBeforeRender();

	}
}
