package org.dieschnittstelle.jee.esa.crm.entities;

import java.io.Serializable;
import org.jboss.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

/**
 * a product bundle on the part of the crm system that tracks the number of
 * units for some erpProductId and also tracks wheher the product is a campaign
 */
@Entity
public class CrmProductBundle implements Serializable {

	protected static Logger logger = Logger.getLogger(CrmProductBundle.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 5027719621777767575L;

	@Id
	@GeneratedValue
	private int id = -1;

	private int erpProductId;

	private int units;

	private boolean isCampaign;
	
	public CrmProductBundle() {
		logger.info("<constructor>");
	}
	
	public CrmProductBundle(int erpProductId, int units) {
		this(erpProductId, units, false);
	}

	public CrmProductBundle(int erpProductId, int units, boolean isCampaign) {
		this.erpProductId = erpProductId;
		this.units = units;
		this.isCampaign = isCampaign;
	}

	public int getId() {
		return id;
	}

	public int getErpProductId() {
		return erpProductId;
	}

	public int getUnits() {
		return units;
	}

	public boolean isCampaign() {
		return isCampaign;
	}

	public String toString() {
		return "{CrmProductBundle " + this.id + " (" + this.erpProductId + ":"
				+ this.units + ")}";
	}

	/*
	 * lifecycle logging
	 */

	@PostLoad
	public void onPostLoad() {
		logger.info("@PostLoad: " + this);
	}

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

}
