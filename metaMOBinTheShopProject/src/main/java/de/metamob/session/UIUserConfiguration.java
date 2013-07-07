package de.metamob.session;

import java.io.Serializable;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;
import org.dieschnittstelle.jee.esa.erp.entities.SortType;

public class UIUserConfiguration implements Serializable{
	private static final long serialVersionUID = -9027476182757928717L;

	private Integer itemsPerPage = null;
	private ProductType productType = null;
	private SortType sortType = null;
	private AbstractTouchpoint touchpont = null;

	public Integer getItemsPerPage() {
		return this.itemsPerPage;
	}
	public ProductType getProductType() {
		return this.productType;
	}
	public SortType getSortType() {
		return this.sortType;
	}
	public AbstractTouchpoint getTouchpont() {
		return this.touchpont;
	}
	public void setItemsPerPage(final Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	public void setProductType(final ProductType productType) {
		this.productType = productType;
	}
	public void setSortType(final SortType sortType) {
		this.sortType = sortType;
	}
	public void setTouchpont(final AbstractTouchpoint touchpont) {
		this.touchpont = touchpont;
	}
}
