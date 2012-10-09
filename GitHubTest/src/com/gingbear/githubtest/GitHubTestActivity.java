package com.gingbear.githubtest;


import com.google.android.c2dm.C2DMessaging;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GitHubTestActivity extends CustomActivity implements OnClickListener {
	CustomSensorEvent event ;

    public static Handler mH;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        Intent intent = getIntent();
//		CustomLog.i("DEBUG", "action: " + intent.getAction());
//        if(intent != null){
//            String str = intent.getStringExtra("test");
//			str += WifiChange.check(intent) + "\n";
//			str +=  WifiChange.change(this) + "\n";
////			str += Battery.check(intent) + "\n";
//			str += NotifyTest.check(this);
//			 Battery battery = Battery.getInstance();
//			 str +=  battery.getSb();
//            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
//            setEditText(R.id.editText1,"key",str);
//        }
        CustomLog.w("message1:", "test1");
        Button button = (Button)findViewById(R.id.button1);
        button.setText("ティッカーテキストon");
        button.setOnClickListener(this);
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setText("ティッカーテキストcancel");
        button2.setOnClickListener(this);
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setText("JSON");
        button3.setOnClickListener(this);
        Button button4 = (Button)findViewById(R.id.button4);
        button4.setText("C2DMサーバーへ登録");
        button4.setOnClickListener(this);
        Button button5 = (Button)findViewById(R.id.button5);
        button5.setText("C2DMサーバーへ解除");
        button5.setOnClickListener(this);

  
        
        event = new CustomSensorEvent(this);
        TextView textView = (TextView)findViewById(R.id.textView1);
        event.setTetView(textView);
        CustomLocation location = new CustomLocation();
        location.create(this, location);
        
        setNetworkInterface();
        TextView textViewmacaddress = (TextView)findViewById(R.id.macaddress);
        textViewmacaddress.setText(getMacAddress());
        TextView textViewdeviceid = (TextView)findViewById(R.id.deviceid);
        textViewdeviceid.setText(getDeviceId());
        

        TextView textView1 = (TextView)findViewById(R.id.textView2);
        textView1.setText(WifiChange.getWifiState(this));
//        EditText wifiview = (EditText)findViewById(R.id.editText2);
//        wifiview.setText(WifiChange.getWifiState(this));
        TextView ipview = (TextView)findViewById(R.id.ipview);
        ipview.setText(getIpAddress());
//        loadApplicationInfo();
        getPermissions();
        this.setC2DM((TextView)findViewById(R.id.c2dm));
    }

	@Override
	protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	// Listenerの登録解除
event.onStop();
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.button1){
			
			NotifyTest.test(getApplicationContext(), this);
		} else if(v.getId() == R.id.button2){
			NotifyTest.cancel(GitHubTestActivity.this);
		} else if(v.getId() == R.id.button3){
            String str = (new CustomJson()).getJson();
            CustomToast.makeText(this, str, Toast.LENGTH_LONG).show();
		} else if(v.getId() == R.id.button4){
			// C2DMサーバーへ登録リクエスト
			startC2DM();
	        TextView textView1 = (TextView)findViewById(R.id.c2dm);
	        textView1.setText("C2DMサーバーへ登録リクエスト");
			CustomToast.makeText(this, "C2DMサーバーへ登録リクエスト", Toast.LENGTH_LONG).show();
		} else if(v.getId() == R.id.button5){
			// C2DMサーバーへ解除リクエスト
			stopC2DM();
			CustomToast.makeText(this, "C2DMサーバーへ解除リクエスト", Toast.LENGTH_LONG).show();
			
		}
	}
    @Override
    protected void onResume() {
        super.onResume();
        event.onResume();
//        Battery.getInstance().registerReceiver(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
//        Battery.getInstance().unregisterReceiver(this);
        
    }
    
}



