package com.gingbear.githubtest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class ServiceTest extends Service  {
    private final String BUTTON_CLICK_ACTION = "BUTTON_CLICK_ACTION";
 
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
 
 
        // ボタンが押された時に発行されたインテントの場合は文字を変更する
        if (BUTTON_CLICK_ACTION.equals(intent.getAction())) {
            Toast.makeText(this, "Service Test", Toast.LENGTH_LONG).show();
        }
 
    }
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service has been terminated.", Toast.LENGTH_SHORT).show();
    }
}
