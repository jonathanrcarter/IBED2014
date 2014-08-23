package com.example.glimwormbeacons.glimwormbeaconsettings;

import com.example.glimwormbeacons.beacon.BeaconMessage;
import com.example.glimwormbeacons.configuring.BeaconConnection;
import com.example.glimwormbeacons.configuring.SettingsAdapter.SettingSummary;

import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class BeaconPinSetting extends AbstractBeaconSetting {

	private boolean pinOn = false;
	private String original_pin = "";

	public BeaconPinSetting(String name, String commandString, String returnString, String defaultValue, boolean readOnly,boolean visible, String description) {
		super(name, commandString, returnString, defaultValue, readOnly,visible, description);
	}

	public View populateFullView(Activity a, View v) {
		View newview = super.populateFullView(a, v);
		EditText et = new EditText(v.getContext());
		et.setInputType(InputType.TYPE_CLASS_PHONE);
		if (pinOn)
			et.setText(value);
		else
			et.setText("");
		et.addTextChangedListener(new TextWatcher() {

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
				if (s.length() > 6)
					s.replace(6, s.length(), "");
				if (s.length() == 6) {
					pinOn=true;
					value = s.toString();
				} else {
					pinOn=false;
					value = "000000";
				}
			}
		});
		et.setTransformationMethod(new PasswordTransformationMethod());

		setting_full_view.addView(et);
		return newview;
	}

	public String getValue(BeaconConnection beaconConnection) {
		beaconConnection.addListener(this, 1);
		beaconConnection.transmitData("AT+TYPE?");
		beaconConnection.transmitData("AT+PASS?");
		return null;
	}

	public void setValue(BeaconConnection beaconConnection) {
		// beaconConnection.addListener(this, 1);
		System.out.println("SETTING VALUE: " + "AT+PASS" + value);
		if(pinOn) beaconConnection.transmitData("AT+TYPE2");
		else beaconConnection.transmitData("AT+TYPE0");
		beaconConnection.transmitData("AT+PASS" + value);
	}

	public void dataReceived(BeaconMessage bm) {
		System.out.println("Message Received: " + bm.getResponse());
		if (bm.getMessage().equals("AT+TYPE?")) {
			if (bm.getResponse().startsWith("OK+Get:")) {
				String response = bm.getResponse().replace("OK+Get:", "");
				if (Integer.valueOf(response) > 0)
					pinOn = true;
			}
		} else if (bm.getMessage().equals("AT+PASS?")) {
			if (bm.getResponse().startsWith("OK+Get:")) {
				String response = bm.getResponse().replace("OK+Get:", "");
				this.value = response;
			}
		}
	}

	public String formatOutput(String input) {
		if (!pinOn)
			return "Off";
		else return "On";
	}

}
