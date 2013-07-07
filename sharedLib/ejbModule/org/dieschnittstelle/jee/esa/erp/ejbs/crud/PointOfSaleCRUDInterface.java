package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;

public interface PointOfSaleCRUDInterface {
	public PointOfSale createPointOfSale(PointOfSale pos);
	
	public boolean deletePointOfSale(int posId);
	
	public PointOfSale readPointOfSale(int posId);
}
