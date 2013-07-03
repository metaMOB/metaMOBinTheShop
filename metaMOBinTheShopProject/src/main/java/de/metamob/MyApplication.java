package de.metamob;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.javaee.injection.JavaEEComponentInjector;

import de.metamob.jndi.Jboss7JndiNamingStrategy;
import de.metamob.ui.*;

public class MyApplication extends WebApplication {
	
	@Override
    public void init() {
        super.init();
        getComponentInstantiationListeners().add(new JavaEEComponentInjector(this, new Jboss7JndiNamingStrategy("org.dieschnittstelle.jee.esa.ejbs")));
	}
	
	@Override
	public Class<? extends Page> getHomePage() {
		return MainPage.class; //return default page
	}

}
