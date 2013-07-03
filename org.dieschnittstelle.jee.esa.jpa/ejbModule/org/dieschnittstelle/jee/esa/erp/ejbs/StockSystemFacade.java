package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.StockItemCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

/**
 * Session Bean implementation class StockSystemFacade
 */
@Singleton
public class StockSystemFacade implements StockSystemRemote, StockSystemLocal {

	@EJB(beanName="StockItemCRUDService")
	private StockItemCRUDLocal stockItemCRUD; 
	
	@EJB(beanName="PointOfSaleCRUDService")
	private PointOfSaleCRUDLocal pointOfSaleCRUD;
	
    /**
     * Default constructor. 
     */
    public StockSystemFacade() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * @see StockSystemRemote#addToStock(IndividualisedProductItem, int, int)
     */
    public void addToStock(IndividualisedProductItem product, int pointOfSaleId, int units) {
    	StockItem stockItem = stockItemCRUD.getStockItem(product, pointOfSaleCRUD.readPointOfSale(pointOfSaleId));
    	stockItem.setUnits(stockItem.getUnits()+units);
    	stockItemCRUD.updateStockItem(stockItem);
    }
    
    /**
     * @throws Exception 
     * @see StockSystemRemote#removeFromStock(IndividualisedProductItem, int, int)
     */
    public void removeFromStock(IndividualisedProductItem product, int pointOfSaleId, int units) throws Exception {
    	StockItem stockItem = stockItemCRUD.getStockItem(product, pointOfSaleCRUD.readPointOfSale(pointOfSaleId));
    	if(stockItem.getUnits()<units){
    		throw new Exception("Not enough units of product ("+product.getId()+") at Point of Sale ("+pointOfSaleId+")");
    	}
    	stockItem.setUnits(stockItem.getUnits()-units);
    	stockItemCRUD.updateStockItem(stockItem);
    }
    
    private List<IndividualisedProductItem> getProducts(PointOfSale pointOfSale) {
    	List<StockItem> stockItems = stockItemCRUD.readUnitsOnStock(pointOfSale);
    	List<IndividualisedProductItem> result = new ArrayList<IndividualisedProductItem>();
    	for (StockItem stockItem:stockItems){
    		result.add((IndividualisedProductItem) stockItem.getProduct());
    	}
		return result;
    }
    
    
    /**
     * @see StockSystemRemote#getProductsOnStock(int)
     */
    public List<IndividualisedProductItem> getProductsOnStock(int pointOfSaleId) {
		return getProducts(pointOfSaleCRUD.readPointOfSale(pointOfSaleId));
    }
    
    /**
     * @see StockSystemRemote#getAllProductsOnStock()
     */
    public List<IndividualisedProductItem> getAllProductsOnStock() {
		return getProducts(null);
    }
    
    
    /**
     * @see StockSystemRemote#getTotalUnitsOnStock(IndividualisedProductItem)
     */
    public int getTotalUnitsOnStock(IndividualisedProductItem product) {
    	return stockItemCRUD.getStockItemUnitCount(product,null);
    }

	/**
     * @see StockSystemRemote#getUnitsOnStock(IndividualisedProductItem, int)
     */
    public int getUnitsOnStock(IndividualisedProductItem product, int pointOfSaleId) {   
		return stockItemCRUD.getStockItemUnitCount(product,pointOfSaleCRUD.readPointOfSale(pointOfSaleId));
    }
    
	/**
     * @see StockSystemRemote#getPointsOfSale(IndividualisedProductItem)
     */
    public List<Integer> getPointsOfSale(IndividualisedProductItem product) {    
    	List<StockItem> stockItems = stockItemCRUD.readUnitsOnStock((AbstractProduct)product);
    	List<Integer> result = new ArrayList<Integer>();
    	for (StockItem stockItem:stockItems){
    		result.add(stockItem.getProduct().getId());
    	}
		return result;
    }
}
