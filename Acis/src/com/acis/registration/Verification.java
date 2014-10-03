package com.acis.registration;

import com.sourcepad.acis.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Verification extends Activity implements OnClickListener{
	
	Bundle extras;
	String name, number;
	
	Button buttonNext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verification);
		
		buttonNext = (Button) findViewById(R.id.next);
		buttonNext.setOnClickListener(this);
		
		extras = getIntent().getExtras();
		name = extras.getString("name");
		number = extras.getString("number");
		
		Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(), number, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.next:
			Intent information = new Intent(Verification.this, Information.class);
			information.putExtra("number", number);
			information.putExtra("name", name);
			startActivity(information);
			break;
		}
	}

}
