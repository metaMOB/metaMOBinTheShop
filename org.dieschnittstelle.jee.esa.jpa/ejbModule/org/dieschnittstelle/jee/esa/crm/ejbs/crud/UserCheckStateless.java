package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.dieschnittstelle.jee.esa.crm.ejbs.UserCheckLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.UserCheckRemote;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;

@Stateless(name="LoginSystem")
public class UserCheckStateless implements UserCheckLocal, UserCheckRemote {
	
	@EJB(beanName="CustomerCRUD")
	CustomerCRUDLocal customerCRUD;
	
	@Override
	public Customer checkLoginData(String email, String passwordHash) {
		return  customerCRUD.readCustomer( email,  passwordHash);
	}
}
