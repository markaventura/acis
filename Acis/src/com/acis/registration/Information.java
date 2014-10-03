package com.acis.registration;

import java.io.FileNotFoundException;
import java.io.InputStream;

import com.sourcepad.acis.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Information extends Activity implements OnClickListener{
	
	TextView number;
	TextView name;
	EditText email;
	EditText company;
	EditText title;
	EditText url;
	
	ImageView image;
	
	Bundle extras;
	
	String userName;
	String userNumber;
	
	private static final int SELECT_PHOTO = 100;
	
	Button upload;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);
		
		number = (TextView) findViewById(R.id.number);
		name = (TextView) findViewById(R.id.name);
		
		email = (EditText) findViewById(R.id.email);
		company = (EditText) findViewById(R.id.company);
		title = (EditText) findViewById(R.id.title);
		url = (EditText) findViewById(R.id.url);
		
		extras = getIntent().getExtras();
		userName = extras.getString("name");
		userNumber = extras.getString("number");
		
		number.setText(userNumber);
		name.setText(userName);
		
		image = (ImageView) findViewById(R.id.imageView1);
		
		upload = (Button) findViewById(R.id.upload);
		upload.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.upload:
			Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
			photoPickerIntent.setType("image/*");
			startActivityForResult(photoPickerIntent, SELECT_PHOTO); 
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	    switch(requestCode) { 
	    	case SELECT_PHOTO:
	    		if(resultCode == RESULT_OK){  
	    			Uri selectedImage = data.getData();
	    			InputStream imageStream = null;
	    			
					try {
						imageStream = getContentResolver().openInputStream(selectedImage);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
	    			
	    			int nh = (int) ( yourSelectedImage.getHeight() * (512.0 / yourSelectedImage.getWidth()) );
	    			Bitmap scaled = Bitmap.createScaledBitmap(yourSelectedImage, 512, nh, true);
	    			image.setImageBitmap(scaled);
	    			
	    		}
	    	}

		}
	}
