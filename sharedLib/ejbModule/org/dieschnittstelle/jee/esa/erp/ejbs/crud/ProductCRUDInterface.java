package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

public interface ProductCRUDInterface {
	public AbstractProduct createProduct(AbstractProduct product);
	
	public boolean deleteProduct(int id);
	
	public List<AbstractProduct> readAllProducts();
	
	public AbstractProduct readProduct(int id);
	
	public AbstractProduct updateProduct(AbstractProduct product);
}
