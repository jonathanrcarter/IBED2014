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

public class BeaconResetSetting extends AbstractBeaconSetting {

	public BeaconResetSetting(String name, String commandString, String returnString, String defaultValue, boolean readOnly,boolean visible, String description) {
		super(name, commandString, returnString, defaultValue, readOnly,visible, description);
	}

	public View populateSummaryView(SettingSummary setting) {
		return null;
	}
	
	public String getValue(BeaconConnection beaconConnection)
	{
		return null;
	}
	
	public void setValue(BeaconConnection beaconConnection)
	{
		beaconConnection.addListener(this, 1);
		System.out.println("SETTING VALUE: "+commandString );
		beaconConnection.transmitData(commandString);
	}

	public View populateFullView(Activity a, View v) {
		return v;
	}
}
