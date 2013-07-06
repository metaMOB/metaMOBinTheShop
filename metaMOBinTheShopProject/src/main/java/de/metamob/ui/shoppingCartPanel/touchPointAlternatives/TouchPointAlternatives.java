package de.metamob.ui.shoppingCartPanel.touchPointAlternatives;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GMarker;
import org.wicketstuff.gmap.api.GMarkerOptions;

import de.metamob.session.SessionUtil;

public class TouchPointAlternatives extends Panel {
	
	private GLatLng centerCoordinate;
	
	
	private MapPanel positionDummy;
	private Label messageDummy;
	private ListView<AbstractTouchpoint> alternativeTouchpoints;
	private MapPanel map;
	
	public TouchPointAlternatives(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	public TouchPointAlternatives(String id, List <AbstractTouchpoint> alternativeTP, String productName) {
		super(id);
		// TODO Auto-generated constructor stub
		positionDummy = new MapPanel ("position");
		messageDummy = new Label("message", "");
		positionDummy.add(new AttributeAppender("style", new Model<String>("height:0px;")));
		messageDummy.add(new AttributeAppender("style", new Model<String>("height:0px;")));
		
		addTouchpointAlternatives(alternativeTP, productName);
	}
	
	public void updateData(List <AbstractTouchpoint> alternativeTP, String productName){
		addTouchpointAlternatives(alternativeTP, productName);
	}
	
	private void addTouchpointAlternatives(List <AbstractTouchpoint> alternativeTP, String productName){
		if (alternativeTP.size()> 0){		
			remove(positionDummy);
			remove(messageDummy);
			
			map = new MapPanel("position");
			map.add(new AttributeAppender("style", new Model<String>("height:300px;")));
			add(new MessagePanel("message", productName));
			add(map);
	                        
	        for (AbstractTouchpoint at: alternativeTP){
	        	if (at instanceof StationaryTouchpoint){
	        		StationaryTouchpoint temp = (StationaryTouchpoint) at;
	        		GLatLng tempCoord = new GLatLng(temp.getLocation().getGeoLat(), temp.getLocation().getGeoLong());
	        		map.addMarker(tempCoord); 
	        		if (centerCoordinate == null){
	                	map.setCenter(tempCoord);
	                }
	        	}        	
	        }    
		} else {
			add(positionDummy);
			add(messageDummy);
		}
		
		if (alternativeTouchpoints != null){
			remove(alternativeTouchpoints);
		}
		
		alternativeTouchpoints = new ListView<AbstractTouchpoint>("alternativeTouchpoints", (List<AbstractTouchpoint>) alternativeTP){
        	@Override
			protected void populateItem(final ListItem<AbstractTouchpoint> entry) {
        		if (entry.getModelObject() instanceof StationaryTouchpoint){
        			AjaxLink<Void> link = new AjaxLink<Void>("touchpointAlternativeLink"){
    					@Override
    					public void onClick(AjaxRequestTarget target) {						
    		                System.out.println("TP LINK");    		                
    		                StationaryTouchpoint temp = (StationaryTouchpoint) entry.getModelObject();
    		                GLatLng tempCoord = new GLatLng((double)temp.getLocation().getGeoLat(), (double)temp.getLocation().getGeoLong());
    		                map.setCenter(tempCoord);
    		            }
    				};
    				link.add(new Label("touchpointAlternative", entry.getModelObject().getName()));    				
        			entry.add(link);
        		}
        	}
        };
        
        add(alternativeTouchpoints);       	
	}
}
