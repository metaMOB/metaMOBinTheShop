package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.Campaign;
import org.dieschnittstelle.jee.esa.erp.entities.ProductBundle;

/**
 * Session Bean implementation class ProductCRUDStateless
 */
@Stateless(name="ProductCRUD")
public class ProductCRUDStateless implements ProductCRUDRemote, ProductCRUDLocal {

	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;

    /**
     * Default constructor.
     */
    public ProductCRUDStateless() {

    }

	/**
     * @see ProductCRUDRemote#createProduct(AbstractProduct)
     */
    @Override
	public AbstractProduct createProduct(final AbstractProduct product) {
    	if (product instanceof Campaign){
    		this.persistCampainElements((Campaign) product);
    	}
    	this.em.persist(product);
		return product;
    }

	/**
     * @see ProductCRUDRemote#deleteProduct(int)
     */
    @Override
	public boolean deleteProduct(final int id) {
        this.em.remove(this.em.find(AbstractProduct.class,id));
		return true;
    }

    public Campaign persistCampainElements(final Campaign capn){
    	for (final ProductBundle productBundle : capn.getBundles()){
    		this.em.persist(productBundle);
    	}
    	return capn;
    }

	/**
     * @see ProductCRUDRemote#readAllProducts()
     */
    @Override
	public List<AbstractProduct> readAllProducts() {
		return  this.em.createQuery("SELECT e FROM AbstractProduct e").getResultList();
    }

	/**
     * @see ProductCRUDRemote#readProduct(int)
     */
    @Override
	public AbstractProduct readProduct(final int id) {
		return this.em.find(AbstractProduct.class,id);
    }

	/**
     * @see ProductCRUDRemote#updateProduct(AbstractProduct)
     */
    @Override
	public AbstractProduct updateProduct(final AbstractProduct product) {
		return this.em.merge(product);
    }
}
