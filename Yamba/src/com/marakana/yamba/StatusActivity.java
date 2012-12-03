package com.marakana.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
		try
		{
			twitter.setStatus(editText.getText().toString());
			Log.d ("Yamba", "Message sent:" + editText.getText().toString());
		}
		catch (TwitterException e)
		{
			Log.e("Yamba", e.toString());
		}
	}

}
