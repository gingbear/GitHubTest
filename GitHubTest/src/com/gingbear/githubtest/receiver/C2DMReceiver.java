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
	public C2DMReceiver() {
		super("");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String str = intent.getStringExtra("message");
        CustomLog.w("message:", str);
        sendMessage(str);
	}

 
    @Override
    public void onUnregistered(Context context) {
        sendMessage("C2DM Unregistered");
    }
	@Override
	public void onError(Context context, String errorId) {
		// TODO Auto-generated method stub
        sendMessage("err:" + errorId);
	}
    private void sendMessage(String str) {
        Message mes = Message.obtain(TopActivity.mH);
        Bundle data = mes.getData();
        data.putBoolean("receivedMessageFlag", true);
        data.putString("receivedMessageString", str);
        TopActivity.mH.sendMessage(mes);
        mes = null;
    }

}
