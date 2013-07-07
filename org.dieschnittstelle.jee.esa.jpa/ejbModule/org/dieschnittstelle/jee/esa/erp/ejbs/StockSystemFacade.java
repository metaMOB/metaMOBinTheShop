package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.StockItemCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;
import org.dieschnittstelle.jee.esa.erp.entities.SortType;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;
import org.dieschnittstelle.jee.esa.erp.exceptions.ProductUnitCountToLowInStockException;

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
    public void removeFromStock(IndividualisedProductItem product, int pointOfSaleId, int units) throws ProductUnitCountToLowInStockException {
    	StockItem stockItem = stockItemCRUD.getStockItem(product, pointOfSaleCRUD.readPointOfSale(pointOfSaleId));
    	if(stockItem.getUnits()<units){
    		throw new ProductUnitCountToLowInStockException(product, units);
    	}
    	stockItem.setUnits(stockItem.getUnits()-units);
    	stockItemCRUD.updateStockItem(stockItem);
    }
    
    private List<IndividualisedProductItem> getProducts(PointOfSale pointOfSale, ProductType productType, SortType sortType, int minItems) {
    	List<StockItem> stockItems = stockItemCRUD.readUnitsOnStock(pointOfSale, productType, sortType,minItems);
    	List<IndividualisedProductItem> result = new ArrayList<IndividualisedProductItem>();
    	for (StockItem stockItem:stockItems){
    		result.add((IndividualisedProductItem) stockItem.getProduct());
    	}
		return result;
    }
    
    
    /**
     * @see StockSystemRemote#getProductsOnStock(int)
     */
    public List<IndividualisedProductItem> getProductsOnStock(int pointOfSaleId, ProductType productType, SortType sortType, int minItems) {
		return getProducts(pointOfSaleCRUD.readPointOfSale(pointOfSaleId), productType, sortType, minItems);
    }
    
    public List<IndividualisedProductItem> getProductsOnStock(int pointOfSaleId, ProductType productType, SortType sortType) {
		return getProducts(pointOfSaleCRUD.readPointOfSale(pointOfSaleId), productType, sortType, 0);
    }
    
    
    public List<IndividualisedProductItem> getAllProductsOnStock(ProductType productType, SortType sortType) {
		return getProducts(null, productType, sortType, 0);
    }
    
    /**
     * @see StockSystemRemote#getAllProductsOnStock()
     */
    public List<IndividualisedProductItem> getAllProductsOnStock() {
		return getAllProductsOnStock(null,null);
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
    
    public void setUnitsOnStock(IndividualisedProductItem product, int pointOfSaleId, int units) {   
    	StockItem stockItem = stockItemCRUD.getStockItem(product, pointOfSaleCRUD.readPointOfSale(pointOfSaleId));
    	stockItem.setUnits(units);
    	stockItemCRUD.updateStockItem(stockItem);
    }
    
	/**
     * @see StockSystemRemote#getPointsOfSale(IndividualisedProductItem)
     */
    
    public List<Integer> getPointsOfSale(IndividualisedProductItem product, int minUnits) {    
    	List<StockItem> stockItems = stockItemCRUD.readUnitsOnStock((AbstractProduct)product, minUnits);
    	Set<Integer> ids = new HashSet<Integer>();
    	
    	for (StockItem stockItem:stockItems){
    		ids.add(stockItem.getPos().getId());
    	}
    	
		return new ArrayList<Integer>(ids);
    }
    
    public List<Integer> getPointsOfSale(IndividualisedProductItem product) {    
    	return getPointsOfSale(product,0);
    }
}
