package com.gingbear.githubtest;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GitHubTestActivity extends CustomActivity implements OnClickListener {
	CustomSensorEvent event ;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent = getIntent();
		CustomLog.i("DEBUG", "action: " + intent.getAction());
        if(intent != null){
            String str = intent.getStringExtra("test");
			str += WifiChange.check(intent) + "\n";
			str +=  WifiChange.change(this) + "\n";
//			str += Battery.check(intent) + "\n";
			str += NotifyTest.check(this);
			 Battery battery = Battery.getInstance();
			 str +=  battery.getSb();
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
            setEditText(R.id.editText1,"key",str);
        }

        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(this);
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(this);
        
        event = new CustomSensorEvent(this);
        TextView textView = (TextView)findViewById(R.id.textView1);
        event.setTetView(textView);
        CustomLocation location = new CustomLocation();
        location.create(this, location);
        
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
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
		}
	}
    @Override
    protected void onResume() {
        super.onResume();
        event.onResume();
        Battery.getInstance().registerReceiver(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Battery.getInstance().unregisterReceiver(this);
        
    }
    
}
