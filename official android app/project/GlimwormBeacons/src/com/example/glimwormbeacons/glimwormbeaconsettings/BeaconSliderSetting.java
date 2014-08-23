package com.example.glimwormbeacons.glimwormbeaconsettings;

import android.app.Activity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.glimwormbeacons.beacon.BeaconMessage;
import com.example.glimwormbeacons.configuring.BeaconConnection;

public class BeaconSliderSetting extends AbstractBeaconSetting {
	
	String valueStrings[];
	TextView newValue;
	
	public BeaconSliderSetting(String name,String commandString,String returnString, String defaultValue,String valueStrings[],boolean readOnly,boolean visible, String description){
		super(name,commandString,returnString,defaultValue,readOnly,visible,description);
		this.valueStrings = valueStrings;
	}

	public View populateFullView(Activity a,View v)
	{
		View newview = super.populateFullView(a,v);
		SeekBar seek = new SeekBar(v.getContext());
		newValue = new TextView(v.getContext());
		seek.setMax(valueStrings.length-1);
		seek.setProgress(Integer.valueOf(value));
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			int seekbar_value = 0;

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				seekbar_value = progress;
				onStopTrackingTouch(seekBar);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				value = String.valueOf(seekbar_value);
				newValue.setText(formatOutput(value));
			}

		});
		newValue.setText(formatOutput(value));
		setting_full_view.addView(newValue);
		setting_full_view.addView(seek);
		return newview;
	}


	public String formatOutput(String input) {
		return valueStrings[Integer.valueOf(value)];
	}
}
