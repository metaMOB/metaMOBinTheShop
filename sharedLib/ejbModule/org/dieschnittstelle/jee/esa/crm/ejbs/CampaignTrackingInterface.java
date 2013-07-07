package org.dieschnittstelle.jee.esa.crm.ejbs;

import java.util.List;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.CampaignExecution;

public interface CampaignTrackingInterface {
	public void addCampaignExecution(CampaignExecution campaign);
	
	public int existsValidCampaignExecutionAtTouchpoint(int erpProductId, AbstractTouchpoint tp);
	
	public List<CampaignExecution> getAllCampaignExecutions();
	
	public void purchaseCampaignAtTouchpoint(int erpProductId, AbstractTouchpoint tp, int units);
}
