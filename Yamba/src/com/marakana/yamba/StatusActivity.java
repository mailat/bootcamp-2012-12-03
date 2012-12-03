package com.marakana.yamba;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status);
		
		//get the text entered by user
		editText = (EditText) findViewById(R.id.editText);
		
		//updateButton
		updateButton = (Button) findViewById(R.id.buttonUpdate);
		updateButton.setOnClickListener(this);
	}
	
	public void onClick (View v)
	{
		//send update to yamba.marakana.com
		//TODO
		
		//log the message
		Log.d ("Yamba", "Message sent:" + editText.getText().toString());
	}

}
