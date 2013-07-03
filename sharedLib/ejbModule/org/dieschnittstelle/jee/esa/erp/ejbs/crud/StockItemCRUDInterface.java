package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

public interface StockItemCRUDInterface {
	 public StockItem updateStockItem(StockItem stockItem);
	 public StockItem getStockItem(AbstractProduct product, PointOfSale pos);
	 public List<StockItem> readUnitsOnStock(PointOfSale pos);
	 public List<StockItem> readUnitsOnStock(AbstractProduct product);
	 public Integer getStockItemUnitCount(IndividualisedProductItem product, PointOfSale pos);
}