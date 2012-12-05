package com.marakana.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	
	private static final String TAG = "UpdaterService";
	static final int DELAY = 60000; // one  minute 
	private boolean runFlag = false;
	private Updater updater;
	private YambaApplication yamba;
	
	@Override
	public void onCreate() {
		super.onCreate();
		this.yamba = (YambaApplication) getApplication();
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
		this.yamba.setServiceRunning(true);
		
		Log.d(TAG, "onStartCommand");
	    return  START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.runFlag = false;
		this.updater.interrupt();
		this.updater = null;
		this.yamba.setServiceRunning(false);
		
		Log.d(TAG, "onDestroy");
	}
	
	private class Updater extends Thread
	{
		List<Twitter.Status> timeline;
		
		public Updater()
		{
			super ("UpdaterService-Updater");
		}
		
		@Override
		public void run() {
			UpdaterService updaterService = UpdaterService.this;
			while ( updaterService.runFlag)
			{
				Log.d(TAG, "Updater runnning");
				try {
					try{
						//get the timeline from the yamba.marakana.com
						timeline = yamba.getTwitter().getFriendsTimeline();
					}
					catch (TwitterException e)
					{
						Log.d(TAG, "No connection was possible to twitter API");
					}
					
					for (Twitter.Status status : timeline)
					{
						Log.d(TAG, status.user.name + " - " + status.text);
					}
					
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					updaterService.runFlag = false;
					Log.d(TAG, "error runnning the updater");
				}
			}
		}
	}
}
