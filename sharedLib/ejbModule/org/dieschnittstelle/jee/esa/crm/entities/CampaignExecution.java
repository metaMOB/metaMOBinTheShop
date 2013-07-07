package org.dieschnittstelle.jee.esa.crm.entities;

import java.io.Serializable;

/**
 * the execution of a campaign for some erpCampaignId, some touchpoint, some
 * quantity of units, some start date and some duration
 */
public class CampaignExecution implements Serializable {
	
	/**
	 *
	 */
	private static final long			serialVersionUID	= 5077516349500947392L;
	
	private final long					duration;
	
	private final int					erpCampaignId;
	
	private final long					startDate			= System.currentTimeMillis();
	
	private final AbstractTouchpoint	touchpoint;
	
	private final int					units;
	
	/**
	 * track the units that are left for this execution
	 */
	private int							unitsLeft;
	
	public CampaignExecution(final AbstractTouchpoint touchpoint, final int erpCampaignId, final int units, final long duration) {
		this.touchpoint = touchpoint;
		this.erpCampaignId = erpCampaignId;
		this.units = units;
		this.duration = duration;
		this.unitsLeft = units;
	}
	
	public long getDuration() {
		return this.duration;
	}
	
	public int getErpCampaignId() {
		return this.erpCampaignId;
	}
	
	public long getStartDate() {
		return this.startDate;
	}
	
	public AbstractTouchpoint getTouchpoint() {
		return this.touchpoint;
	}
	
	public int getUnits() {
		return this.units;
	}
	
	public int getUnitsLeft() {
		return this.unitsLeft;
	}
	
	/**
	 * check whether the execution is valid
	 */
	public boolean isValid() {
		return (this.unitsLeft > 0) && ((this.duration == -1) || ((System.currentTimeMillis() - this.startDate) <= this.duration));
	}
	
	/**
	 * purchase a given number of units
	 */
	public void purchase(final int units) {
		if ((this.duration != -1) && ((System.currentTimeMillis() - this.startDate) > this.duration)) {
			throw new RuntimeException("campaign for " + this.erpCampaignId + " has expired!");
		}
		
		if (units > this.unitsLeft) {
			throw new RuntimeException("number " + units + " of units to be purchased exceeds the number " + this.unitsLeft + " of units left");
		}
		
		this.unitsLeft -= units;
	}
	
	@Override
	public String toString() {
		return "{CampaignExecution " + this.erpCampaignId + " " + this.touchpoint + " " + this.unitsLeft + "/" + this.units + ", "
				+ (this.duration == -1 ? "<no time limit>" : (System.currentTimeMillis() - this.startDate - this.duration)) + "}";
	}
	
}
