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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.acis.registration.Name;
import com.sourcepad.acis.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	HttpClient httpclient = new DefaultHttpClient();
	String username = "";
	String password = "";
	String responseCode = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Thread t = new Thread(new Runnable() {
	        @Override
	        public void run() {

	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost("https://40d6b289.ngrok.com/api/verification_codes");
	            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	    		pairs.add(new BasicNameValuePair("name", "mark"));
	    		pairs.add(new BasicNameValuePair("mobile_number", "09166456537"));
	    		
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
