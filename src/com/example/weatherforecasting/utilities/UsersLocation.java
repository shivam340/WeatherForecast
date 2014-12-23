package com.example.weatherforecasting.utilities;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

public class UsersLocation{


	// flag for GPS status
	private boolean mIsGPSEnabled = false;
	// flag for network status

	private boolean mCanGetLocation = false;
	public static Location sLocation = null; // location
	public static double sLatitude = -1 , sLongitude = -1;

	// The minimum distance to change Updates in meters
	private  final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 0 meters

	// The minimum time between updates in milliseconds
	private  final long MIN_TIME_BW_UPDATES = 0; // 0 minute

	private LocationManager sLocationManager = null;
	private Context mActivity = null;

	//	private LocationListener sLocationListener = null;

	public UsersLocation(Activity mActivity, Context mContext) {

		this.mActivity = mActivity;
		getLocation();
	}



	/**
	 *  this method uses GPS provider to find Users Location.
	 * @return Location object
	 */
	public Location getLocation() {

		try {
			sLocationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);

			// getting  status of GPS
			mIsGPSEnabled = sLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


			if (!mIsGPSEnabled )  {

				App.showSettingsAlert(mActivity);
			} 
			else  {

				this.mCanGetLocation = true;

				// if GPS Enabled get lat/long using GPS Services
				if (mIsGPSEnabled) {

					if (sLocation == null)  {

						sLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) mActivity);
						Log.d("GPS", "GPS Enabled");

						if (sLocationManager != null) {

							sLocation = sLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

							Log.d("Location is ", ""+getLatitude());
							Log.d("Location is ", ""+getLongitude());

							if (sLocation != null) {

								sLatitude = sLocation.getLatitude();
								sLongitude = sLocation.getLongitude();
							}
							else {

								Log.d("Location is ", " null");
							}
						}
					}
				}

			}

		} 
		catch (Exception e) {

			App.Log(App.D, "Error occured while getting users location ",""+e.getMessage());
		}

		return sLocation;
	}



	/**
	 * Stop listening for location change 
	 * */
	public void stopUsingGPS() {

		if (sLocationManager != null){

			sLocationManager.removeUpdates((LocationListener) mActivity);
		}
	}


	/**
	 * Function to get latitude
	 * */
	public double getLatitude() {

		if (sLocation != null) {

			sLatitude = sLocation.getLatitude();
		}

		return sLatitude;
	}


	public double getLongitude() {


		if (sLocation != null) {

			sLongitude = sLocation.getLongitude();
		}

		return sLongitude;
	}

	/**
	 * check GPS is enabled or not
	 * @return boolean
	 * */
	public boolean canGetLocation() {

		return this.mCanGetLocation;
	}

}