package com.mkyong.hello.itemPanel;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.mkyong.hello.Item;
import com.mkyong.hello.callbacks.IMainPageItemCallback;

public class ItemPanel extends Panel {
	
	private IMainPageItemCallback iMainPageItemCallback;

	public ItemPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public ItemPanel(String id, IModel<List<Item>> model, IMainPageItemCallback itemPanelCallback) {
		super(id, model);
		this.iMainPageItemCallback = itemPanelCallback;
		// TODO Auto-generated constructor stub
		
		ListView<Item> items = new ListView<Item>("items", (IModel<List<Item>>) model){

			@Override
			protected void populateItem(final ListItem<Item> entry) {
				// TODO Auto-generated method stub
				AjaxLink<Void> link = new AjaxLink<Void>("itemLink"){
					@Override
					public void onClick(AjaxRequestTarget target) {
						Item temp = (Item) entry.getModel().getObject();
						iMainPageItemCallback.itemPanelClicked (temp);
		                System.out.println("JUP "+ temp.getName());
		            }
				};
				Item temp = (Item) entry.getModel().getObject();
				entry.add(link);
				entry.add(new Label("itemName", temp.getName()));
				entry.add(new Label("itemDescription", temp.getDescription()));
				entry.add(new Label("itemPrice", temp.getPrice()));
				entry.add(new Image("itemImage", temp.getImage()));
			}
        	
        };
        add(items);
	}
}
