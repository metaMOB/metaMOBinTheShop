package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.Campaign;
import org.dieschnittstelle.jee.esa.erp.entities.ProductBundle;

/**
 * Session Bean implementation class ProductCRUDStateless
 */
@Stateless
/*@Local(ProductCRUDRemote.class)
@LocalBean*/
public class ProductCRUDStateless implements ProductCRUDRemote {
	
	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public ProductCRUDStateless() {
        
    }

	/**
     * @see ProductCRUDRemote#updateProduct(AbstractProduct)
     */
    public AbstractProduct updateProduct(AbstractProduct product) {
		return em.merge(product);
    }

	/**
     * @see ProductCRUDRemote#createProduct(AbstractProduct)
     */
    public AbstractProduct createProduct(AbstractProduct product) {
    	if (product instanceof Campaign){
    		persistCampainElements((Campaign) product);
    	} 	
    	em.persist(product);
		return product;
    }
    
    public Campaign persistCampainElements(Campaign capn){
    	for (ProductBundle productBundle : capn.getBundles()){
    		em.persist(productBundle);
    	}
    	return capn;
    }

	/**
     * @see ProductCRUDRemote#deleteProduct(int)
     */
    public boolean deleteProduct(int id) {
        em.remove(em.find(AbstractProduct.class,id));
		return true;
    }

	/**
     * @see ProductCRUDRemote#readProduct(int)
     */
    public AbstractProduct readProduct(int id) {
		return em.find(AbstractProduct.class,id);
    }

	/**
     * @see ProductCRUDRemote#readAllProducts()
     */
    public List<AbstractProduct> readAllProducts() {
		return  em.createQuery("SELECT e FROM AbstractProduct e").getResultList();
    }
}
