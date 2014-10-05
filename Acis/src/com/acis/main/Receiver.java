package com.acis.main;

import com.acis.registration.Information;
import com.acis.registration.Name;
import com.acis.registration.Number;
import com.acis.registration.Verification;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
	Integer prev_state=0;
	AlertDialog dialog;
	Context context;
	public static String pNumber = "";
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
	    String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
	    if(stateStr.equals("RINGING")){
	    	pNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
	    }
	    
	    Toast.makeText(context, pNumber, Toast.LENGTH_LONG).show(); 
	    if(stateStr.equals("IDLE")){
	    	try {
				
				Intent information = new Intent(context, Information.class);
				information.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				
				information.putExtra("number", pNumber);
				information.putExtra("name", "");
				information.putExtra("response", "");
//    			if (jsonResponse.getString("success").toString() == "true"){
				
				context.startActivity(information);

			} catch (Exception e) {
				Log.e("AlarmClock.java", "ERROR: ", e);
			}
	    }
	    
	    
	    
	}
	
	private void runOnUiThread(Runnable runnable) {
		// TODO Auto-generated method stub
		
	}

	public class MyPhoneStateListener extends PhoneStateListener {
	  public void onCallStateChanged(int state,String incomingNumber){
	  switch(state){
	    case TelephonyManager.CALL_STATE_IDLE:
//	    	Toast.makeText(context, "yeah", Toast.LENGTH_LONG).show();
	    break;
	    case TelephonyManager.CALL_STATE_OFFHOOK:
//	    	Toast.makeText(context, "asds", Toast.LENGTH_LONG).show();
	    break;
	    case TelephonyManager.CALL_STATE_RINGING:
//	    	Toast.makeText(context, "asasdsds", Toast.LENGTH_LONG).show();
	    break;
	    }
	  } 
	}


	
}