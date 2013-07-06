package de.metamob.ui.shoppingCartPanel.touchPointAlternatives;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class MessagePanel extends Panel {

	public MessagePanel(String id, String name) {
		super(id);
		// TODO Auto-generated constructor stub
		add(new Label("name", name));
	}
}
