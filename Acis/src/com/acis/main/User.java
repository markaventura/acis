package com.acis.main;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.sourcepad.acis.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.provider.Settings.Secure;

public class User extends Activity{
	AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String android_id = Secure.getString(getApplicationContext().getContentResolver(),
                Secure.ANDROID_ID);
		
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost("https://40d6b289.ngrok.com/api/users/get_user?device_token=?"+android_id);
//		httpPost.setEntity(new UrlEncodedFormEntity(param));
		
		setContentView(R.layout.user);
	}

}
