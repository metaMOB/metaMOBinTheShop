package com.mkyong;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import com.mkyong.hello.*;

public class MyApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return MainPage.class; //return default page
	}

}
