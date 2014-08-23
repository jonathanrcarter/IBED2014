package com.example.glimwormbeacons.glimwormbeaconsettings;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.glimwormbeacons.R;
import com.example.glimwormbeacons.beacon.BeaconMessage;
import com.example.glimwormbeacons.configuring.BeaconConnection;
import com.example.glimwormbeacons.configuring.BeaconConnectionListener;
import com.example.glimwormbeacons.configuring.SettingsAdapter.SettingSummary;

public class BeaconTextSetting extends AbstractBeaconSetting {

	public BeaconTextSetting(String name, String commandString, String returnString, String defaultValue, boolean readOnly, boolean visible,String description) {
		super(name, commandString, returnString, defaultValue, readOnly,visible,description);
	}

	public View populateFullView(Activity a, View v) {		

		View newview = super.populateFullView(a, v);
		EditText et = new EditText(v.getContext());
		et.setText(formatOutput(value));
		et.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterTextChanged(Editable s) {
			if(s.length() > 0) value = formatInput(s.toString());
			else value = "";
			}});
		
		setting_full_view.addView(et);
		return newview;
	}

	public String formatInput(String input)
	{
		return input.replaceAll("[^A-Za-z0-9 ]", "");
	}
}
