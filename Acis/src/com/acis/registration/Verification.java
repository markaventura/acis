package com.acis.registration;

import java.io.IOException;
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
import android.widget.TextView;
import android.widget.Toast;

public class Verification extends Activity implements OnClickListener{
	
	Bundle extras;
	String name, number;
	
	Button buttonNext;
	
	TextView txtName;
	
	EditText code;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verification);
		
		buttonNext = (Button) findViewById(R.id.next);
		txtName = (TextView) findViewById(R.id.txtName);
		
		buttonNext.setOnClickListener(this);
		
		extras = getIntent().getExtras();
		name = extras.getString("name");
		number = extras.getString("number");
		
		code = (EditText) findViewById(R.id.code);
		
		txtName.setText(name + ", we are sending you an SMS to " + number);
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.next:
			Thread t = new Thread(new Runnable() {
		        @Override
		        public void run() { 
		        	HttpClient Client = new DefaultHttpClient();
		        	HttpPost httppost = new HttpPost("https://40d6b289.ngrok.com/api/verification_codes/check_verification");

		        	String android_id = Secure.getString(getApplicationContext().getContentResolver(),
                            Secure.ANDROID_ID);
		        	
    			    List<NameValuePair> pairs = new ArrayList<NameValuePair>();

    			    pairs.add(new BasicNameValuePair("access_token", MainActivity.accessToken));
    			    pairs.add(new BasicNameValuePair("device_token", android_id));
    			    pairs.add(new BasicNameValuePair("mobile_number", number));
		    		
		    		pairs.add(new BasicNameValuePair("code", code.getText().toString()));

		            try {
		            	httppost.setEntity(new UrlEncodedFormEntity(pairs));
		            	HttpResponse response = Client.execute(httppost);
		            	String responseBody = EntityUtils.toString(response.getEntity());
		            	JSONObject jsonResponse=new JSONObject(responseBody);
		            	
		            	Intent information = new Intent(Verification.this, Information.class);
		    			information.putExtra("number", number);
		    			information.putExtra("name", name);
		    			information.putExtra("response", jsonResponse.getString("success").toString());
//		    			if (jsonResponse.getString("success").toString() == "true"){
	    				startActivity(information);
//		    			}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						Intent information = new Intent(Verification.this, Information.class);
		    			information.putExtra("number", number);
		    			information.putExtra("name", name);
		    			information.putExtra("response", "");
//		    			if (jsonResponse.getString("success").toString() == "true"){
	    				startActivity(information);
					}
		        }
		    });
		    t.start();
		}
	}

}
