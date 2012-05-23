package com.gingbear.githubtest;

import android.util.Log;

public class CustomLog {

	private static boolean debugLog = false;
	private static boolean logOutput = false;

	public static void setDebugLogging(boolean enable){
		debugLog = enable;
	}
	
	public static void setLogOutput(boolean enable){
		logOutput = enable;
	} 
	public static void i(String tag, String msg){
		if(logOutput) Log.i(tag , msg);
	}
	public static void v(String tag , String msg)	{
		if (debugLog) Log.v(tag, msg);
	}
}
