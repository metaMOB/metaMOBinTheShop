package de.metamob.usermanagement;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.commons.codec.digest.DigestUtils;

public class UserManager extends WebPage{
//Test
	private String name;
	private String role;
	private String hashedPassword;
	private String username;
	
	public UserManager() {
		Session session = Session.get();
		session.setAttribute("username", "NONE");
		session.setAttribute("name", "NONE");
		session.setAttribute("role", "NONE");
	}
	
	public boolean logIn (String username, String password){
		// md5-Hash aus Password
		hashedPassword = DigestUtils.md5Hex(password);
		
		// READOUT
		String hashedPasswordStored = hashedPassword;
		name = "Herr Lohse";
		username = "lohse";
		role = "CUSTOMER";
		
		if (hashedPasswordStored.equals(hashedPassword)){
			System.out.println("USERMANAGER: LOGIN PASSED");
			Session.get().setAttribute("username", username);
			Session.get().setAttribute("name", name);
			Session.get().setAttribute("role", role);
			return true;
		}
		System.out.println("USERMANAGER: LOGIN FAILED");
		return false;
	}
	
	public void logOut (){
		System.out.println("USERMANAGER: LOGOUT");
		Session.get().setAttribute("username", "NONE");
		Session.get().setAttribute("name", "NONE");
		Session.get().setAttribute("role", "NONE");
	}
	
	public boolean hasRole(String role){
		return ((String)Session.get().getAttribute("role")).equals(role);
	}
	
	public boolean isLoggedIn (){
		return !((String)Session.get().getAttribute("role")).equals("NONE");
	}
	
	public String getName(){
		if ( ((String)Session.get().getAttribute("name")).equals("NONE")){
			return "Gast";
		}
		return (String)Session.get().getAttribute("name"); 
	}		
}
