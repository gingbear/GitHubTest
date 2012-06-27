package com.gingbear.githubtest;

import android.content.Context;
import android.widget.Toast;

public class CustomToast extends Toast {

	private CustomToast() {
		super(null);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public static Toast makeText (Context context, CharSequence text, int duration) {
		return Toast.makeText(context, text + " by GitHubTest", duration);
		
	}

}
