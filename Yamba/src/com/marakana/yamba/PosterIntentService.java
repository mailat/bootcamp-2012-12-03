package com.marakana.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class PosterIntentService extends IntentService {
	
	private static final String TAG = "PosterService";
	
	public PosterIntentService() {
		super("UpdaterService-Poster");
	}

	@Override
	protected void onHandleIntent(Intent intent) {		
		String status = intent.getStringExtra("post_status");
		
		if (status!=null && status.length()>0) {
			try
			{				
				Twitter.Status twitterStatus = ((YambaApplication) getApplication()).getTwitter().setStatus(status);
				Log.d (TAG, "Message sent, response:" + twitterStatus);
			}
			catch (TwitterException e)
			{
				Log.e(TAG, "Posting error: " + e.toString());
			}
		}
	}
}
