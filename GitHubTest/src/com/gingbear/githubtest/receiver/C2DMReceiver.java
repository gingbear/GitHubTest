package com.gingbear.githubtest.receiver;

import com.gingbear.githubtest.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.gingbear.githubtest.CustomActivity;
import com.gingbear.githubtest.CustomLog;
import com.gingbear.githubtest.GitHubTestActivity;
import com.google.android.c2dm.C2DMBaseReceiver;

public class C2DMReceiver extends C2DMBaseReceiver{
//	private CustomActivity TopActivity = null;
	public C2DMReceiver() {
//		super(context.getString(R.string.googleId));
		super("test@gmail.com");
		// TODO Auto-generated constructor stub
		CustomLog.w("message:", "test-----");
	}

    // 登録時の処理
    @Override
    public void onRegistered(
		Context context, String registration_id) {
    	CustomLog.w("message:", "test1");
    	CustomLog.i("C2DMReceiver#onRegistered", registration_id);
    }
	@Override
	protected void onMessage(Context context, Intent intent) {
		// TODO Auto-generated method stub
		CustomLog.w("message:", "test2");
		String str = intent.getStringExtra("message");
		CustomLog.w("message:", str);
        sendMessage(str);
	}


    // 登録解除時の処理
    @Override
    public void onUnregistered(Context context) {
    	CustomLog.w("message:", "test3");
        sendMessage("C2DM Unregistered");
    }
    // エラー時の処理
	@Override
	public void onError(Context context, String errorId) {
		// TODO Auto-generated method stub
		CustomLog.w("message:", "test4");
        sendMessage("err:" + errorId);
	}
    // サーバーからメッセージを受け取ったときの処理
    private void sendMessage(String str) {
    	CustomLog.w("message:", "test5");
        Message mes = Message.obtain(GitHubTestActivity.mH);
        Bundle data = mes.getData();
        data.putBoolean("receivedMessageFlag", true);
        data.putString("receivedMessageString", str);
        GitHubTestActivity.mH.sendMessage(mes);
        mes = null;
    }

}
