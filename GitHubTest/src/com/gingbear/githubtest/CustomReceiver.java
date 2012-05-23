package com.gingbear.githubtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class CustomReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		CustomLog.i("DEBUG", "---------- enter ----------");
		CustomLog.i("DEBUG", "action: " + intent.getAction());
		CharSequence cs = intent.getExtras().getCharSequence(Intent.EXTRA_TEXT);
		String action = intent.getAction();
		// Wifi の ON/OFF が切り替えられたら WifiChangeActivity を起動
		if(action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){
//			intent.setClass(context, WifiChangeActivity.class);
//			itnent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(itnent);
			CustomLog.i("DEBUG", "WifiChange: " + WifiChange.check(intent));
		}
		// ネットワーク接続状態が変更されたら
		if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
			NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			// ネットワーク接続
			if(networkInfo.isConnected()){
//				intent.setClass(context, WifiChangeActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(intent);
			// ネットワーク非接続
			}else if(!networkInfo.isConnected()){
			}
		}
	      
	}

}
