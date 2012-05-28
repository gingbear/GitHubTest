package com.gingbear.githubtest;

import com.gingbear.githubtest.receiver.CustomReceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class GitHubTestActivity extends CustomActivity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent = getIntent();
        if(intent != null){
            String str = intent.getStringExtra("test");
			str += WifiChange.check(intent) + "\n";
			str +=  WifiChange.change(this) + "\n";
//			str += Battery.check(intent) + "\n";
			 Battery battery = Battery.getInstance();
			 str +=  battery.getSb();
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
            setEditText(R.id.editText1,"key",str);
        }

        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(this);
    }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.button1){
			
			NotifyTest.test(getApplicationContext(), this);
		}
	}
    @Override
    protected void onResume() {
        super.onResume();
        Battery.getInstance().registerReceiver(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Battery.getInstance().unregisterReceiver(this);
        
    }
    
}
