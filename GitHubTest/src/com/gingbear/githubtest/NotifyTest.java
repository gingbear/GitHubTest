package com.gingbear.githubtest;

import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotifyTest {
	private static final int HELLO_ID = 1;

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
		//タイミングを見計らってIntentを発信する
		PendingIntent pend = PendingIntent.getActivity(activity.getApplicationContext(), 0, i, 0);
		
		n.setLatestEventInfo(activity.getApplicationContext(), "タイトル", "テキスト",pend );
		mManager.notify(HELLO_ID,n);
	}
	 
	 public static void cancel(Activity activity){
		 String ns = Context.NOTIFICATION_SERVICE;
		                 NotificationManager mNotificationManager = (NotificationManager) activity.getSystemService(ns);
		                 mNotificationManager.cancel(HELLO_ID);
	 }
	 public static String check(Activity activity){
		 ActivityManager activityManager = (ActivityManager) activity
				                 .getSystemService(Activity.ACTIVITY_SERVICE);
				         List<ActivityManager.RunningServiceInfo> serviceInfos = activityManager
				                 .getRunningServices(Integer.MAX_VALUE);

				         StringBuffer sb = new StringBuffer();
				         for (RunningServiceInfo serviceInfo : serviceInfos) {
				             sb.append(serviceInfo.service.getShortClassName()).append("\n");
				         }
return sb.toString();
	 }
}
