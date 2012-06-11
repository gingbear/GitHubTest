package com.gingbear.githubtest;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class CustomC2DM {
	CustomC2DM(){
		
	}
	//	C2DM の登録
	public void send(Context context, Activity activity){
		Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
		registrationIntent.putExtra("app", PendingIntent.getBroadcast(context, 0, new Intent(), 0)); // お決まりのコード
		registrationIntent.putExtra("sender", R.string.googleId);
		activity.startService(registrationIntent);
	}
}
