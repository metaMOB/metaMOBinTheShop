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

	/**
	 *
	 */
	private static final long	serialVersionUID	= 4319630302908198354L;
	private TextField<String> emailLoginField;
	private final Label feedback = new Label("feedback", this.messageModel);
	private String feedbackText = "";
	private IMainPageCallback iMainPageCallback;

	PropertyModel<String> messageModel = new PropertyModel<String>(this, "feedbackText");

	private PasswordTextField passwordLoginField;

	@EJB(name="LoginSystem")
    private UserCheckLocal userCheck;

	public LoginPanel(final String id, final IMainPageCallback mainPageCallback) {
		super(id);
		this.iMainPageCallback = mainPageCallback;
		this.feedback.setOutputMarkupId(true);
		final Form<?> formLogin = new Form<Void>("formLogin");

        this.add(this.feedback);

		this.emailLoginField = new TextField<String>("emailLogin", Model.of(""));
		this.passwordLoginField = new PasswordTextField("passwordLogin", Model.of(""));
		formLogin.add(this.emailLoginField);
		formLogin.add(this.passwordLoginField);


		formLogin.add(new AjaxButton("submitLogin")
		{
            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> formLogin)
            {
                System.out.println("LOGIN ERROR: MISSING PASSWORD"+LoginPanel.this.emailLoginField.getModelObject());

            	LoginPanel.this.feedbackText = "Fehlende Eingabe!";
            	target.add(LoginPanel.this.feedback);
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> formLogin)
            {
            	final Customer user = LoginPanel.this.userCheck.checkLoginData(LoginPanel.this.emailLoginField.getModelObject(), DigestUtils.md5Hex(LoginPanel.this.passwordLoginField.getModelObject()));
            	if (user!=null){
            		SessionUtil.login(user);
            		LoginPanel.this.feedbackText = "PASST";
            		LoginPanel.this.iMainPageCallback.userLoggedIn();
            	}else{
            		LoginPanel.this.feedbackText = "Falscher Benutzer/Passwort";
            	}

            	target.add(LoginPanel.this.feedback);
            }
        });
		this.add(formLogin);
	}

	public LoginPanel(final String id, final IModel<?> model) {
		super(id, model);
		// TODO Auto-generated constructor stub
	}

	public String getFeedbackText() {
		return this.feedbackText;
	}

	public void setFeedbackText(final String feedbackText) {
		this.feedbackText = feedbackText;
	}

}
