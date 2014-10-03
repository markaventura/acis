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
import com.acis.registration.Profile;
import com.acis.registration.Verification;
import com.sourcepad.acis.R;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
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
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		userState = 2;
		

//		Intent a = new Intent(MainActivity.this, Information.class);
//		a.putExtra("number", "");
//		a.putExtra("name", "");
//		startActivity(a);			

	    Thread t = new Thread(new Runnable() {
	      @Override
	      public void run() { 
	
	      String android_id = Secure.getString(getApplicationContext().getContentResolver(),
	                  Secure.ANDROID_ID);
	      
	      HttpClient Client = new DefaultHttpClient();
	      HttpPost httppost = new HttpPost("https://40d6b289.ngrok.com/api/users");
	
		      List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		      pairs.add(new BasicNameValuePair("device_token", android_id));
		

		          try {
		            httppost.setEntity(new UrlEncodedFormEntity(pairs));
		            HttpResponse response = Client.execute(httppost);
		            String responseBody = EntityUtils.toString(response.getEntity());
		            JSONObject jsonResponse=new JSONObject(responseBody);
		            
//		            {"success":true,"active":true,"uid":6,"access_token":"o1NQaPReoCy7HC6CWbke"}
		            
		            String responseMessage = "";
		            String status = "";
		            JSONObject userJson = new JSONObject();
		            
		            if(!jsonResponse.getString("message").isEmpty()){
		            	responseMessage = jsonResponse.getString("message").toString();	
		            }
		            	
		            if(!jsonResponse.getString("active").isEmpty()){
		              status = jsonResponse.getString("active").toString();	
		            }
		            
		            
		            
		            if(!jsonResponse.getString("user").isEmpty()){
			              status = jsonResponse.getString("active").toString();	
			          }

	
		            accessToken = jsonResponse.getString("access_token").toString();
		            
		            
		            if(responseMessage == "User not found"){
		              Intent a = new Intent(MainActivity.this, Name.class);
		              startActivity(a);
		            }
		            else if(status == "active"){
		              Intent a = new Intent(MainActivity.this, Information.class);
		      		  a.putExtra("number",  jsonResponse.getString("mobile_number").toString());
		    		  a.putExtra("name",  jsonResponse.getString("name").toString());
		              startActivity(a);                 
		            }
		            else{
		              Intent a = new Intent(MainActivity.this, Verification.class);
		      		  a.putExtra("number",  jsonResponse.getString("mobile_number").toString());
		    		  a.putExtra("name",  jsonResponse.getString("name").toString());
		              startActivity(a);                   
		            }
		            
		           
		            
		            
		
		
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
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
