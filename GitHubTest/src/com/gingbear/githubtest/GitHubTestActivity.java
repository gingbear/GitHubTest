package com.gingbear.githubtest;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

public class GitHubTestActivity extends CustomActivity {
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        IntentFilter filter = new IntentFilter();
        
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mBroadcastReceiver, filter);
    }
    @Override
    protected void onPause() {
        super.onPause();
        
        unregisterReceiver(mBroadcastReceiver);
    }
    
    private CustomReceiver mBroadcastReceiver = new CustomReceiver();
}
