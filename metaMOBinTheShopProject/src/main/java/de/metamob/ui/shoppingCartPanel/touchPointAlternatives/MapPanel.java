package de.metamob.ui.shoppingCartPanel.touchPointAlternatives;

import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GMarker;
import org.wicketstuff.gmap.api.GMarkerOptions;

public class MapPanel extends Panel {

	private GMap map;
	
	public MapPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
		addMapModule();
	}
	
	private void addMapModule(){
		map = new GMap("mapposition");		
		add(map);
		map.setStreetViewControlEnabled(false);
        map.setScaleControlEnabled(true);
        map.setScrollWheelZoomEnabled(true);
	}
	
	public void addMarker(GLatLng coords){
		map.addOverlay(new GMarker(new GMarkerOptions(map, coords)));
	}
	
	public void setCenter(GLatLng coords){
		map.setCenter(coords);
	}
	
}
