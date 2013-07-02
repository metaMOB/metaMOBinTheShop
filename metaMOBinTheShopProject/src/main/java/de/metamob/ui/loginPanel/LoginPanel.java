package de.metamob.ui.loginPanel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.value.ValueMap;


import de.metamob.ui.Item;
import de.metamob.ui.callbacks.IMainPageCallback;
import de.metamob.usermanagement.UserManager;

public class LoginPanel extends Panel {

	private IMainPageCallback iMainPageCallback;
	
	
	private TextField<String> emailLoginField;
	private PasswordTextField passwordLoginField;	
	private String feedbackText = "";
	private UserManager userManager = new UserManager();
	
	public String getFeedbackText() {
		return feedbackText;
	}

	public void setFeedbackText(String feedbackText) {
		this.feedbackText = feedbackText;
	}

	
	
	PropertyModel<String> messageModel = new PropertyModel<String>(this, "feedbackText");

	private Label feedback = new Label("feedback", messageModel);
	
	public LoginPanel(String id, IMainPageCallback mainPageCallback) {
		super(id);
		this.iMainPageCallback = mainPageCallback;
		feedback.setOutputMarkupId(true);
		// TODO Auto-generated constructor stub
		//emailLoginField = new TextField<String>("emailLogin");
		//passwordLoginField = new PasswordTextField<String>("passwordLogin");
	   
		Form<?> formLogin = new Form<Void>("formLogin");
		/*Form<?> formLogin = new Form<Void>("formLogin") {
			@Override
			protected void onSubmit() {
				//get the entered password and pass to next page
				PageParameters pageParameters = new PageParameters();
				pageParameters.add("password", password.getModelObject());
				setResponsePage(SuccessPage.class, pageParameters);
 
			}
		};*/
		
		
        add(feedback);
		
		emailLoginField = new TextField<String>("emailLogin", Model.of(""));
		passwordLoginField = new PasswordTextField("passwordLogin", Model.of(""));
		formLogin.add(emailLoginField);
		formLogin.add(passwordLoginField);
		
		
		formLogin.add(new AjaxButton("submitLogin")
		{
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> formLogin)
            {
            	System.out.println("BLUB");
                // repaint the feedback panel so that it is hidden
                //target.add(feedback);
            	
            	if (!emailLoginField.getModelObject().equals(passwordLoginField.getModelObject())){
            		feedbackText = "Falscher Benutzer/Passwort";
            	} else {
            		
            		if (userManager.logIn("Lohse", "geheim")){
            			feedbackText = "PASST";
            			iMainPageCallback.userLoggedIn();
            			
            		} else {
            			feedbackText = "EXISTIERT NICHT";
            		}
            	}
            	target.add(feedback);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> formLogin)
            {
                // repaint the feedback panel so errors are shown
                //target.add(feedback);
            	System.out.println("LOGIN ERROR: MISSING PASSWORD"+emailLoginField.getModelObject());
            	
            	feedbackText = "Fehlende Eingabe!";           	
            	target.add(feedback);
            }
        });
		add(formLogin);
		
		
	}

	public LoginPanel(String id, IModel<?> model) {
		super(id, model);
		// TODO Auto-generated constructor stub
	}
		
}
