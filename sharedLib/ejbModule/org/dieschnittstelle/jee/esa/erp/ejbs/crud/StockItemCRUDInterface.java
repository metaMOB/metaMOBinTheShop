package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;
import org.dieschnittstelle.jee.esa.erp.entities.SortType;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

public interface StockItemCRUDInterface {
	public StockItem getStockItem(AbstractProduct product, PointOfSale pos);
	
	public Integer getStockItemUnitCount(IndividualisedProductItem product, PointOfSale pos);
	
	public List<StockItem> readUnitsOnStock(AbstractProduct product);
	
	public List<StockItem> readUnitsOnStock(AbstractProduct product, int minUnits);
	
	public List<StockItem> readUnitsOnStock(PointOfSale pos, ProductType productType, SortType sortType, int minUnits);
	
	public StockItem updateStockItem(StockItem stockItem);
}
