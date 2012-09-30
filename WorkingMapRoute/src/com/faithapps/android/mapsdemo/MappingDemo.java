package com.faithapps.android.mapsdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MappingDemo extends Activity implements OnClickListener {
	public double lat;
    public double lon;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping_demo);
        
     // Add Click listeners for all buttons
        View firstButton = findViewById(R.id.geocode_button);
        firstButton.setOnClickListener(this);
        View secondButton = findViewById(R.id.latlong_button);
        secondButton.setOnClickListener(this);
        View thirdButton = findViewById(R.id.presentLocation_button);
        thirdButton.setOnClickListener(this);
        
        View directionButton = findViewById(R.id.getDirections);
        directionButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_mapping_demo, menu);
        return true;
    }
    
    @Override
    public void onClick(View v) {
    	switch(v.getId()){
	        case R.id.geocode_button:
	        	// Following adapted from Conder and Darcey, pp.321 ff.		
	            EditText placeText = (EditText) findViewById(R.id.geocode_input);			
	            String placeName = placeText.getText().toString();
	            // Break from execution if the user has not entered anything in the field
	            if(placeName.compareTo("")==0) break;
	            
	            Intent j = new Intent(this, ShowTheMap.class);
	            ShowTheMap.goToPlace(placeName);
	            startActivity(j);
	        break;
	        
	        case R.id.latlong_button:
	        	EditText latText = (EditText) findViewById(R.id.lat_input);
	            EditText lonText = (EditText) findViewById(R.id.lon_input);	
	            String latString = latText.getText().toString();
	            String lonString = lonText.getText().toString();
	            // Only execute if user has put entries in both lat and long fields.
	            if(latString.compareTo("") != 0 && lonString.compareTo("") != 0){
	                lat = Double.parseDouble(latString);
	                lon = Double.parseDouble(lonString);          
	                Intent k = new Intent(this, ShowTheMap.class);
	                ShowTheMap.putLatLong(lat,lon);
	                startActivity(k);
	            }
	        break;
	        
	        case R.id.presentLocation_button:
	            Log.i("Button","Button 3 pushed");
	            Intent m = new Intent(this, MapMe.class);
	            startActivity(m);
	        break;
	        
	        case R.id.getDirections:
	        	Log.i("Button","Directions button pushed");
	        
	            Intent d = new Intent(this, ShowTheMap.class);
	            startActivity(d);
    	}
    }
}
