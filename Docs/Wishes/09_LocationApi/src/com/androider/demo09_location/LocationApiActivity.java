package com.androider.demo09_location;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class LocationApiActivity extends MapActivity implements LocationListener {
	
	LocationManager locationManager;
	Geocoder geocoder;
	TextView locationText;
	MapView map;	
	MapController mapController;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        locationText = (TextView)this.findViewById(R.id.placeLocation);
        Drawable marker = getResources().getDrawable(R.drawable.starbucks_footer);
        marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
        	   
        map = (MapView)this.findViewById(R.id.placeMapview);
        map.setBuiltInZoomControls(true);
        map.getOverlays().add(new InterestingLocations(marker, 51121209, 7002411));
        map.getOverlays().add(new InterestingLocations(marker, 51117798, 7002642));
        
        //map.getOverlays().add(new InterestingLocations(marker,33489442,-86801863));
       // map.getOverlays().add(new InterestingLocations(marker,33488860, -86800522));

        mapController = map.getController();
        mapController.setZoom(16);
        
        locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);

        geocoder = new Geocoder(this);
        
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
        	Log.d("Demo09_LocationApi", "Avem o locatie deja salvata: " + location.toString());
        	this.onLocationChanged(location);	
        }
    }

    @Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
	}
    
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d("Demo09_LocationApi", "onLocationChanged, noua pozitie: " + location.toString());

		// afiseaza datele despre loca?ie
		String text = String.format("Latitudine:\t %f\nLongitudine:\t %f\nAltitudine:\t %f\nExactitate:\t %f", location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy()) ;
		this.locationText.setText(text);
		
		
		try {
			
			try {
				//foloseste Geocoder pentru a afla adresa
				List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
				this.locationText.append("\nAdresa aproximativa: ");
				for (Address address : addresses) {
					this.locationText.append("\n" + address.getAddressLine(0));
				}
			} catch (Throwable e) {
				Log.e("Demo09_LocationApi", "Nu se poat decoda datele din Geocoder.", e);
			}
			
			int latitude = (int)(location.getLatitude() * 1000000);
			int longitude = (int)(location.getLongitude() * 1000000);

			GeoPoint point = new GeoPoint(latitude,longitude);
			mapController.animateTo(point);
			
		} catch (Throwable e) {
			Log.e("Demo09_LocationApi", "Nu se poat decoda datele din Geocoder.", e);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
	
	class InterestingLocations extends ItemizedOverlay<OverlayItem>{
		  
		  private List<OverlayItem> locations = 
		   new ArrayList<OverlayItem>();
		  private Drawable marker;

		  public InterestingLocations(Drawable defaultMarker, 
		    int LatitudeE6, int LongitudeE6) {
		   super(defaultMarker);
		   // TODO Auto-generated constructor stub
		   this.marker=defaultMarker;
		   // create locations of interest
		   GeoPoint myPlace = new GeoPoint(LatitudeE6,LongitudeE6);
		   locations.add(new OverlayItem(myPlace , 
		     "My Place", "My Place"));
		   populate();
		  }

		  @Override
		  protected OverlayItem createItem(int i) {
		   // TODO Auto-generated method stub
		   return locations.get(i);
		  }

		  @Override
		  public int size() {
		   // TODO Auto-generated method stub
		   return locations.size();
		  }

		  @Override
		  public void draw(Canvas canvas, MapView mapView, 
		    boolean shadow) {
		   // TODO Auto-generated method stub
		   super.draw(canvas, mapView, shadow);
		   
		   boundCenterBottom(marker);
		  }

		@Override
		protected boolean onTap(int arg0) {
			//TODO - starbuck click
			AlertDialog.Builder dialog = new AlertDialog.Builder(LocationApiActivity.this);
			  dialog.setTitle("dummy text");
			  dialog.setMessage("dummy text 2");
			  dialog.show();
			  
			return super.onTap(arg0);
		}
		  
		  
		 }
		

}