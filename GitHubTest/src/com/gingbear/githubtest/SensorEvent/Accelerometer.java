package com.gingbear.githubtest.SensorEvent;

import java.text.MessageFormat;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

//加速度センサーを利用する実装  
class Accelerometer implements IOrientationSensorListener, SensorEventListener {
	private SensorManager mSensor;
	private String mValue;

	// C'tor
	public Accelerometer(SensorManager sm) {
		mSensor = sm;
		mSensor.registerListener(this,
				sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), // ★
				SensorManager.SENSOR_DELAY_UI);
	}

	// センサーの値が変化した際に呼び出される
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) // ★
		{
			return;
		}

		// 傾き状態を取得
		mValue = MessageFormat.format("x:{0},y:{1},z:{2}", event.values[0],
				event.values[1], event.values[2]);
	}

	public String getValue() {
		return mValue;
	}

	public void onAccuracyChanged(Sensor sensor, int i) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
