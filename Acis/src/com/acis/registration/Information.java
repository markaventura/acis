package com.acis.registration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.sourcepad.acis.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
	String response;
	
	private static final int SELECT_PHOTO = 100;
	
	Button upload;
	Button saveBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);
		
		number = (TextView) findViewById(R.id.number);
		name = (TextView) findViewById(R.id.name);


		
		email = (EditText) findViewById(R.id.email);
		company = (EditText) findViewById(R.id.company);
		title = (EditText) findViewById(R.id.job_title);
		url = (EditText) findViewById(R.id.website_url);
		

		
		extras = getIntent().getExtras();
		userName = extras.getString("name");
		userNumber = extras.getString("number");
		
		response = extras.getString("response");
		
		number.setText(userNumber);
		name.setText(userName);
		
//		image = (ImageView) findViewById(R.id.imageView1);
//		
//		upload = (Button) findViewById(R.id.upload);
//		upload.setOnClickListener(this);

		
		saveBtn = (Button) findViewById(R.id.saveBtn);
		saveBtn.setOnClickListener(this);
		
		Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.saveBtn:
			Thread t = new Thread(new Runnable() {
		        @Override
		        public void run() {

		        	String android_id = Secure.getString(getApplicationContext().getContentResolver(),
                            Secure.ANDROID_ID);

		            HttpClient httpclient = new DefaultHttpClient();
		            HttpPost httppost = new HttpPost("https://40d6b289.ngrok.com/api/profiles/create_profile");
		            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		            
		            pairs.add(new BasicNameValuePair("device_token", android_id));
		            pairs.add(new BasicNameValuePair("profile[name]", name.getText().toString()));
		    		pairs.add(new BasicNameValuePair("profile[email]", email.getText().toString()));
		    		pairs.add(new BasicNameValuePair("profile[company]", company.getText().toString()));
		    		pairs.add(new BasicNameValuePair("profile[job_title]", url.getText().toString()));
		    		pairs.add(new BasicNameValuePair("profile[website]", title.getText().toString()));
		    		
		            try {
		            	httppost.setEntity(new UrlEncodedFormEntity(pairs));
						httpclient.execute(httppost);
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		    });
		    t.start();

			break;

//			case R.id.upload:
//				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//				photoPickerIntent.setType("image/*");
//				startActivityForResult(photoPickerIntent, SELECT_PHOTO); 
//				break;
			
		}
	}
		
//			case R.id.upload:
//				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//				photoPickerIntent.setType("image/*");
//				startActivityForResult(photoPickerIntent, SELECT_PHOTO); 
//				break;
		
		
		
		


	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//
//	    switch(requestCode) { 
//	    	case SELECT_PHOTO:
//	    		if(resultCode == RESULT_OK){  
//	    			Uri selectedImage = data.getData();
//	    			InputStream imageStream = null;
//	    			
//					try {
//						imageStream = getContentResolver().openInputStream(selectedImage);
//					} catch (FileNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//	    			Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
//	    			
//	    			int nh = (int) ( yourSelectedImage.getHeight() * (512.0 / yourSelectedImage.getWidth()) );
//	    			Bitmap scaled = Bitmap.createScaledBitmap(yourSelectedImage, 512, nh, true);
//	    			image.setImageBitmap(scaled);
//	    			
//	    		}
//	    	}
//
//		}
	}
