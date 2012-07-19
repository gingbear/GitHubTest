package com.gingbear.githubtest;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
			return "「使用不可にする」をしている状態";
		case WifiManager.WIFI_STATE_DISABLED://使用不可
			CustomLog.v("WifiState", "WIFI_STATE_DISABLED");
			return "使用不可";
		case WifiManager.WIFI_STATE_ENABLING:// 「使用可能にする」をしている状態
			CustomLog.v("WifiState", "WIFI_STATE_ENABLING");
			return "「使用可能にする」をしている状態";
		case WifiManager.WIFI_STATE_ENABLED:// 使用可能
			CustomLog.v("WifiState", "WIFI_STATE_ENABLED");
			return "使用可能";
		case WifiManager.WIFI_STATE_UNKNOWN:
			CustomLog.v("WifiState", "WIFI_STATE_UNKNOWN");
			return "不明";
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
	
	class OutWifiConfiguration extends WifiConfiguration{
		private OutWifiConfiguration(){
			
		}
		public String toString(){
			return toStringNetWorkId() + "\n"
					+ toStringBSSID() + "\n"
							+ toStringBSSID() + "\n"
									+ toStringSSID() + "\n"
											+ toStringHiddenSSID() + "\n"
													+ toStringNetworkId() + "\n"
															+ toStringPreSharedKey() + "\n"
																	+ toStringPriority() + "\n"
																			+ toStringStatus() + "\n"
																					+ toStringWepKeys() + "\n"
																					+ toStringWepTxKeyIndex() + "\n";
		}
		private String toStringNetWorkId(){
			return String.format("Network ID:%4d", this.networkId);
		}
		
		/**
		 * AP( アクセス・ポイント)のMACアドレス
		 * @return
		 */
		private String toStringBSSID(){
			return String.format("BSSID:%s", this.BSSID);
		}
		
		/**
		 * ネットワーク名
		 * @return
		 */
		private String toStringSSID(){
			return String.format("SSID:%s", this.SSID);
		}
		
		/**
		 * ステルスモード
		 * @return
		 */
		private String toStringHiddenSSID(){
			return String.format("hiddenSSID:%4d", this.hiddenSSID);
		}
		
		/**
		 * ネットワーク番号
		 * @return
		 */
		private String toStringNetworkId(){
			return String.format("Network ID:%4d", this.networkId);
		}
		
		/**
		 * WPA-PSKの鍵
		 * @return
		 */
		private String toStringPreSharedKey(){
			return String.format("preSharedKey:%4d", this.preSharedKey);
		}
		
		/**
		 * 複数のAPが使用できるときの優先度
		 * @return
		 */
		private String toStringPriority(){
			return String.format("priority:%4d", this.priority);
		}
		
		/**
		 * ネットワークの状態
		 * @return
		 */
		private String toStringStatus(){
			String string = "";
			switch(this.status){
			case Status.CURRENT:
				string = "使用中";
				break;
			case Status.DISABLED:
				string = "使用不可";
				break;
			case Status.ENABLED:
				string = "使用可能";
				break;
			}
			return "status:" + string;
		}
		
		/**
		 * WEPキー
		 * @return
		 */
		private String toStringWepKeys(){
			String string ="";
			for(int i=0;i<this.wepKeys.length;++i){
				string += "wepKey " + i + " :" +this.wepKeys[i] + "\n";
			}
			
			return string;
		}
		
		/**
		 * WEPキーのインデックス
		 * @return
		 */
		private String toStringWepTxKeyIndex(){
			return String.format("wepTxKeyIndex:%4d", this.wepTxKeyIndex);
		}
	}
	static public String getConfiguredNetworks(Context context){
		WifiManager manager  = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		// WiFi設定の一覧を取得
		List<WifiConfiguration> cfgList = manager.getConfiguredNetworks();
		if(cfgList != null) {
			String[] nets = new String[cfgList.size()];
			for(int i=0; i<cfgList.size(); i++) {
				nets[i] = ((OutWifiConfiguration) cfgList.get(i)).toString();
			}
			return nets.toString();
		}
		return "";
	}
	static public ListView getListView(Context context){
			ListView lv = new ListView(context);
		WifiManager manager  = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		// WiFi設定の一覧を取得
		List<WifiConfiguration> cfgList = manager.getConfiguredNetworks();
		if(cfgList != null) {
			String[] nets = new String[cfgList.size()];
			for(int i=0; i<cfgList.size(); i++) {
				nets[i] = ((OutWifiConfiguration) cfgList.get(i)).toString();
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, nets);
			lv.setAdapter(adapter);
		}
		return lv;
		
	}
	public void fufu(Context context){
		WifiManager manager  = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if(manager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
			// APをスキャン
			manager.startScan();
			// スキャン結果を取得
			List<ScanResult> apList = manager.getScanResults();
			String[] aps = new String[apList.size()];
			for(int i=0; i<apList.size(); i++) {
				aps[i] = "SSID:" + apList.get(i).SSID + "\n" 
							+ apList.get(i).frequency + "MHz " + apList.get(i).level + "dBm";
			}
//			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, aps);
//			setListAdapter(adapter);
		}
	}
}
