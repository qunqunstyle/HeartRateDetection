package com.example.heartratedect.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.heartratedect.R;

// just a test for github
public class AboutUsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.aboutus);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		AboutUsActivity.this.setResult(2);
		AboutUsActivity.this.finish();
	}
}
