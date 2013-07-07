package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.List;

import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;
import org.dieschnittstelle.jee.esa.erp.entities.SortType;
import org.dieschnittstelle.jee.esa.erp.exceptions.ProductUnitCountToLowInStockException;

public interface StockSystemInterface {
	
	/**
	 * adds some units of a product to the stock of a point of sale
	 *
	 * @param product
	 * @param pointOfSaleId
	 * @param units
	 */
	public void addToStock(IndividualisedProductItem product,int pointOfSaleId,int units);

	/**
	 * removes some units of a product from the stock of a point of sale
	 *	
	 * @param product
	 * @param pointOfSaleId
	 * @param units
	 * @throws Exception 
	 */
	public void removeFromStock(IndividualisedProductItem product,int pointOfSaleId,int units) throws ProductUnitCountToLowInStockException;
	
	/**
	 * returns all products on stock of some pointOfSale
	 * 
	 * @param pointOfSaleId
	 * @return
	 */
	public List<IndividualisedProductItem> getProductsOnStock(int pointOfSaleId, ProductType productType, SortType sortType, int minItems);
	public List<IndividualisedProductItem> getProductsOnStock(int pointOfSaleId, ProductType productType, SortType sortType);

	/**
	 * returns all products on stock
	 * 
	 * @return
	 */
	public List<IndividualisedProductItem> getAllProductsOnStock(ProductType productType, SortType sortType);
	
	public List<IndividualisedProductItem> getAllProductsOnStock();

	/**
	 * returns the units on stock for a product at some point of sale
	 * 
	 * @param product
	 * @param pointOfSaleId
	 * @return
	 */
	public int getUnitsOnStock(IndividualisedProductItem product, int pointOfSaleId);
	public void setUnitsOnStock(IndividualisedProductItem product, int pointOfSaleId, int units);

	/**
	 * returns the total number of units on stock for some product
	 * 
	 * @param product
	 * @return
	 */
	public int getTotalUnitsOnStock(IndividualisedProductItem product);
	
	/**
	 * returns the points of sale where some product is available
	 * 
	 * @param product
	 * @return
	 */
	public List<Integer> getPointsOfSale(IndividualisedProductItem product);
	public List<Integer> getPointsOfSale(IndividualisedProductItem product, int minUnits);

}
