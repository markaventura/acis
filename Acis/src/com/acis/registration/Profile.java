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

public class Profile extends Activity implements OnClickListener{
	
	Button buttonNext;
	EditText txtName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.name);
		
		buttonNext = (Button) findViewById(R.id.next);
		buttonNext.setOnClickListener(this);
		
		txtName = (EditText) findViewById(R.id.name);
	}

	@Override
	public void onClick(View v) {

	}
	
}
