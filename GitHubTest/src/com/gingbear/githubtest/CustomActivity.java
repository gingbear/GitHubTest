package com.gingbear.githubtest;

import com.google.android.c2dm.C2DMessaging;

import android.app.Activity;
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
	public static void test(){
		
	}
//	public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        
//	}
	public void setC2DM(TextView textView){

        C2DMessaging.register(this, "この端末利用者のGoogleアカウントID");
 
        textView.setText(C2DMessaging.getRegistrationId(this));
       final TextView  test = textView;
        mH = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.getData().getBoolean("receivedMessageFlag")) {
                    String message = msg.getData().getString("receivedMessageString");
                   
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                   
                    test.setText(message);
                }
            }
        };
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
}
