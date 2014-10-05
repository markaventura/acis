package com.acis.registration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.acis.main.MainActivity;
import com.sourcepad.acis.R;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
	TextView txtName;
	EditText etName;
	EditText email;
	EditText company;
	EditText title;
	EditText txturl;
	
	ImageView image;
	
	Bundle extras;
	
	String userName;
	String userNumber;
	String response;
	
	private static final int SELECT_PHOTO = 100;
	
	Button upload, saveContact;
	JSONObject jsonResponse;
	Button saveBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);

		
		number = (TextView) findViewById(R.id.number);
		txtName = (TextView) findViewById(R.id.txtName);

		etName = (EditText) findViewById(R.id.etName);
		
		email = (EditText) findViewById(R.id.email);
		company = (EditText) findViewById(R.id.company);
		title = (EditText) findViewById(R.id.job_title);
		txturl = (EditText) findViewById(R.id.website_url);
		saveContact = (Button) findViewById(R.id.btnSaveToContact);
		saveContact.setOnClickListener(this);
		
		extras = getIntent().getExtras();
		userName = extras.getString("name");
		userNumber = extras.getString("number");
		
		//response = extras.getString("response");
		
		number.setText(userNumber);
		txtName.setText(userName);
		
//		image = (ImageView) findViewById(R.id.imageView1);
//		
//		upload = (Button) findViewById(R.id.upload);
//		upload.setOnClickListener(this);


		Thread t = new Thread(new Runnable() {
	        @Override
	        public void run() { 
	        	HttpClient Client = new DefaultHttpClient();
	      
	        	String android_id = Secure.getString(getApplicationContext().getContentResolver(),
                        Secure.ANDROID_ID);
	        	
	          	String url = "device_token="+android_id+"&access_token="+MainActivity.accessToken+"&mobile_number="+ userNumber;
	        	HttpGet httppost = new HttpGet("https://40d6b289.ngrok.com/api/users/get_user?"+url);

	        	

	            try {
//	            	httppost.setEntity(new UrlEncodedFormEntity(pairs));
	            	HttpResponse response = Client.execute(httppost);
	            	String responseBody = EntityUtils.toString(response.getEntity());
	            	jsonResponse=new JSONObject(responseBody);
	            	
	            	
	            	 runOnUiThread(new Runnable() {
	                        @Override
	                        public void run() {
	                        	try {
	                        		number.setText(jsonResponse.getString("mobile_number").toString());
	                        		txtName.setText(jsonResponse.getString("name").toString());
	                        		
	                        		etName.setText(jsonResponse.getString("name").toString());
									email.setText(jsonResponse.getString("email").toString());
									company.setText(jsonResponse.getString("company").toString());
									title.setText(jsonResponse.getString("job_title").toString());
									txturl.setText(jsonResponse.getString("website").toString());
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                        }
	                    });
	            	 
	
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
	    t.start();
	    
		saveBtn = (Button) findViewById(R.id.saveBtn);
		saveBtn.setOnClickListener(this);
		
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
		          
    			    pairs.add(new BasicNameValuePair("access_token", MainActivity.accessToken));
    			    pairs.add(new BasicNameValuePair("device_token", android_id));
		            pairs.add(new BasicNameValuePair("profile[name]", etName.getText().toString()));
		    		pairs.add(new BasicNameValuePair("profile[email]", email.getText().toString()));
		    		pairs.add(new BasicNameValuePair("profile[company]", company.getText().toString()));
		    		pairs.add(new BasicNameValuePair("profile[job_title]", txturl.getText().toString()));
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

		case R.id.btnSaveToContact:
//				Toast.makeText(this, "Contact added", Toast.LENGTH_LONG).show();
				String DisplayName = etName.getText().toString();
				String MobileNumber = number.getText().toString();
				
				ArrayList < ContentProviderOperation > ops = new ArrayList < ContentProviderOperation > ();

				 ops.add(ContentProviderOperation.newInsert(
				 ContactsContract.RawContacts.CONTENT_URI)
				     .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				     .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
				     .build());
				 
//				 if (DisplayName != null) {
				     ops.add(ContentProviderOperation.newInsert(
				     ContactsContract.Data.CONTENT_URI)
				         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				         .withValue(ContactsContract.Data.MIMETYPE,
				     ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				         .withValue(
				     ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
				     DisplayName).build());
//				 }
				 
//				 if (MobileNumber != null) {
				     ops.add(ContentProviderOperation.
				     newInsert(ContactsContract.Data.CONTENT_URI)
				         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				         .withValue(ContactsContract.Data.MIMETYPE,
				     ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				         .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
				         .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
				     ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
				         .build());
//				 }
				 
			     try {
			         getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
			         Toast.makeText(getApplicationContext(), "Contact added.", Toast.LENGTH_SHORT).show();
					   
			     } catch (Exception e) {
			         e.printStackTrace();
			         Toast.makeText(getApplicationContext(), "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			     } 
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
