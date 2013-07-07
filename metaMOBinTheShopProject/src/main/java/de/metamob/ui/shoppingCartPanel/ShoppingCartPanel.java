package de.metamob.ui.shoppingCartPanel;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;

import de.metamob.data.shoppingCart.ShoppingItem;
import de.metamob.data.shoppingCart.UserShoppingCarts;
import de.metamob.session.SessionUtil;
import de.metamob.ui.callbacks.IMainPageItemCallback;
import de.metamob.ui.shoppingCartPanel.touchPointPanel.TouchPointPanel;

public class ShoppingCartPanel extends Panel {

	/**
	 *
	 */
	private static final long	serialVersionUID	= -2825573509871724886L;
	private IMainPageItemCallback iMainPageItemCallback;

	public ShoppingCartPanel(final String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public ShoppingCartPanel(final String id, final IMainPageItemCallback itemPanelCallback) {
		super(id);
		this.iMainPageItemCallback = itemPanelCallback;
		// TODO Auto-generated constructor stub
		//addTouchpointPanel();
	}

	private void addTouchpointPanel(){
		final UserShoppingCarts shoppingCarts = SessionUtil.getShoppingCarts();
		System.out.println("TP SIZE!!!!!!!!!!!! " +shoppingCarts.getTouchpoints().size());
		final ListView<AbstractTouchpoint> touchpoints = new ListView<AbstractTouchpoint>("touchpoints", new ArrayList(shoppingCarts.getTouchpoints())){

			@Override
			protected void populateItem(final ListItem<AbstractTouchpoint> entry) {
				// TODO Auto-generated method stub
				//entry.getModelObject()
				System.out.println("TP: "+entry.getModelObject().getId());
				final List<ShoppingItem> test = new ArrayList<ShoppingItem>( shoppingCarts.getShoppingCard(entry.getModelObject()));
				System.out.println("TP SIZE "+test.size());

				for (final ShoppingItem si:test){
					System.out.println("TPIT: "+si.getProduct().getName());
				}
				if (test.size()>0){
				final TouchPointPanel touchPointPanel = new TouchPointPanel("oneTouchpoint", shoppingCarts.getShoppingCard(entry.getModelObject()), entry.getModelObject(), ShoppingCartPanel.this.iMainPageItemCallback);
				System.out.println("GENERATE TOUCHPOINTPANEL BY SHOPPINGCART");
				entry.add(touchPointPanel);
				}
				else {
					final Label touchPointPanel = new Label("oneTouchpoint","");
					entry.add(touchPointPanel);
				}
			}
		};
		if (touchpoints!=null){
        	this.remove(touchpoints);
        }
		this.add(touchpoints);
	}

	@Override
	public void onBeforeRender(){
		super.onBeforeRender();
		this.addTouchpointPanel();
	}
}
