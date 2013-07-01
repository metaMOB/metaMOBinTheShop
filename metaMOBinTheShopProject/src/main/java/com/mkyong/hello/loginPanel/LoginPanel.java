package com.mkyong.hello.loginPanel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.mkyong.hello.Item;
import com.mkyong.hello.callbacks.IMainPageCallback;

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
