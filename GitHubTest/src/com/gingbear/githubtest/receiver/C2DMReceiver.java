package com.gingbear.githubtest.receiver;

import com.gingbear.githubtest.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.gingbear.githubtest.CustomActivity;
import com.gingbear.githubtest.CustomLog;
import com.google.android.c2dm.C2DMBaseReceiver;

public class C2DMReceiver extends C2DMBaseReceiver{
	private CustomActivity TopActivity = null;
	public C2DMReceiver(Context context) {
		super(context.getString(R.string.googleId));
		// TODO Auto-generated constructor stub
	}

    // 登録時の処理
    @Override
    public void onRegistered(
		Context context, String registration_id) {
    	CustomLog.i("C2DMReceiver#onRegistered", registration_id);
    }
	@Override
	protected void onMessage(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String str = intent.getStringExtra("message");
        CustomLog.w("message:", str);
        sendMessage(str);
	}


    // 登録解除時の処理
    @Override
    public void onUnregistered(Context context) {
        sendMessage("C2DM Unregistered");
    }
    // エラー時の処理
	@Override
	public void onError(Context context, String errorId) {
		// TODO Auto-generated method stub
        sendMessage("err:" + errorId);
	}
    // サーバーからメッセージを受け取ったときの処理
    private void sendMessage(String str) {
        Message mes = Message.obtain(TopActivity.mH);
        Bundle data = mes.getData();
        data.putBoolean("receivedMessageFlag", true);
        data.putString("receivedMessageString", str);
        TopActivity.mH.sendMessage(mes);
        mes = null;
    }

}
