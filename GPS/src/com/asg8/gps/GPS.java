package com.asg8.gps;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class GPS extends Activity implements LocationListener {
	
	private TextView latituteField;
	private TextView longitudeField;
	private LocationManager locationManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    latituteField = (TextView) findViewById(R.id.txtLatNumber);
	    longitudeField = (TextView) findViewById(R.id.txtLongNumber);

	    // Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
	}

	// Remove the LocationListener updates when Activity is paused */
	@Override
	protected void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
	    int lat = (int) (location.getLatitude());
	    int lng = (int) (location.getLongitude());
	    latituteField.setText(String.valueOf(lat));
	    longitudeField.setText(String.valueOf(lng));
	    
	    if (lat >= 200 && lat <= 300 && lng >= 200 && lng <= 300)
	    {
	    	Toast.makeText(this, "You are in the zone.", Toast.LENGTH_SHORT).show();
	    }
	}

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    // TODO Auto-generated method stub
	  
  }

  @Override
  public void onProviderEnabled(String provider) {
    Toast.makeText(this, "Enabled new provider " + provider,
        Toast.LENGTH_SHORT).show();

  }

  @Override
  public void onProviderDisabled(String provider) {
    Toast.makeText(this, "Disabled provider " + provider,
        Toast.LENGTH_SHORT).show();
  }
}

