package com.gingbear.githubtest;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;

public class WifiChange {
	static public String check(Intent intent){
		String action = intent.getAction();
		// BroadcastReceiver が Wifi の ON/OFF を検知して起動されたら
//		if(action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){

//			WifiManager wifi = (WifiManager)getSystemService(WIFI_SERVICE);
//			// Wifi が ON になったら OFF
//			wifi.setWifiEnabled(false);

			// 任意のタイミングで ON/OFF の状態を取得したい場合 wifi.isWifiEnabled(); で取得する

			// Wifi の状態を取得
			switch (intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)){
				case WifiManager.WIFI_STATE_DISABLING:
					return "0:WIFI_STATE_DISABLING";
				case WifiManager.WIFI_STATE_DISABLED:
					return "1:WIFI_STATE_DISABLED";
				case WifiManager.WIFI_STATE_ENABLING:
					return "3:WIFI_STATE_ENABLING";
				case WifiManager.WIFI_STATE_ENABLED:
					return "4:WIFI_STATE_ENABLED";
				case WifiManager.WIFI_STATE_UNKNOWN:
					return "5:WIFI_STATE_UNKNOWN";
				default:
					return "wifi error";
			}
//		}
//		return "non";
	}
	static public String change(Activity activity){
		Intent intent = activity.getIntent();
//		String action = intent.getAction();
		// BroadcastReceiver がネットワークへの接続を検知して起動されたら
//		if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
			ConnectivityManager cm = (ConnectivityManager)activity.getSystemService(Activity.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if((ni!=null) && (ni.isConnected())){
				switch (ni.getType() ){
				case ConnectivityManager.TYPE_WIFI:
					return "1:TYPE_WIFI";
				case ConnectivityManager.TYPE_MOBILE:
					return "0:TYPE_MOBILE";
				case ConnectivityManager.TYPE_MOBILE_DUN:
					return "4:TYPE_MOBILE_DUN";
				case ConnectivityManager.TYPE_MOBILE_HIPRI:
					return "5:TYPE_MOBILE_HIPRI";
				case ConnectivityManager.TYPE_MOBILE_MMS:
					return "2:TYPE_MOBILE_MMS";
				case ConnectivityManager.TYPE_MOBILE_SUPL:
					return "3:TYPE_MOBILE_SUPL";
				case ConnectivityManager.TYPE_WIMAX:
					return "6:TYPE_WIMAX";
				default:
					return "connect error";
				}
				// Wifi に接続中！TYPE_MOBILE だったらモバイルネットワークに接続中ということになる
			}else{
//				WifiManager wifi = (WifiManager)getSystemService(WIFI_SERVICE);
//				// Wifi に接続してなかったら Wifi を OFF
//				wifi.setWifiEnabled(false);
				return "not connect";
			}
//		}
//		return "non";
	}
	static public String getMacAddress(Context context){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); 

		WifiInfo wifiInfo = wifiManager.getConnectionInfo();


		String macAddress = wifiInfo.getMacAddress();
		return macAddress;
	}
	static public String getWifiState(Context context){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

		// android.permission.ACCESS_WIFI_STATE
		int wifiState = wifiManager.getWifiState();
		switch (wifiState) {
		case WifiManager.WIFI_STATE_DISABLING:// 「使用不可にする」をしている状態
			CustomLog.v("WifiState", "WIFI_STATE_DISABLING");
		    break;
		case WifiManager.WIFI_STATE_DISABLED://使用不可
			CustomLog.v("WifiState", "WIFI_STATE_DISABLED");
		    break;
		case WifiManager.WIFI_STATE_ENABLING:// 「使用可能にする」をしている状態
			CustomLog.v("WifiState", "WIFI_STATE_ENABLING");
		    break;
		case WifiManager.WIFI_STATE_ENABLED:// 使用可能
			CustomLog.v("WifiState", "WIFI_STATE_ENABLED");
		    break;
		case WifiManager.WIFI_STATE_UNKNOWN:
			CustomLog.v("WifiState", "WIFI_STATE_UNKNOWN");
		    break;
		}
		return "";
	}
//	WifiConfiguration#getメソッドで, 設定情報を取り出す.
//	以下の情報(一部)が取得できる.
//	BSSID	AP( アクセス・ポイント)のMACアドレス
//	SSID	ネットワーク名
//	hiddenSSID	ステルスモード
//	networkId	ネットワーク番号
//	preSharedKey	WPA-PSKの鍵
//	priority	複数のAPが使用できるときの優先度
//	status	ネットワークの状態
//	wepKeys	WEPキー
//	wepTxKeyIndex	WEPキーのインデックス
	static public String getConfiguredNetworks(Context context){
		WifiManager manager  = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		// WiFi設定の一覧を取得
		List<WifiConfiguration> cfgList = manager.getConfiguredNetworks();
		if(cfgList != null) {
			String[] nets = new String[cfgList.size()];
			for(int i=0; i<cfgList.size(); i++) {
				nets[i] = String.format("Network ID:%4d\nSSID:%s", 
													cfgList.get(i).networkId, cfgList.get(i).SSID);
			}
//			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nets);
//			setListAdapter(adapter);
		}
		return "";
	}
}
