package de.metamob.ui.shoppingCartPanel.touchPointAlternatives;

import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GMarker;
import org.wicketstuff.gmap.api.GMarkerOptions;

public class MapPanel extends Panel {

	/**
	 *
	 */
	private static final long	serialVersionUID	= -3698063182194731796L;
	private GMap map;

	public MapPanel(final String id) {
		super(id);
		// TODO Auto-generated constructor stub
		this.addMapModule();
	}

	private void addMapModule(){
		this.map = new GMap("mapposition");
		this.add(this.map);
		this.map.setStreetViewControlEnabled(false);
        this.map.setScaleControlEnabled(true);
        this.map.setScrollWheelZoomEnabled(true);
	}

	public void addMarker(final GLatLng coords){
		this.map.addOverlay(new GMarker(new GMarkerOptions(this.map, coords)));
	}

	public void setCenter(final GLatLng coords){
		this.map.setCenter(coords);
	}

}
