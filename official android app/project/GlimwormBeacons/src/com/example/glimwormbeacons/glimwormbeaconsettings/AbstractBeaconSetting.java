package com.example.glimwormbeacons.glimwormbeaconsettings;

import com.example.glimwormbeacons.R;
import com.example.glimwormbeacons.beacon.BeaconMessage;
import com.example.glimwormbeacons.configuring.BeaconConnection;
import com.example.glimwormbeacons.configuring.BeaconConnectionListener;
import com.example.glimwormbeacons.configuring.SettingsAdapter.SettingSummary;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class AbstractBeaconSetting implements BeaconConnectionListener {
	protected String name;
	protected String commandString;
	protected String returnString;
	protected String value;
	protected String defaultValue;
	protected boolean readOnly = true;
	protected String description;
	protected LinearLayout setting_full_view;
	protected boolean visible = false;
	
	public AbstractBeaconSetting(String name, String commandString, String returnString, String defaultValue, boolean readOnly,boolean visible, String description) {
		this.name = name;
		this.commandString = commandString;
		this.returnString = returnString;
		this.defaultValue = defaultValue;
		value = defaultValue;
		this.readOnly = readOnly;
		this.description = description;
		this.visible = visible;
	}
	
	public boolean isReadOnly()
	{
		return readOnly;
	}
	
	public boolean isVisible()
	{
		return visible;
	}

	public void beaconConnected() {

	}

	public void beaconSystemDisconnected() {
	}

	public void beaconUserDisconnected() {
	}
	
	public void dataReceived(BeaconMessage bm) {
		System.out.println("Message Received: " + bm.getResponse());
		if (bm.getMessage().equals(commandString + "?")) {
			if (bm.getResponse().startsWith(returnString)) {
				String response = bm.getResponse().replace(returnString, "");
				response=response.replaceAll("[^A-Za-z0-9 ]", "");
				this.value = response;
			}
		}
	}

	public String getValue(BeaconConnection beaconConnection)
	{
		beaconConnection.addListener(this, 1);
		beaconConnection.transmitData(commandString + "?");
		return null;
	}
	
	public void setValue(BeaconConnection beaconConnection)
	{
	//	beaconConnection.addListener(this, 1);
		System.out.println("SETTING VALUE: "+commandString +value );
		beaconConnection.transmitData(commandString + value);
	}

	public View populateSummaryView(SettingSummary setting) {
		if (!readOnly)
			setting.full_settings_arrow.setVisibility(android.view.View.VISIBLE);
		setting.setting_name.setText(name);
		setting.setting_value.setText(formatOutput(value));
		return null;
	}

	public View populateFullView(Activity a, View v) {
		View newview = a.getLayoutInflater().inflate(R.layout.setting_full, null);
		TextView sname = (TextView) newview.findViewById(R.id.setting_name);
		TextView detail = (TextView) newview.findViewById(R.id.setting_detail);
		TextView current_value = (TextView) newview.findViewById(R.id.setting_current_value);
		sname.setText("Setting beacon " + name.toLowerCase());
		detail.setText(description);
		current_value.setText("Current value: " + formatOutput(value));
		setting_full_view = (LinearLayout) newview.findViewById(R.id.setting_container);
		return newview;
	}

	public String formatOutput(String input) {
		return input.trim();
	}
	
	public String formatInput(String input)
	{
		return input;
	}
}
