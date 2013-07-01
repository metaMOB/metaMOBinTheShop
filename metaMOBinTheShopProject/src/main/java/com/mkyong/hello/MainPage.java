package com.mkyong.hello;

import java.util.ArrayList;

import java.util.List;

import javax.management.relation.Role;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;

import com.mkyong.hello.callbacks.IMainPageCallback;
import com.mkyong.hello.callbacks.IMainPageItemCallback;
import com.mkyong.hello.loginPanel.LoginPanel;
import com.mkyong.hello.mainPanel.MainPanel;
import com.mkyong.usermanagement.UserManager;

import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;

public class MainPage extends WebPage implements IMainPageCallback { // IMainPageItemCallback
																		// {

	private static final long serialVersionUID = 1L;
	private UserManager userManager = new UserManager();
	private Panel visiblePanel;
	private Panel loginPanel;
	private Panel mainPanel;
	private String mode = "main";
	// test
	
	private Label userNameLabel = new Label("userName", "Gast");

	public MainPage(final PageParameters parameters) {

		add(userNameLabel);
		
		mainPanel = new MainPanel("contentPanel", this);
		loginPanel = new LoginPanel("contentPanel", this);
		loginPanel.setOutputMarkupId(true);
		mainPanel.setOutputMarkupId(true);
		this.setOutputMarkupId(true);

		visiblePanel = mainPanel;
		add(visiblePanel);

		AjaxLink<Void> link = new AjaxLink<Void>("loginLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				System.out.println("Hakllo");
				if (mode.equals("main")) {
					visiblePanel.replaceWith(loginPanel);
					visiblePanel = loginPanel;
					target.add(loginPanel);
					mode = "login";
				} else {
					visiblePanel.replaceWith(mainPanel);
					visiblePanel = mainPanel;
					target.add(mainPanel);
					mode = "main";
				}
			}
		};
		add(link);
	}

	@Override
	public void itemPanelClicked(Item item) {

	}
}