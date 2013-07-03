package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

/**
 * Session Bean implementation class StockItemCRUDStateless
 */
@Stateless
public class StockItemCRUDStateless implements StockItemCRUDRemote, StockItemCRUDLocal {

	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public StockItemCRUDStateless() {
    	
    }
    
    public StockItem createStockItem(StockItem stockItem){
    	em.persist(stockItem);
    	return stockItem;
    }
    
    public StockItem updateStockItem(StockItem stockItem){
    	return em.merge(stockItem);
    }
    
    public List<StockItem> readUnitsOnStock(PointOfSale pos){
    	String queryString = "SELECT a FROM StockItem a";
    	if (pos!=null){
    		queryString += " WHERE a.pos.id = "+ pos.getId();
    	}
    	return em.createQuery(queryString).getResultList();
    }
    
    public List<StockItem> readUnitsOnStock(AbstractProduct product){
    	if (product==null){
    		return null;
    	}
    	String queryString = "SELECT a FROM StockItem a WHERE a.product.id = " + product.getId();
    	return em.createQuery(queryString).getResultList();
    }
    
    public StockItem getStockItem(AbstractProduct product, PointOfSale pos){
    	
    	String queryString = "SELECT a FROM StockItem a WHERE a.pos.id = "+ pos.getId() + " AND a.product.id = " + product.getId();
    	Query query = em.createQuery(queryString);
    	//em.find(arg0, arg1)
    	
    	List<StockItem> stockItems = query.getResultList();
    	if(stockItems.size()>0){
    		return stockItems.get(0);
    	}
    	return createStockItem(new StockItem(product,pos,0));
    }
    
    public Integer getStockItemUnitCount(IndividualisedProductItem product, PointOfSale pos){
    	String queryString = "SELECT sum (a.units) FROM StockItem a where a.product.id = " + product.getId();
    	if(pos!=null){
    		queryString += " and a.pos.id = "+ pos.getId();
    	}
    	Query query=em.createQuery(queryString);
    	return ((Number) query.getSingleResult()).intValue();
    	
    	/*Query query=em.createQuery("SELECT COUNT(p.itemName) FROM Product p");
    	int  countResult= ((Number) query.getSingleResult()).intValue();*/
    }
}
