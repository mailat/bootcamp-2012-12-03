package com.marakana.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class YambaApplication extends Application implements OnSharedPreferenceChangeListener{
	private static final String TAG = "Yamba";
	Twitter twitter;
	SharedPreferences prefs;
	private boolean serviceRunning;
	
	public boolean isServiceRunning() {
		return serviceRunning;
	}

	public void setServiceRunning(boolean serviceRunning) {
		this.serviceRunning = serviceRunning;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		//get the preferences and register on preferences changes
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.prefs.registerOnSharedPreferenceChangeListener(this);
		Log.d(TAG, "onCreate");
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.d(TAG, "onTerminate");
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		//invalidate the twitter existing object
		this.twitter = null;
	}
	
	public synchronized Twitter getTwitter()
	{
		if ( this.twitter == null )
		{
			String username, password, apiRoot;
			username = this.prefs.getString("username", "");
			password = this.prefs.getString("password", "");
			apiRoot = this.prefs.getString("apiRoot", "http://yamba.marakana.com/api");
			
			//connect to the twitter
			//twitter = new Twitter("MariusMailat", "parola");
			this.twitter = new Twitter(username, password);
			this.twitter.setAPIRootUrl(apiRoot);	
		}
		
		return (this.twitter);
	}
}
