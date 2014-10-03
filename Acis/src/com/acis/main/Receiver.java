package com.acis.main;

import com.acis.registration.Name;
import com.acis.registration.Number;

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
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
	    String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
	    Toast.makeText(context, stateStr, Toast.LENGTH_LONG).show(); 
	    if(stateStr.equals("IDLE")){
	    	try {
	    		
	    		Intent number = new Intent(context, User.class);
	    		number.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(number);

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