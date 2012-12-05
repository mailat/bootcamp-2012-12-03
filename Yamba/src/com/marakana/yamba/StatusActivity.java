package com.marakana.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class StatusActivity extends Activity implements OnClickListener{
	EditText editText;
	Button updateButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status);
		
		//TODO if you want to start the custom activity startActivity(new Intent(this, CustomPreferenceActivity.class));
		
		//get the text entered by user
		editText = (EditText) findViewById(R.id.editText);
		
		//updateButton
		updateButton = (Button) findViewById(R.id.buttonUpdate);
		
		//we assign onclick
		updateButton.setOnClickListener(this);	
		
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
	/**
	 * onClick
	 * whenwe push the button UPDATE
	 */
	public void onClick (View v)
	{
		//if no preferences are added and the user is trying to post an update redirect him to the preference page		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if( !prefs.contains("username")  && !prefs.contains("password"))
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Please enter your credentials. You are now redirected to Settings.");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startActivity(new Intent(StatusActivity.this, PrefsActivity.class));					
				}
			});	
			builder.create().show();
			return;
		}
							
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
				status = ((YambaApplication) getApplication()).getTwitter().setStatus(editText.getText().toString());
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//inflate the menu
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId())
		{
			case R.id.itemPrefs:
				startActivity ( new Intent(this, PrefsActivity.class) );
		}	
		return (true);
	}

}
