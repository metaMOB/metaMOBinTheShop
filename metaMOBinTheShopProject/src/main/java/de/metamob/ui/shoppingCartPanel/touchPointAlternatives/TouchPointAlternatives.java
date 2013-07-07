package de.metamob.ui.shoppingCartPanel.touchPointAlternatives;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.wicketstuff.gmap.api.GLatLng;

public class TouchPointAlternatives extends Panel {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 8429011732870872726L;


	private ListView<AbstractTouchpoint> alternativeTouchpoints;


	private GLatLng centerCoordinate;
	private MapPanel map;
	private Label messageDummy;
	private MapPanel positionDummy;

	public TouchPointAlternatives(final String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public TouchPointAlternatives(final String id, final List <AbstractTouchpoint> alternativeTP, final String productName) {
		super(id);
		// TODO Auto-generated constructor stub
		this.positionDummy = new MapPanel ("position");
		this.messageDummy = new Label("message", "");
		this.positionDummy.add(new AttributeAppender("style", new Model<String>("height:0px;")));
		this.messageDummy.add(new AttributeAppender("style", new Model<String>("height:0px;")));

		this.addTouchpointAlternatives(alternativeTP, productName);
	}

	private void addTouchpointAlternatives(final List <AbstractTouchpoint> alternativeTP, final String productName){
		if (alternativeTP.size()> 0){
			this.remove(this.positionDummy);
			this.remove(this.messageDummy);

			this.map = new MapPanel("position");
			this.map.add(new AttributeAppender("style", new Model<String>("height:300px;")));
			this.add(new MessagePanel("message", productName));
			this.add(this.map);

	        for (final AbstractTouchpoint at: alternativeTP){
	        	if (at instanceof StationaryTouchpoint){
	        		final StationaryTouchpoint temp = (StationaryTouchpoint) at;
	        		final GLatLng tempCoord = new GLatLng(temp.getLocation().getGeoLat(), temp.getLocation().getGeoLong());
	        		this.map.addMarker(tempCoord);
	        		if (this.centerCoordinate == null){
	                	this.map.setCenter(tempCoord);
	                }
	        	}
	        }
		} else {
			this.add(this.positionDummy);
			this.add(this.messageDummy);
		}

		if (this.alternativeTouchpoints != null){
			this.remove(this.alternativeTouchpoints);
		}

		this.alternativeTouchpoints = new ListView<AbstractTouchpoint>("alternativeTouchpoints", alternativeTP){
        	@Override
			protected void populateItem(final ListItem<AbstractTouchpoint> entry) {
        		if (entry.getModelObject() instanceof StationaryTouchpoint){
        			final AjaxLink<Void> link = new AjaxLink<Void>("touchpointAlternativeLink"){
    					@Override
    					public void onClick(final AjaxRequestTarget target) {
    		                final StationaryTouchpoint temp = (StationaryTouchpoint) entry.getModelObject();
    		                final GLatLng tempCoord = new GLatLng(temp.getLocation().getGeoLat(), temp.getLocation().getGeoLong());
    		                TouchPointAlternatives.this.map.setCenter(tempCoord);
    		            }
    				};
    				final StationaryTouchpoint tempTP = (StationaryTouchpoint) entry.getModelObject();
    				final Address tempTPAdr = tempTP.getLocation();
    				link.add(new Label("touchpointAlternative", tempTP.getName()));
    				entry.add(new Label("touchpointAlternativeAddress", tempTPAdr.getStreet()+" "+tempTPAdr.getHouseNr()));
    				entry.add(new Label("touchpointAlternativeAddressCity", tempTPAdr.getZipCode()+" "+tempTPAdr.getCity()));
    				entry.add(link);
        		}
        	}
        };

        this.add(this.alternativeTouchpoints);
	}

	public void updateData(final List <AbstractTouchpoint> alternativeTP, final String productName){
		this.addTouchpointAlternatives(alternativeTP, productName);
	}
}
