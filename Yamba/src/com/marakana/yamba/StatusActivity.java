package com.marakana.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
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
		
		//add a counter for the entered chars
		TextWatcher textWatcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				TextView counterText = (TextView) findViewById(R.id.counterText);
				counterText.setText(String.valueOf(editText.getText().toString().length()).toString());
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		};
		editText.addTextChangedListener(textWatcher);
	}
	
	@Override
	public void onClick (View v)
	{
		//send update to yamba.marakana.com
		String status = editText.getText().toString();
		new PostToTwitter().execute(status);
	}

	class PostToTwitter extends AsyncTask<String, Integer, String>{
		Twitter.Status status;
		
		@Override
		protected String doInBackground(String... statuses) {	
			try
			{
				status = twitter.setStatus(editText.getText().toString());
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
			editText.setText("");
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}		
	}
}
