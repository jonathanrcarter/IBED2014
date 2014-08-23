package com.example.glimwormbeacons.configuring;

import java.util.ArrayList;
import java.util.List;
import com.example.glimwormbeacons.R;
import com.example.glimwormbeacons.glimwormbeaconsettings.AbstractBeaconSetting;

import android.app.Activity;
import android.app.ListActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SettingsAdapter extends BaseAdapter {
	private List<AbstractBeaconSetting> beaconSettings = new ArrayList<AbstractBeaconSetting>();
	private LayoutInflater mInflator;

	public SettingsAdapter(Activity la) {
		super();
		mInflator = la.getLayoutInflater();
	}
	
	public void addBeaconSetting(AbstractBeaconSetting s)
	{
		beaconSettings.add(s);
	}
	
	public void addBeaconSettings(List<AbstractBeaconSetting> s)
	{
		beaconSettings.addAll(s);
	}
	
	public void clear()
	{
		beaconSettings.clear();
	}
	
	public AbstractBeaconSetting getBeaconSetting(int i)
	{
		return beaconSettings.get(i);
	}


	@Override
	public int getCount() {
		return beaconSettings.size();
	}

	@Override
	public Object getItem(int i) {
		return beaconSettings.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		SettingSummary settingSummary;
		if (view == null) {
			view = mInflator.inflate(R.layout.setting_summary, null);
			settingSummary = new SettingSummary();
			settingSummary.setting_name = (TextView) view.findViewById(R.id.setting_name);
			settingSummary.setting_value = (TextView) view.findViewById(R.id.setting_value);
			settingSummary.full_settings_arrow = (TextView) view.findViewById(R.id.full_settings_arrow);
			view.setTag(settingSummary);
		} else {
			settingSummary = (SettingSummary) view.getTag();
		}
		AbstractBeaconSetting beacon = beaconSettings.get(i);
		beacon.populateSummaryView(settingSummary);
		return view;
	}

	public static class SettingSummary {
		public TextView setting_name;
		public TextView setting_value;
		public TextView full_settings_arrow;
	}
}
