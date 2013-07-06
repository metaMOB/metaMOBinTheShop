package de.metamob.ui.shoppingCartPanel.touchPointAlternatives;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GMarker;
import org.wicketstuff.gmap.api.GMarkerOptions;

import de.metamob.session.SessionUtil;

public class TouchPointAlternatives extends Panel {
	
	private GLatLng centerCoordinate;
	private GMap map;
	
	public TouchPointAlternatives(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	public TouchPointAlternatives(String id, List <AbstractTouchpoint> alternativeTP) {
		super(id);
		// TODO Auto-generated constructor stub
		addTouchpointAlternatives(alternativeTP);
	}
	
	private void addTouchpointAlternatives(List <AbstractTouchpoint> alternativeTP){
		if (alternativeTP.size()> 0){		
			map = new GMap("position");
			add(new MessagePanel("message"));
			add(map);
			map.setStreetViewControlEnabled(false);
	        map.setScaleControlEnabled(true);
	        map.setScrollWheelZoomEnabled(true);        
	                        
	        for (AbstractTouchpoint at: alternativeTP){
	        	if (at instanceof StationaryTouchpoint){
	        		StationaryTouchpoint temp = (StationaryTouchpoint) at;
	        		GLatLng tempCoord = new GLatLng(temp.getLocation().getGeoLat(), temp.getLocation().getGeoLong());
	        		map.addOverlay(new GMarker(new GMarkerOptions(map, tempCoord))); 
	        		if (centerCoordinate == null){
	                	map.setCenter(tempCoord);
	                }
	        	}        	
	        }    
		} else {
			add(new Label("position", ""));
			add(new Label("message", ""));
		}
		
        ListView<AbstractTouchpoint> alternativeTouchpoints = new ListView<AbstractTouchpoint>("alternativeTouchpoints", (List<AbstractTouchpoint>) alternativeTP){
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
