package de.metamob.session;

import org.apache.wicket.Session;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;

import com.sun.org.apache.xml.internal.serializer.ToUnknownStream;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;

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
	
	public static void setSelectedTouchPoint(AbstractTouchpoint touchpoint){
		Session.get().setAttribute(SessionAttributes.SELECTEDTOUCHPOINT.toString(), touchpoint);
	}
	
	public static AbstractTouchpoint getSelectedTouchPoint(){
		return (AbstractTouchpoint) Session.get().getAttribute(SessionAttributes.SELECTEDTOUCHPOINT.toString());
	}
	
}
