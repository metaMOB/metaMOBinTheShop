package de.metamob.ui.shoppingCartPanel.touchPointAlternatives;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public class MessagePanel extends Panel {

	/**
	 *
	 */
	private static final long	serialVersionUID	= -1184582797482333369L;

	public MessagePanel(final String id, final String name) {
		super(id);
		// TODO Auto-generated constructor stub
		this.add(new Label("name", name));
	}
}
