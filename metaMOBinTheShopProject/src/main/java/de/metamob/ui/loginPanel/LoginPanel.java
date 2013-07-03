package de.metamob.ui.loginPanel;

import javax.ejb.EJB;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.dieschnittstelle.jee.esa.crm.ejbs.UserCheckLocal;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;

import de.metamob.session.SessionUtil;
import de.metamob.ui.callbacks.IMainPageCallback;

public class LoginPanel extends Panel {

	private IMainPageCallback iMainPageCallback;
	private TextField<String> emailLoginField;
	private PasswordTextField passwordLoginField;	
	private String feedbackText = "";
	//private UserManager userManager = new UserManager();
	
	@EJB(name="LoginSystem")
    private UserCheckLocal userCheck;
	
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
		Form<?> formLogin = new Form<Void>("formLogin");

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
            	Customer user = userCheck.checkLoginData(emailLoginField.getModelObject(), DigestUtils.md5Hex(passwordLoginField.getModelObject()));
            	if (user!=null){
            		SessionUtil.login(user);
            		feedbackText = "PASST";
            		iMainPageCallback.userLoggedIn();
            	}else{
            		feedbackText = "Falscher Benutzer/Passwort";
            	}
            	
            	target.add(feedback);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> formLogin)
            {
                //repaint the feedback panel so errors are shown
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
