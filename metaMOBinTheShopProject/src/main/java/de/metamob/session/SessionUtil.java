package de.metamob.session;

import org.apache.wicket.Session;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;

import de.metamob.data.shoppingCart.UserShoppingCarts;

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
	
	public static void setCurrentPage(int currentPage){
		Session.get().setAttribute(SessionAttributes.CURRENTPAGE.toString(), currentPage);
	}
	
	public static int getCurrentPage(){
		return (Integer) Session.get().getAttribute(SessionAttributes.CURRENTPAGE.toString());
	}
		
	public static UIUserConfiguration getUIUserConfiguration(){
		UIUserConfiguration uiuc = (UIUserConfiguration)Session.get().getAttribute(SessionAttributes.UIUSERCONFIG.toString());
		if(uiuc==null){
			return new UIUserConfiguration();
		}
		return(UIUserConfiguration)Session.get().getAttribute(SessionAttributes.UIUSERCONFIG.toString());
	}
	
	public static void setUIUserConfiguration(UIUserConfiguration uiUserConfiguration){
		Session.get().setAttribute(SessionAttributes.UIUSERCONFIG.toString(), uiUserConfiguration);
	}
	
	public static UserShoppingCarts getShoppingCarts(){
		UserShoppingCarts carts = (UserShoppingCarts) Session.get().getAttribute(SessionAttributes.SHOPPINGCARTS.toString());
		if(carts==null){
			carts = new UserShoppingCarts();
			addShoppingCarts(carts);
		}
		return carts;
	}
	
	public static void addShoppingCarts(UserShoppingCarts shoppingCarts){
		Session.get().setAttribute(SessionAttributes.SHOPPINGCARTS.toString(), shoppingCarts);
	}
}
