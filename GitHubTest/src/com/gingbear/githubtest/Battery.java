package com.gingbear.githubtest;

import com.gingbear.githubtest.receiver.BatteryReceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class Battery extends BatteryData{
	
	   private static Battery instance;
	   private Battery(){}; 
	   public static synchronized Battery getInstance(){
	     if(instance == null){ 
	       instance = new Battery();
	     }
	     return instance;
	   }
	   
	   
	   public void setregisterReceiver(Activity activity){

		   BroadcastReceiver mBroadcastReceiver = new BatteryReceiver(this);
	        IntentFilter filter = new IntentFilter();
	        
	        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
	        activity.registerReceiver(mBroadcastReceiver, filter);
	   }
//	public static String check(Intent intent){
//		int level = intent.getIntExtra("level", 0);
//		return level + "%";
//	}
	
	
	public String checkBattery(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
        	sb  = new StringBuilder();   
             status = intent.getIntExtra("status", 0);
             health = intent.getIntExtra("health", 0);
            present = intent.getBooleanExtra("present", false);
             level = intent.getIntExtra("level", 0);
             scale = intent.getIntExtra("scale", 0);
             icon_small = intent.getIntExtra("icon-small", 0);
             plugged = intent.getIntExtra("plugged", 0);
            voltage = intent.getIntExtra("voltage", 0);
             temperature = intent.getIntExtra("temperature", 0);
            /*String*/ technology = intent.getStringExtra("technology");
            
//            String statusString = "";
            
            switch (status) {
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                statusString = "unknown";
                break;
            case BatteryManager.BATTERY_STATUS_CHARGING:
                statusString = "charging";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                statusString = "discharging";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                statusString = "not charging";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                statusString = "full";
                break;
            }
            
//            String healthString = "";
            
            switch (health) {
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                healthString = "unknown";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                healthString = "good";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                healthString = "overheat";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                healthString = "dead";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                healthString = "voltage";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                healthString = "unspecified failure";
                break;
            }
            
//            String acString = "";
            
            switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                acString = "plugged ac";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                acString = "plugged usb";
                break;
            }    
            sb.append("status:"+statusString+"\n");  
            sb.append("health:"+healthString+"\n");  
            sb.append("present:"+String.valueOf(present)+"\n");
            sb.append("level:"+String.valueOf(level)+"\n");    
            sb.append("scale:"+String.valueOf(scale)+"\n");  
            sb.append("icon_small:"+String.valueOf(icon_small)+"\n");  
            sb.append("plugged:"+acString+"\n");
            sb.append("voltage:"+String.valueOf(voltage)+"\n");
            sb.append("temperature:"+String.valueOf(temperature)+"\n");
            sb.append("technology:"+technology+"\n");
            
            CustomLog.v("status", statusString);
            CustomLog.v("health", healthString);
            CustomLog.v("present", String.valueOf(present));
            CustomLog.v("level", String.valueOf(level));
            CustomLog.v("scale", String.valueOf(scale));
            CustomLog.v("icon_small", String.valueOf(icon_small));
            CustomLog.v("plugged", acString);
            CustomLog.v("voltage", String.valueOf(voltage));
            CustomLog.v("temperature", String.valueOf(temperature));
            CustomLog.v("technology", technology);
        }
        return sb.toString();
    }
    
}
