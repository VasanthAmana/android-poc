package com.faithapps.android.mapsdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.faithapps.android.directions.GoogleParser;
import com.faithapps.android.directions.Parser;
import com.faithapps.android.directions.Route;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;



public class DirectionMapActivity extends MapActivity {
private MapController mapControl;
private MapView mapView;

	
	private Route directions(final GeoPoint start, final GeoPoint dest) {
	    Parser parser;
	    String jsonURL = "http://maps.google.com/maps/api/directions/json?";
	    final StringBuffer sBuf = new StringBuffer(jsonURL);
	    sBuf.append("origin=");
	    sBuf.append(start.getLatitudeE6()/1E6);
	    sBuf.append(',');
	    sBuf.append(start.getLongitudeE6()/1E6);
	    sBuf.append("&destination=");
	    sBuf.append(dest.getLatitudeE6()/1E6);
	    sBuf.append(',');
	    sBuf.append(dest.getLongitudeE6()/1E6);
	    sBuf.append("&sensor=true&mode=driving");
	    Log.d("DIRECTIONS",sBuf.toString());
	    parser = new GoogleParser(sBuf.toString());
	    Route r =  parser.parse();
	    return r;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		try{
			
		
		final GeoPoint source = new GeoPoint(13066196, 80175195);
    	final GeoPoint destination = new GeoPoint(13086693, 80187334);
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.show_the_map);
        
        final Route route = directions(source, destination);
    	
        mapView = (MapView) findViewById(R.id.mv);
        mapView.setSatellite(false);
        mapView.setTraffic(true);
        mapView.setBuiltInZoomControls(true);   // Set android:clickable=true in main.xml
		
		com.faithapps.android.directions.RouteOverlay routeOverlay = new com.faithapps.android.directions.RouteOverlay(route, Color.BLUE);
		mapView.getOverlays().add(routeOverlay);
		
		mapControl = mapView.getController();
        double fitFactor = 1.002;
		GeoPoint max = route.max();
		GeoPoint min = route.min();
		mapControl.zoomToSpan((int)(Math.abs(max.getLatitudeE6() - min.getLatitudeE6()) * fitFactor), (int)(Math.abs(max.getLongitudeE6() - min.getLongitudeE6())*fitFactor));
		mapControl.animateTo(new GeoPoint( (max.getLatitudeE6() + min.getLatitudeE6())/2, (max.getLongitudeE6() + min.getLongitudeE6())/2 ));
        // Convert lat/long in degrees into integers in microdegrees
		
		}catch(RuntimeException e){
			Log.e("DIRECTIONS", "unexpected",e);
		}
	}
    
   
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
