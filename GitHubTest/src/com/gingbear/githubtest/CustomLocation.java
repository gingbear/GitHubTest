package com.gingbear.githubtest;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.location.LocationProvider;
import android.os.Bundle;
import android.app.Activity;

public class CustomLocation  implements LocationListener {
	private static CustomLocation instance;
    private LocationManager mLocationManager;
	    CustomLocation(){
	    	
	    }; 
	   public static synchronized CustomLocation getInstance(){
	     if(instance == null){ 
	       instance = new CustomLocation();
//		   mBroadcastReceiver = new BatteryReceiver(instance);
	     }
	     return instance;
	   }
	public void check(Activity activity){
		//LocationManagerの取得
		LocationManager locationManager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
		//GPSから現在地の情報を取得
		Location myLocate = locationManager.getLastKnownLocation("gps");
	}
	public void create(Activity activity, LocationListener listener){

        // LocationManagerオブジェクトの生成
		mLocationManager = (LocationManager) activity.getSystemService(Activity.LOCATION_SERVICE);
 
        // ローケーション取得条件の設定
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setSpeedRequired(false);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
 
        mLocationManager.requestLocationUpdates(1000, 1, criteria, listener, activity.getMainLooper());

        mLocationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
//            LocationManager.NETWORK_PROVIDER,
            0,
            0,
            listener);
	}
	public void onLocationChanged(Location location) {
		//位置情報が変更された場合に呼び出される
       CustomLog.v("----------", "----------");
       CustomLog.v("Latitude（緯度）", String.valueOf(location.getLatitude()));
       CustomLog.v("Longitude（経度）", String.valueOf(location.getLongitude()));
       CustomLog.v("Accuracy（精度）", String.valueOf(location.getAccuracy()));
       CustomLog.v("Altitude（標高）", String.valueOf(location.getAltitude()));
       CustomLog.v("Time", String.valueOf(location.getTime()));
       CustomLog.v("Speed", String.valueOf(location.getSpeed()));
       CustomLog.v("Bearing", String.valueOf(location.getBearing()));
		
	}

	public void onProviderDisabled(String provider) {
		// LocationProviderが有効になった場合に呼び出される
		
	}

	public void onProviderEnabled(String provider) {
		// LocationProviderが無効になった場合に呼び出される
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		//LocationProviderの状態が変更された場合に呼び出される
        switch (status) {
        case LocationProvider.AVAILABLE:
        	CustomLog.v("Status", "AVAILABLE");
            break;
        case LocationProvider.OUT_OF_SERVICE:
        	CustomLog.v("Status", "OUT_OF_SERVICE");
            break;
        case LocationProvider.TEMPORARILY_UNAVAILABLE:
        	CustomLog.v("Status", "TEMPORARILY_UNAVAILABLE");
            break;
        }
		
	}
}
