package org.dieschnittstelle.jee.esa.crm.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.jboss.logging.Logger;

/**
 * a product bundle on the part of the crm system that tracks the number of
 * units for some erpProductId and also tracks wheher the product is a campaign
 */
@Entity
public class CrmProductBundle implements Serializable {
	
	protected static Logger		logger				= Logger.getLogger(CrmProductBundle.class);
	private static final long	serialVersionUID	= 5027719621777767575L;
	
	private int					erpProductId;
	
	@Id
	@GeneratedValue
	private final int			id					= -1;
	
	private boolean				isCampaign;
	
	private int					units;
	
	public CrmProductBundle() {
		logger.info("<constructor>");
	}
	
	public CrmProductBundle(final int erpProductId, final int units) {
		this(erpProductId, units, false);
	}
	
	public CrmProductBundle(final int erpProductId, final int units, final boolean isCampaign) {
		this.erpProductId = erpProductId;
		this.units = units;
		this.isCampaign = isCampaign;
	}
	
	public int getErpProductId() {
		return this.erpProductId;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getUnits() {
		return this.units;
	}
	
	public boolean isCampaign() {
		return this.isCampaign;
	}
	
	@PostLoad
	public void onPostLoad() {
		logger.info("@PostLoad: " + this);
	}
	
	/*
	 * lifecycle logging
	 */
	
	@PostPersist
	public void onPostPersist() {
		logger.info("@PostPersist: " + this);
	}
	
	@PostRemove
	public void onPostRemove() {
		logger.info("@PostRemove: " + this);
	}
	
	@PostUpdate
	public void onPostUpdate() {
		logger.info("@PostUpdate: " + this);
	}
	
	@PrePersist
	public void onPrePersist() {
		logger.info("@PrePersist: " + this);
	}
	
	@PreRemove
	public void onPreRemove() {
		logger.info("@PreRemove: " + this);
	}
	
	@PreUpdate
	public void onPreUpdate() {
		logger.info("@PreUpdate: " + this);
	}
	
	@Override
	public String toString() {
		return "{CrmProductBundle " + this.id + " (" + this.erpProductId + ":" + this.units + ")}";
	}
	
}
