package de.metamob;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import de.metamob.ui.*;

public class MyApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return MainPage.class; //return default page
	}

}
