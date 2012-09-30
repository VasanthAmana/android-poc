package com.faithapps.android.mapsdemo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class ShowTheMap extends MapActivity {

	private static double lat = 35.952967;   // Temporary test values for lat/long
    private static double lon = -83.929158 ;
    private int latE6;
    private int lonE6;
    private MapController mapControl;
    private GeoPoint gp;
    private MapView mapView;
	private static String placeName;
        
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // Suppress title bar to give more space
        setContentView(R.layout.show_the_map);
        
        initiateLocation();
        
        // Add map controller with zoom controls
        mapView = (MapView) findViewById(R.id.mv);
        mapView.setSatellite(true);
        mapView.setTraffic(false);
        mapView.setBuiltInZoomControls(true);   // Set android:clickable=true in main.xml
        int maxZoom = mapView.getMaxZoomLevel();
        int initZoom = (int)(0.80*(double)maxZoom);
        mapControl = mapView.getController();
        mapControl.setZoom(initZoom);
        // Convert lat/long in degrees into integers in microdegrees
        latE6 =  (int) (lat*1e6);
        lonE6 = (int) (lon*1e6);
        gp = new GeoPoint(latE6, lonE6);
        mapControl.animateTo(gp);
        
        
        
    }
    
    public void initiateLocation(){
    	if(placeName == null) return;
    	
    	JSONObject locationInfo = getLocationInfo(placeName);
    	try {

            lon = ((JSONArray)locationInfo.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lng");

            lat = ((JSONArray)locationInfo.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lat");

        } catch (Exception e) {
            e.printStackTrace();

        }
    	
    	int numberOptions = 5;
        String [] optionArray = new String[numberOptions];
        Geocoder gcoder = new Geocoder(this);  
          
        // Note that the Geocoder uses synchronous network access, so in a serious application
        // it would be best to put it on a background thread to prevent blocking the main UI if network
        // access is slow. Here we are just giving an example of how to use it so, for simplicity, we
        // don't put it on a separate thread.
                            
        try{
            List<Address> results = gcoder.getFromLocationName(placeName,numberOptions);
            Iterator<Address> locations = results.iterator();
            String raw = "\nRaw String:\n";
            String country;
            int opCount = 0;
            while(locations.hasNext()){
                Address location = locations.next();
                lat = location.getLatitude();
                lon = location.getLongitude();
                country = location.getCountryName();
                if(country == null) {
                    country = "";
                } else {
                    country =  ", "+country;
                }
                raw += location+"\n";
                optionArray[opCount] = location.getAddressLine(0)+", "+location.getAddressLine(1)+country+"\n";
                opCount ++;
            }
            Log.i("Location-List", raw);
            Log.i("Location-List","\nOptions:\n");
            for(int i=0; i<opCount; i++){
                Log.i("Location-List","("+(i+1)+") "+optionArray[i]);
            }
            ShowTheMap.putLatLong(lat,lon);
        } catch (IOException e){
            Log.e("Geocoder", "I/O Failure; is network available?",e);
        }
    }
    
    public static JSONObject getLocationInfo(String address) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

        address = address.replaceAll(" ","%20");    

        HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        stringBuilder = new StringBuilder();


            response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }
    
    public static void putLatLong(double latitude, double longitude){
        lat = latitude;
        lon = longitude;
    }
            
    // Required method since class extends MapActivity
    @Override
    protected boolean isRouteDisplayed() {
        return false;  // Don't display a route
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent e){
        if(keyCode == KeyEvent.KEYCODE_S){
            mapView.setSatellite(!mapView.isSatellite());
            return true;
        } else if(keyCode == KeyEvent.KEYCODE_T){
            mapView.setTraffic(!mapView.isTraffic());
            mapControl.animateTo(gp);  // To ensure change displays immediately
        }
        return(super.onKeyDown(keyCode, e));
    }

	public static void goToPlace(String placeName) {
		ShowTheMap.placeName = placeName;
	}

}
