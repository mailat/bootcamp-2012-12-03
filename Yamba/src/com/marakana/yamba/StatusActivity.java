package com.marakana.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class StatusActivity extends Activity implements OnClickListener{
	EditText editText;
	Button updateButton;
	Twitter twitter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status);
		
		//get the text entered by user
		editText = (EditText) findViewById(R.id.editText);
		
		//updateButton
		updateButton = (Button) findViewById(R.id.buttonUpdate);
		
		//we assign onclick
		updateButton.setOnClickListener(this);
		
		twitter = new Twitter("MariusMailat", "parola");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");		
	}
	
	public void onClick (View v)
	{
		//send update to yamba.marakana.com
		String status = editText.getText().toString();
		new PostToTwitter().execute(status);
	}

	class PostToTwitter extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... statuses) {	
			try
			{
				Twitter.Status status = twitter.setStatus(editText.getText().toString());
				Log.d ("Yamba", "Message sent:" + editText.getText().toString());
				return (status.text);
			}
			catch (TwitterException e)
			{
				Log.e("Yamba", e.toString());
				return "Failed to post";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}
		
	}
}
