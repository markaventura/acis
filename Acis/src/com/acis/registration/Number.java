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

import com.acis.main.MainActivity;
import com.sourcepad.acis.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Number extends Activity implements OnClickListener{
	
	Bundle extras;
	Button buttonNext;
	EditText txtNumber;
	String name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.number);
		
		buttonNext = (Button) findViewById(R.id.next);
		buttonNext.setOnClickListener(this);
		
		txtNumber = (EditText) findViewById(R.id.number);
		
		extras = getIntent().getExtras();
		
		name = extras.getString("name");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.next:
			Thread t = new Thread(new Runnable() {
		        @Override
		        public void run() {

		        	String android_id = Secure.getString(getApplicationContext().getContentResolver(),
                            Secure.ANDROID_ID);
		        	
		            HttpClient httpclient = new DefaultHttpClient();
		            HttpPost httppost = new HttpPost("https://40d6b289.ngrok.com/api/users/create_user");
		            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		    		pairs.add(new BasicNameValuePair("name", name));
    			    pairs.add(new BasicNameValuePair("access_token", MainActivity.accessToken));
    			    pairs.add(new BasicNameValuePair("device_token", android_id));
		    		pairs.add(new BasicNameValuePair("user[mobile_number]", txtNumber.getText().toString()));
		    		pairs.add(new BasicNameValuePair("user[device_token]", android_id));
		    		
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
		    
			Intent verification = new Intent(Number.this, Verification.class);
			verification.putExtra("number", txtNumber.getText().toString());
			verification.putExtra("name", name);
			startActivity(verification);
			break;
		}
	}

}
