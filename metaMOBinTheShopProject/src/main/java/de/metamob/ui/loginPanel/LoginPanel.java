package de.metamob.ui.loginPanel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;


import de.metamob.ui.Item;
import de.metamob.ui.callbacks.IMainPageCallback;

public class LoginPanel extends Panel {

	private IMainPageCallback iMainPageCallback;
	
	public LoginPanel(String id, IMainPageCallback mainPageCallback) {
		super(id);
		// TODO Auto-generated constructor stub
		
		
	}

	public LoginPanel(String id, IModel<?> model) {
		super(id, model);
		// TODO Auto-generated constructor stub
	}
}
