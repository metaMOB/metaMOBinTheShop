package org.dieschnittstelle.jee.esa.crm.ejbs;

import org.dieschnittstelle.jee.esa.crm.entities.Customer;

public interface UserCheckInterface {
	Customer checkLoginData(String email, String passwordHash);
}
