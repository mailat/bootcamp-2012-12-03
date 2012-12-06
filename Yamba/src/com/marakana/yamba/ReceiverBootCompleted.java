package com.marakana.yamba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReceiverBootCompleted extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if ( intent.getAction().equalsIgnoreCase("android.intent.action.BOOT_COMPLETED"))
		{
			//do some stuff
			Log.d("Yamba", "we just detected the restart of the phone.");
			
			//TODO homework: Start the service reading the timelines from the yamba.marakana.com
		}
		
	}

}
