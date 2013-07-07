package de.metamob.session;

import org.apache.wicket.Session;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;

import de.metamob.data.shoppingCart.UserShoppingCarts;

public class SessionUtil {

	public static void addShoppingCarts(final UserShoppingCarts shoppingCarts){
		Session.get().setAttribute(SessionAttributes.SHOPPINGCARTS.toString(), shoppingCarts);
	}

	public static int getCurrentPage(){
		return (Integer) Session.get().getAttribute(SessionAttributes.CURRENTPAGE.toString());
	}

	public static Customer getCurrentUser(){
		return (Customer) Session.get().getAttribute(SessionAttributes.USER.toString());
	}

	public static UserShoppingCarts getShoppingCarts(){
		UserShoppingCarts carts = (UserShoppingCarts) Session.get().getAttribute(SessionAttributes.SHOPPINGCARTS.toString());
		if(carts==null){
			carts = new UserShoppingCarts();
			addShoppingCarts(carts);
		}
		return carts;
	}

	public static UIUserConfiguration getUIUserConfiguration(){
		UIUserConfiguration uiuc = (UIUserConfiguration)Session.get().getAttribute(SessionAttributes.UIUSERCONFIG.toString());
		if(uiuc==null){
			uiuc = new UIUserConfiguration();
			setUIUserConfiguration(uiuc);
		}
		return uiuc;
	}

	public static boolean isLoggedIn(){
		return (Session.get().getAttribute(SessionAttributes.USER.toString())!=null);
	}

	public static void login(final Customer user){
		Session.get().setAttribute(SessionAttributes.USER.toString(), user);
	}

	public static void logout(){
		Session.get().setAttribute(SessionAttributes.USER.toString(), null);
	}

	public static void setCurrentPage(final int currentPage){
		Session.get().setAttribute(SessionAttributes.CURRENTPAGE.toString(), currentPage);
	}

	public static void setUIUserConfiguration(final UIUserConfiguration uiUserConfiguration){
		Session.get().setAttribute(SessionAttributes.UIUSERCONFIG.toString(), uiUserConfiguration);
	}
}
