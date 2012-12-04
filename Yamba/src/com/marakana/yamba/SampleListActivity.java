package com.marakana.yamba;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * SampleListActivity - small demo of a list activity
 * @author mailat
 *
 */
public class SampleListActivity extends ListActivity {

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		String[] valuesAvailable = {"marius test 1", "marius test 2", "marius test 3"};
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, valuesAvailable));
	}
		
}

