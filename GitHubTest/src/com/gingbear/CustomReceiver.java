package com.gingbear;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CustomReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		CustomLog.i("DEBUG", "---------- enter ----------");
		CustomLog.i("DEBUG", "action: " + intent.getAction());
		CharSequence cs = intent.getExtras().getCharSequence(Intent.EXTRA_TEXT);
	      
	}

}
