package org.dieschnittstelle.jee.esa.crm.ejbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.CampaignExecution;
import org.jboss.logging.Logger;

/**
 * tracks the execution of a compaign
 */
@Singleton
@Startup
@javax.ejb.ConcurrencyManagement(javax.ejb.ConcurrencyManagementType.CONTAINER)
public class CampaignTrackingSingleton implements CampaignTrackingRemote, CampaignTrackingLocal  {

	protected static Logger logger = Logger.getLogger(CampaignTrackingSingleton.class);

	/**
	 * a map that associates touchpoint ids with campaign ids (we assume that
	 * for each touchpoint id there may only exist a single campaign execution
	 * for a given campaign id)
	 */
	private final Map<Integer, Map<Integer, CampaignExecution>> campaignExecutionsAtTouchpoint = new HashMap<Integer, Map<Integer, CampaignExecution>>();

	public CampaignTrackingSingleton() {
		logger.info("<constructor>: " + this);
	}

	/**
	 * add a campaign execution
	 */
	@Override
	@javax.ejb.Lock(javax.ejb.LockType.WRITE)
	public void addCampaignExecution(final CampaignExecution campaign) {
		logger.info("addCampaignExecution(): " + campaign);

		if (!this.campaignExecutionsAtTouchpoint.containsKey(campaign
				.getTouchpoint().getId())) {
			this.campaignExecutionsAtTouchpoint.put(campaign.getTouchpoint()
					.getId(), new HashMap<Integer, CampaignExecution>());
		}
		this.campaignExecutionsAtTouchpoint.get(
				campaign.getTouchpoint().getId()).put(
				campaign.getErpCampaignId(), campaign);
	}

	@PreDestroy
	public void ende() {
		logger.info("@PreDestroy");
	}

	/**
	 * check whether for some product id there exists a campaign at some
	 * touchpoint and return its available units
	 */
	@Override
	@javax.ejb.Lock(javax.ejb.LockType.READ)
	@javax.ejb.AccessTimeout(value=5,unit=java.util.concurrent.TimeUnit.SECONDS)
	public int existsValidCampaignExecutionAtTouchpoint(final int erpProductId,
			final AbstractTouchpoint tp) {
		logger.info("existsValidCampaignExecutionAtTouchpoint(): " + erpProductId + "@" + tp);

		final Map<Integer, CampaignExecution> campaignExecutions = this.campaignExecutionsAtTouchpoint
				.get(tp.getId());
		if (campaignExecutions == null) {
			logger.warn("no CampaignExecution found for touchpoint " + tp + " in " + this.campaignExecutionsAtTouchpoint);
			return 0;
		}
		final CampaignExecution ce = campaignExecutions.get(erpProductId);
		if (ce == null) {
			logger.warn("no CampaignExecution found for product id " + erpProductId + " in " + this.campaignExecutionsAtTouchpoint);
			return 0;
		}
		if (!ce.isValid()) {
			logger.warn("CampaignExecution " + ce + " is not valid!");
			return 0;
		}

		return ce.getUnitsLeft();
	}

	@Override
	public List<CampaignExecution> getAllCampaignExecutions() {
		final List<CampaignExecution> campaigns = new ArrayList<CampaignExecution>();
		for (final int tpid : this.campaignExecutionsAtTouchpoint.keySet()) {
			for (final int cpid : this.campaignExecutionsAtTouchpoint.get(tpid).keySet()) {
				campaigns.add(this.campaignExecutionsAtTouchpoint.get(tpid).get(cpid));
			}
		}

		return campaigns;
	}


	/**
	 * purchase some units of some campaign at some touchpoint
	 */
	@Override
	@javax.ejb.Lock(javax.ejb.LockType.WRITE)
	public void purchaseCampaignAtTouchpoint(final int erpProductId,
			final AbstractTouchpoint tp, final int units) {
		logger.info("purchaseCampaignAtTouchpoint(): " + erpProductId + "@" + tp + ":" + units);

		this.campaignExecutionsAtTouchpoint.get(tp.getId()).get(erpProductId)
				.purchase(units);
	}

	@PostConstruct
	public void start() {
		logger.info("@PostConstruct");
	}


}
