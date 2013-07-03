package de.metamob.session;

import org.apache.wicket.Session;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;

public class SessionUtil {
	
	public static boolean isLoggedIn(){
		return (Session.get().getAttribute(SessionAttributes.USER.toString())!=null);
	}
	
	public static void login(Customer user){
		Session.get().setAttribute(SessionAttributes.USER.toString(), user);
	}
	
	public static void logout(){
		Session.get().setAttribute(SessionAttributes.USER.toString(), null);
	}
	
	public static Customer getCurrentUser(){
		return (Customer) Session.get().getAttribute(SessionAttributes.USER.toString());
	}
	
}
