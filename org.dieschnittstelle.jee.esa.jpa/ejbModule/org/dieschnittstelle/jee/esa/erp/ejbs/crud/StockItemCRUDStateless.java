package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dieschnittstelle.jee.esa.crm.ejbs.crud.CustomerTransactionCRUDStateless;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;
import org.dieschnittstelle.jee.esa.erp.entities.SortType;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;
import org.jboss.logging.Logger;

/**
 * Session Bean implementation class StockItemCRUDStateless
 */
@Stateless
public class StockItemCRUDStateless implements StockItemCRUDRemote, StockItemCRUDLocal {

	protected static Logger logger = Logger
			.getLogger(CustomerTransactionCRUDStateless.class);

	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;

    /**
     * Default constructor.
     */
    public StockItemCRUDStateless() {

    }

    public StockItem createStockItem(final StockItem stockItem){
    	this.em.persist(stockItem);
    	return stockItem;
    }

    @Override
	public StockItem getStockItem(final AbstractProduct product, final PointOfSale pos){

    	final String queryString = "SELECT a FROM StockItem a WHERE a.pos.id = "+ pos.getId() + " AND a.product.id = " + product.getId();
    	final Query query = this.em.createQuery(queryString);
    	//em.find(arg0, arg1)

    	final List<StockItem> stockItems = query.getResultList();
    	if(stockItems.size()>0){
    		return stockItems.get(0);
    	}
    	return this.createStockItem(new StockItem(product,pos,0));
    }

    @Override
	public Integer getStockItemUnitCount(final IndividualisedProductItem product, final PointOfSale pos){
    	String queryString = "SELECT sum (a.units) FROM StockItem a where a.product.id = " + product.getId();
    	if(pos!=null){
    		queryString += " and a.pos.id = "+ pos.getId();
    	}
    	final Query query=this.em.createQuery(queryString);
    	return ((Number) query.getSingleResult()).intValue();

    	/*Query query=em.createQuery("SELECT COUNT(p.itemName) FROM Product p");
    	int  countResult= ((Number) query.getSingleResult()).intValue();*/
    }

    @Override
	public List<StockItem> readUnitsOnStock(final AbstractProduct product){
    	return this.readUnitsOnStock(product, 0);
    }

    @Override
	public List<StockItem> readUnitsOnStock(final AbstractProduct product, final int minUnits){
    	if (product==null){
    		return null;
    	}
    	String queryString = "SELECT a FROM StockItem a WHERE a.product.id = " + product.getId();
    	queryString += " AND a.units >= "+minUnits;
    	return this.em.createQuery(queryString).getResultList();
    }

    @Override
	public List<StockItem> readUnitsOnStock(final PointOfSale pos, final ProductType productType, final SortType sortType, final int minUnits){
    	String queryString = "SELECT a FROM StockItem a";
    	if((pos!=null)||(productType!=null)){
    		queryString += " WHERE";
    	}
    	if (pos!=null){
    		queryString += " a.pos.id = "+ pos.getId();
    	}

    	if(productType!=null){
    		queryString += (pos!=null)?" AND ":"";
    		queryString += " a.product.productType = org.dieschnittstelle.jee.esa.erp.entities.ProductType." + productType;
    	}

    	if(minUnits>0){
    		queryString += ((pos!=null)||(sortType!=null))?" AND ":"";
    		queryString += " a.units >= " + minUnits;
    	}

    	if (sortType!= null){
	    	switch (sortType) {
			case PRICEUP:
				queryString += " ORDER BY a.product.price ASC";
				break;
			case PRICEDOWN:
				queryString += " ORDER BY a.product.price DESC";
				break;
			case ASC:
				queryString += " ORDER BY a.product.name ASC";
				break;
			case DESC:
				queryString += " ORDER BY a.product.name DESC";
				break;
			default:
				break;
			}
    	}

    	logger.info("JPQL: " + queryString);

    	return this.em.createQuery(queryString).getResultList();
    }

    @Override
	public StockItem updateStockItem(final StockItem stockItem){
    	return this.em.merge(stockItem);
    }
}
