package com.gingbear.githubtest;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class WifiChange {
	public void change(Activity activity){
		Intent intent = activity.getIntent();
		String action = intent.getAction();
		// BroadcastReceiver が Wifi の ON/OFF を検知して起動されたら
		if(action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){
		// Wifi の状態を取得
		if(intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN) == WifiManager.WIFI_STATE_ENABLED){
			/*
			 * 0: WifiManager.WIFI_STATE_DISABLING
			 * 1: WifiManager.WIFI_STATE_DISABLED
			 * 2: WifiManager.WIFI_STATE_ENABLING
			 * 3: WifiManager.WIFI_STATE_ENABLED
			 * 4: WifiManager.WIFI_STATE_UNKNOWN
			 */

//			WifiManager wifi = (WifiManager)getSystemService(WIFI_SERVICE);
//			// Wifi が ON になったら OFF
//			wifi.setWifiEnabled(false);

			// 任意のタイミングで ON/OFF の状態を取得したい場合 wifi.isWifiEnabled(); で取得する
		}
		}
		// BroadcastReceiver がネットワークへの接続を検知して起動されたら
		if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
			ConnectivityManager cm = (ConnectivityManager)activity.getSystemService(Activity.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if((ni!=null) && (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI))){
				// Wifi に接続中！TYPE_MOBILE だったらモバイルネットワークに接続中ということになる
			}else{
//				WifiManager wifi = (WifiManager)getSystemService(WIFI_SERVICE);
//				// Wifi に接続してなかったら Wifi を OFF
//				wifi.setWifiEnabled(false);
			}
		}
	}
}
