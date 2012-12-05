package com.marakana.yamba;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	
	private static final String TAG = "UpdaterService";
	static final int DELAY = 60000; // one  minute 
	private boolean runFlag = false;
	private Updater updater;
	
	@Override
	public void onCreate() {
		super.onCreate();
		this.updater = new Updater();
		
		//do here stuff which should be done only once
		Log.d(TAG, "onCreate");
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		
		this.runFlag = true;
		this.updater.start();
		
		Log.d(TAG, "onStartCommand");
	    return  START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.runFlag = false;
		this.updater.interrupt();
		this.updater = null;
		
		Log.d(TAG, "onDestroy");
	}
	
	private class Updater extends Thread
	{
		public Updater()
		{
			super ("UpdaterService-Updater");
		}
		
		@Override
		public void run() {
			UpdaterService updaterService = UpdaterService.this;
			while ( updaterService.runFlag)
			{
				try {
					//heavy lifting
					Log.d(TAG, "runnning the updater");
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					updaterService.runFlag = false;
					Log.d(TAG, "error runnning the updater");
				}
			}
		}
	}
}
