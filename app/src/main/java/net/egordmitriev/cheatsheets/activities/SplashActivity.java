package net.egordmitriev.cheatsheets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by egordm on 18-8-2017.
 */

public class SplashActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
	
}
