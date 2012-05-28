package com.gingbear.githubtest;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotifyTest {
	 public static void test(Context context, Activity activity){

		NotificationManager mManager = (NotificationManager)activity.getSystemService(Context.NOTIFICATION_SERVICE);
		
		Notification n = new Notification();
		
		n.icon = R.drawable.ic_launcher;
		n.tickerText = "ティッカーテキスト";
		n.number = 2;
		
		try{
			SimpleDateFormat date = new SimpleDateFormat("yy/mm/dd HH:mm");
			n.when =  date.parse("2010/5/20").getTime();
		}catch(Exception e){
			n.when = System.currentTimeMillis();
		}
		
		Intent i = new Intent(activity.getApplicationContext(),GitHubTestActivity.class);
		PendingIntent pend = PendingIntent.getActivity(activity.getApplicationContext(), 0, i, 0);
		
		n.setLatestEventInfo(activity.getApplicationContext(), "タイトル", "テキスト",pend );
		mManager.notify(1,n);
	}
}
