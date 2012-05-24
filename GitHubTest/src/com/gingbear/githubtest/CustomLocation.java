package com.gingbear.githubtest;

import android.content.Context;
import android.location.LocationManager;
import android.location.Location;
import android.app.Activity;

public class CustomLocation {
	public void check(Activity activity){
		//LocationManagerの取得
		LocationManager locationManager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
		//GPSから現在地の情報を取得
		Location myLocate = locationManager.getLastKnownLocation("gps");
	}
}
