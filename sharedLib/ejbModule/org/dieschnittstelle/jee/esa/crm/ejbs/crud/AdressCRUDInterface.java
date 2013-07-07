package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import org.dieschnittstelle.jee.esa.crm.entities.Address;

public interface AdressCRUDInterface {
	public Address createAddress(Address adress);
	
	public void deleteAddress(Address adress);
	
	public Address readAddress(int id);
	
	public Address updateAddress(Address adress);
}
