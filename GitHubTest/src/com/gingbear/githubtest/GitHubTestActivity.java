package com.gingbear.githubtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class GitHubTestActivity extends Activity {
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
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        }
    }
}