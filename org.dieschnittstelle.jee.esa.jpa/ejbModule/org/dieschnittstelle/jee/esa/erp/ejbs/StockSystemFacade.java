package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.StockItemCRUDLocal;
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

	@EJB(beanName="PointOfSaleCRUDService")
	private PointOfSaleCRUDLocal pointOfSaleCRUD;

	@EJB(beanName="StockItemCRUDService")
	private StockItemCRUDLocal stockItemCRUD;

    /**
     * Default constructor.
     */
    public StockSystemFacade() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see StockSystemRemote#addToStock(IndividualisedProductItem, int, int)
     */
    @Override
	public void addToStock(final IndividualisedProductItem product, final int pointOfSaleId, final int units) {
    	final StockItem stockItem = this.stockItemCRUD.getStockItem(product, this.pointOfSaleCRUD.readPointOfSale(pointOfSaleId));
    	stockItem.setUnits(stockItem.getUnits()+units);
    	this.stockItemCRUD.updateStockItem(stockItem);
    }

    /**
     * @see StockSystemRemote#getAllProductsOnStock()
     */
    @Override
	public List<IndividualisedProductItem> getAllProductsOnStock() {
		return this.getAllProductsOnStock(null,null);
    }

    @Override
	public List<IndividualisedProductItem> getAllProductsOnStock(final ProductType productType, final SortType sortType) {
		return this.getProducts(null, productType, sortType, 0);
    }


    @Override
	public List<Integer> getPointsOfSale(final IndividualisedProductItem product) {
    	return this.getPointsOfSale(product,0);
    }

    /**
     * @see StockSystemRemote#getPointsOfSale(IndividualisedProductItem)
     */

    @Override
	public List<Integer> getPointsOfSale(final IndividualisedProductItem product, final int minUnits) {
    	final List<StockItem> stockItems = this.stockItemCRUD.readUnitsOnStock(product, minUnits);
    	final Set<Integer> ids = new HashSet<Integer>();

    	for (final StockItem stockItem:stockItems){
    		ids.add(stockItem.getPos().getId());
    	}

		return new ArrayList<Integer>(ids);
    }


    private List<IndividualisedProductItem> getProducts(final PointOfSale pointOfSale, final ProductType productType, final SortType sortType, final int minItems) {
    	final List<StockItem> stockItems = this.stockItemCRUD.readUnitsOnStock(pointOfSale, productType, sortType,minItems);
    	final List<IndividualisedProductItem> result = new ArrayList<IndividualisedProductItem>();
    	for (final StockItem stockItem:stockItems){
    		result.add((IndividualisedProductItem) stockItem.getProduct());
    	}
		return result;
    }

    @Override
	public List<IndividualisedProductItem> getProductsOnStock(final int pointOfSaleId, final ProductType productType, final SortType sortType) {
		return this.getProducts(this.pointOfSaleCRUD.readPointOfSale(pointOfSaleId), productType, sortType, 0);
    }


    /**
     * @see StockSystemRemote#getProductsOnStock(int)
     */
    @Override
	public List<IndividualisedProductItem> getProductsOnStock(final int pointOfSaleId, final ProductType productType, final SortType sortType, final int minItems) {
		return this.getProducts(this.pointOfSaleCRUD.readPointOfSale(pointOfSaleId), productType, sortType, minItems);
    }

	/**
     * @see StockSystemRemote#getTotalUnitsOnStock(IndividualisedProductItem)
     */
    @Override
	public int getTotalUnitsOnStock(final IndividualisedProductItem product) {
    	return this.stockItemCRUD.getStockItemUnitCount(product,null);
    }

    /**
     * @see StockSystemRemote#getUnitsOnStock(IndividualisedProductItem, int)
     */
    @Override
	public int getUnitsOnStock(final IndividualisedProductItem product, final int pointOfSaleId) {
		return this.stockItemCRUD.getStockItemUnitCount(product,this.pointOfSaleCRUD.readPointOfSale(pointOfSaleId));
    }

	/**
     * @throws Exception
     * @see StockSystemRemote#removeFromStock(IndividualisedProductItem, int, int)
     */
    @Override
	public void removeFromStock(final IndividualisedProductItem product, final int pointOfSaleId, final int units) throws ProductUnitCountToLowInStockException {
    	final StockItem stockItem = this.stockItemCRUD.getStockItem(product, this.pointOfSaleCRUD.readPointOfSale(pointOfSaleId));
    	if(stockItem.getUnits()<units){
    		throw new ProductUnitCountToLowInStockException(product, units);
    	}
    	stockItem.setUnits(stockItem.getUnits()-units);
    	this.stockItemCRUD.updateStockItem(stockItem);
    }

    @Override
	public void setUnitsOnStock(final IndividualisedProductItem product, final int pointOfSaleId, final int units) {
    	final StockItem stockItem = this.stockItemCRUD.getStockItem(product, this.pointOfSaleCRUD.readPointOfSale(pointOfSaleId));
    	stockItem.setUnits(units);
    	this.stockItemCRUD.updateStockItem(stockItem);
    }
}
