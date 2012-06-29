package com.gingbear.githubtest;

import java.util.ArrayList;

import com.gingbear.githubtest.receiver.C2DMReceiver;
import com.google.android.c2dm.C2DMessaging;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CustomActivity extends Activity  {
    public Handler mH;
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
		
        textView.setText(C2DMessaging.getRegistrationId(this));
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
		C2DMReceiver.setParam(this, getString(R.string.googleId));
        C2DMessaging.register(this, this.getAccount(this, 1).name);
        //       C2DMessaging.register(this, "この端末利用者のGoogleアカウントID");
	}
	public void stopC2DM(){
		C2DMessaging.unregister(this);
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
	
	
	
	
}
