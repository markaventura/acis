package com.acis.registration;

import com.acis.main.MainActivity;
import com.sourcepad.acis.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Name extends Activity implements OnClickListener{
	
	Button buttonNext;
	EditText txtName;
	Bundle extras;
	String asds;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.name);
		
		buttonNext = (Button) findViewById(R.id.next);
		buttonNext.setOnClickListener(this);
		
		txtName = (EditText) findViewById(R.id.name);
		
		extras = getIntent().getExtras();
		asds = extras.getString("response");
		
//		Toast.makeText(getApplicationContext(), asds, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.next:
				Intent number = new Intent(Name.this, Number.class);
				number.putExtra("name", txtName.getText().toString());
				startActivity(number);
				break;
		}
		
	}
	
}
