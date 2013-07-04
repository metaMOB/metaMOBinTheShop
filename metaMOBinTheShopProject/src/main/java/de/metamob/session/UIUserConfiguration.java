package de.metamob.session;

import java.io.Serializable;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;
import org.dieschnittstelle.jee.esa.erp.entities.SortType;

public class UIUserConfiguration implements Serializable{
	private static final long serialVersionUID = -9027476182757928717L;

	private AbstractTouchpoint touchpont = null;
	private ProductType productType = null;
	private SortType sortType = null;
	private Integer itemsPerPage = null;
	
	public AbstractTouchpoint getTouchpont() {
		return touchpont;
	}
	public void setTouchpont(AbstractTouchpoint touchpont) {
		this.touchpont = touchpont;
	}
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public SortType getSortType() {
		return sortType;
	}
	public void setSortType(SortType sortType) {
		this.sortType = sortType;
	}
	public Integer getItemsPerPage() {
		return itemsPerPage;
	}
	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
}
