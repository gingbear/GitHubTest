package com.gingbear.githubtest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import com.gingbear.githubtest.receiver.C2DMReceiver;
import com.google.android.c2dm.C2DMessaging;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.UUID;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

public class CustomActivity extends Activity  {
    public Handler mH;
	//--------------------------------------------------------------------------------
	// その他
	//--------------------------------------------------------------------------------
	public static final String LOG_TAG = "CustomActivity";
//	public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        
//	}
	/**
	 * C2DMを使う場合に使う。
	 * C2DMReceiverに表示画面の情報を渡して，受信したら表示が更新されるようにする。
	 * @param textView
	 */
	public void setC2DM(TextView textView){
		
		this.startC2DM();
		
        textView.setText(C2DMessaging.getRegistrationId(CustomActivity.this));
       final TextView  test = textView;
        mH = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.getData().getBoolean("receivedMessageFlag")) {
                    String message = msg.getData().getString("receivedMessageString");
                   
                    CustomToast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                   
                    test.setText(message);
                }
            }
        };
	}
	public void startC2DM(){
		C2DMReceiver.setParam(CustomActivity.this, getString(R.string.googleId));
        C2DMessaging.register(CustomActivity.this, this.getAccount(CustomActivity.this, 1).name);
        //       C2DMessaging.register(this, "この端末利用者のGoogleアカウントID");
	}
	public void stopC2DM(){
		C2DMessaging.unregister(CustomActivity.this);
	}
	/**
	 * xmlで設定されたButtonにTextとClickをセット
	 * @param buttonId
	 * @param text
	 * @param onClickListener
	 */
	private void setButton(int buttonId, String text,  OnClickListener onClickListener){
        Button btn = (Button)findViewById(buttonId);
        btn.setText(text);
        btn.setOnClickListener(onClickListener);
	}
	/**
	 * textを取得するときに，プレファレンスに保存しておく
	 * @param editTextId
	 * @param key
	 * @return
	 */
	private String getEditText(int editTextId, String key){
		EditText editText = (EditText)findViewById(editTextId);
		String text =  editText.getText().toString();
		SharedPreferences pref = getSharedPreferences("pref",MODE_WORLD_READABLE|MODE_WORLD_WRITEABLE);
		Editor e = pref.edit();
		e.putString(key, text);
		e.commit();
		return text;
	}
	/**
	 * プレファレンスにkeyのデータがある場合はそれを使う。
	 * 無い場合は，与えられた文字列を使う
	 * @param editTextId
	 * @param key
	 * @param initText
	 */
	 void setEditText(int editTextId, String key, String initText){
		SharedPreferences pref =getSharedPreferences("pref",MODE_WORLD_READABLE|MODE_WORLD_WRITEABLE);
		EditText editText = (EditText)findViewById(editTextId);
		editText.setSelected(false);
		editText.setText(pref.getString(key, initText));
	}
	/**
	 * メタデータの取得
	 * @param info
	 * @param key
	 */
	private static String getMetaData(ApplicationInfo info, String key){
		Object value = info.metaData.get(key);
		if(value == null) return "";
		return value.toString();
	}
	
	public String[] getAccounts(Context context) {  
	    ArrayList<String> accountsInfo = new ArrayList<String>();  
	    Account[] accounts = AccountManager.get(context).getAccounts();  
	    for (Account account : accounts) {  
	        String name = account.name;  
	        String type = account.type;  
	        int describeContents = account.describeContents();  
	        int hashCode = account.hashCode();  
	     
	        accountsInfo.add("name = " + name +   
	                         "\ntype = " + type +   
	                         "\ndescribeContents = " + describeContents +   
	                         "\nhashCode = " + hashCode);  
	    }  
	  
	    String[] result = new String[accountsInfo.size()];  
	    accountsInfo.toArray(result);  
	    return result;  
	} 
	private Account getAccount(Context context, int number ){
	    ArrayList<String> accountsInfo = new ArrayList<String>();  
	    Account[] accounts = AccountManager.get(context).getAccounts();  
	    if(accounts.length>0)return accounts[number];
	    else return null;
	}
	
	/**
	 * MACアドレスの取得
	 * 形式aa:bb:cc:dd:ee:ff
	 * @return String
	 */
	protected String getMacAddress(){
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE); 
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		String macAddress = wifiInfo.getMacAddress();
		// 端末IDが取得できなければ発行する。
		if(macAddress == null || macAddress.length() <= 0)
			macAddress = "aa:bb:cc:dd:ee:ff";
		return macAddress;
	}

	protected String getDeviceId(){
		TelephonyManager mTelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		// IMEI
		String imei = mTelephonyMgr.getDeviceId();
		// 端末IDが取得できなければ発行する。
		if(imei == null || imei.length() <= 0)
			imei = UUID.randomUUID().toString();
		return imei;
	}

	/**
	 * AndroidManifest.xmlから
	 * アプリケーションの設定情報を取得
	 */
	public synchronized void loadApplicationInfo(){
		ApplicationInfo info;

		//PackageManagerを取得
		PackageManager manager = getPackageManager();
		if(manager == null){
			CustomLog.e(LOG_TAG, "PackageManager is null.");
			return;
		}
		
		//android:debuggable 属性を取得し、それに応じてデバッグログ出力を制御
		//指定されていない場合、通常はtrueとなり、
		//Android Toolsの signed release build時はfalseとなる。
		try {
			//contextのパッケージ内のアプリケーション設定情報を全て取得
			info = manager.getApplicationInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			CustomLog.e(LOG_TAG, "loadApplicationInfo faild. " + e.getMessage());
			return;
		}
		if(info == null){
			CustomLog.e(LOG_TAG, "ApplicationInfo is null.");
			return;
		}
		CustomLog.v(LOG_TAG, info.permission);
		
		if ((info.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE) {
			CustomLog.setDebugLogging(true);
			CustomLog.setLogOutput(true);
		}
		
		PackageManager pm = getPackageManager();
		PackageInfo activityInfo = null;
		try {
		 activityInfo = pm.getPackageInfo("com.twitter.android", PackageManager.GET_PERMISSIONS);
		} catch (NameNotFoundException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		}
		
		
	}
	
	/**
	 * すべてのpermissionを列挙する
	 */
	public void getPermissions(){
		 PackageManager pm = getPackageManager();
	        List<PackageInfo> ilist =
	            pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);
	        List<String> appPermissions = new ArrayList<String>();
	        for (PackageInfo i: ilist) {
	            String[] perms = i.requestedPermissions;
	            if (perms != null) {
	                for (int j=0; j<perms.length; j++) {
	                    if (!appPermissions.contains(perms[j])) {
	                        appPermissions.add(perms[j]);
	                    } 
	                }
	            } 
	        }
	        
	        List<String> manifestPermissions = new ArrayList<String>();
	        
	        //
	        Field[] fields =
	            Manifest.permission.class.getFields();
	        if (fields != null) {
	            for (Field f: fields) {
	                Object value = null;
	                try {
	                    value = f.get(null);
	                } catch (IllegalArgumentException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                } catch (IllegalAccessException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
	                if (value != null) {
	                    manifestPermissions.add(value.toString());
	                }
	            }
	        }
	        
	        List<String> allPermissions =
	            new ArrayList<String>(manifestPermissions);
	        for (String p: appPermissions) {
	            if (!allPermissions.contains(p)) {
	                allPermissions.add(p);
	            }
	        }

	        // sort
	        String[] appPerms = appPermissions.toArray(new String[0]);
	        String[] manPerms = manifestPermissions.toArray(new String[0]);
	        String[] allPerms = allPermissions.toArray(new String[0]);
	        Arrays.sort(appPerms);
	        Arrays.sort(manPerms);
	        Arrays.sort(allPerms);
	        
	        for(int i=0;i<appPerms.length;++i){
				CustomLog.v(LOG_TAG, appPerms[i]);
	        	
	        }
	}
	public void setNetworkInterface(){
    	Enumeration<NetworkInterface> netIFs;
        try {
            netIFs = NetworkInterface.getNetworkInterfaces();
            while( netIFs.hasMoreElements() ) {
                NetworkInterface netIF = netIFs.nextElement();
                CustomLog.v(LOG_TAG, "Name            : " + netIF.getName());
                CustomLog.v(LOG_TAG, "Display name    : " + netIF.getDisplayName());
//                CustomLog.v(LOG_TAG, "Hardware address: " + String.substring(netIF.getHardwareAddress()));
                Enumeration<InetAddress> ipAddrs = netIF.getInetAddresses();
                while( ipAddrs.hasMoreElements() ) {
                    InetAddress ip = ipAddrs.nextElement();
                    if( ! ip.isLoopbackAddress() ) {
//                        return ip.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
	}
	
/**
 * IPアドレスの取得
 * @return
 */
    public String getIpAddress() {
    	Enumeration<NetworkInterface> netIFs;
        try {
            netIFs = NetworkInterface.getNetworkInterfaces();
            while( netIFs.hasMoreElements() ) {
                NetworkInterface netIF = netIFs.nextElement();
                Enumeration<InetAddress> ipAddrs = netIF.getInetAddresses();
                while( ipAddrs.hasMoreElements() ) {
                    InetAddress ip = ipAddrs.nextElement();
                    if( ! ip.isLoopbackAddress() ) {
                        return ip.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
