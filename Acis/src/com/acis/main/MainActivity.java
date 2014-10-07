package com.acis.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.acis.registration.Information;
import com.acis.registration.Name;
import com.acis.registration.Number;
//import com.acis.registration.Profile;
import com.acis.registration.Verification;
import com.sourcepad.acis.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	HttpClient httpclient = new DefaultHttpClient();
	HttpPost httppost = new HttpPost("http://damp-brook-4377.herokuapp.com/api/verification_codes");
	String username = "";
	String password = "";
	String responseCode = "";
	public static int userState;
	public static String accessToken;
	public static String mobileNumber;
	public static String userName;
	TextView txtComedy;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		userState = 2;
		String responseMessage = "";

		Thread t = new Thread(new Runnable() {
	        @Override
	        public void run() { 
	        	HttpClient Client = new DefaultHttpClient();
	        	HttpPost httppost = new HttpPost("https://40d6b289.ngrok.com/api/sessions");

	        	String android_id = Secure.getString(getApplicationContext().getContentResolver(),
                        Secure.ANDROID_ID);
	        	
			    List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			    pairs.add(new BasicNameValuePair("device_token", android_id));


	            try {
	            	httppost.setEntity(new UrlEncodedFormEntity(pairs));
	            	HttpResponse response = Client.execute(httppost);
	            	String responseBody = EntityUtils.toString(response.getEntity());
	            	JSONObject jsonResponse=new JSONObject(responseBody);
	            	
//	            	txtComedy.setText(android_id);
//	            	txtComedy.setText()
//	             	if(jsonResponse.toString().contains("message")){
//	            		if(jsonResponse.getString("message").toString().contains("User not found")){
//	            			
//	            		}
//	            	}else{
	            	if(!jsonResponse.toString().contains("message")){
	            		accessToken = jsonResponse.getString("access_token").toString();
	            		mobileNumber = jsonResponse.getString("mobile_number").toString();
	            		userName = jsonResponse.getString("name").toString();
	            		
	            		Intent verification = new Intent(getApplicationContext(), Information.class);
	        			verification.putExtra("number", mobileNumber);
	        			verification.putExtra("name", userName);
	        			verification.putExtra("bool", 1);
	        			
	        			startActivity(verification);
	            	} else {
	            		Intent a = new Intent(getApplicationContext(), Name.class);
            			a.putExtra("response", jsonResponse.getString("message").toString());
            			startActivity(a);
	            	}
	
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					Intent a = new Intent(getApplicationContext(), Name.class);
					startActivity(a);
				}
	        }
	    });
	    t.start();
		
        
	         
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
