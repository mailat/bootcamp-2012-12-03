package com.marakana.yamba;

import android.os.Bundle;
import android.widget.EditText;
import android.app.Activity;

public class CustomPreferenceActivity extends Activity {
	SecurePreferences prefs;
	EditText editUsername;
	EditText editPassword;
	EditText editApiRoot;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_preferences);
		
		//references to our edit preferences
		editUsername = (EditText) this.findViewById(R.id.editUsername);
		editPassword = (EditText) this.findViewById(R.id.editPassword);
		editApiRoot = (EditText) this.findViewById(R.id.editApiRoot);
		
		//read the preferences
		prefs = new SecurePreferences(this, "secured_preferences", getSaltPassword(), true);
		editUsername.setText(prefs.getString("username"));
		editPassword.setText(prefs.getString("password"));
		editApiRoot.setText(prefs.getString("apiRoot"));	
	}

	private String getSaltPassword()
	{
		return ( this.getPackageName() + "unique device id" ); 
		// read more about SALT unique device id http://stackoverflow.com/questions/13089070/unique-device-based-salt-in-android
	}
		    
    private void savePreferences()
    {		
    	prefs.put("username", editUsername.getText().toString());
    	prefs.put("password", editPassword.getText().toString());
    	prefs.put("apiRoot", editApiRoot.getText().toString());
    }        
	   
    @Override
    protected void onDestroy() {
    	savePreferences();
    	super.onDestroy();
    }
  
}
