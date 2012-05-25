package com.gingbear.githubtest.receiver;

import android.content.Context;
import android.content.Intent;

import com.gingbear.githubtest.Battery;
import com.gingbear.githubtest.CustomLog;

public class BatteryReceiver  extends CustomReceiver {
	private Battery battery;
	public BatteryReceiver(Battery battery){
		super();
		this.battery = battery;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		CustomLog.i("DEBUG", "---------- enter ----------");
		CustomLog.i("DEBUG", "action: " + intent.getAction());
		String action = intent.getAction();
		 if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
//			 Battery battery = Battery.getInstance();
			 battery.checkBattery(context, intent);
		 }
	}
}
