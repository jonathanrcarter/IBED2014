package com.example.glimwormbeacons.scanning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.glimwormbeacons.R;
import com.example.glimwormbeacons.beacon.AbstractBeacon;
import com.example.glimwormbeacons.beacon.Beacon;

import android.app.ListActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BeaconListAdapter extends BaseAdapter {
	private List<AbstractBeacon> ibeacons = new ArrayList<AbstractBeacon>();
	private LayoutInflater mInflator;
	private ListActivity listactivity;

	public BeaconListAdapter(ListActivity la) {
		super();
		mInflator = la.getLayoutInflater();
	}

	public void addDevice(AbstractBeacon b) {

		if (ibeacons.contains(b)) {
			System.out.println("Updating existing beacon");
			int i = ibeacons.indexOf(b);
			ibeacons.get(i).update(b);
		} else {
			ibeacons.add(b);
			System.out.println("Found new beacon");
		}
		Collections.sort(ibeacons);
	}

	public Beacon getDevice(int position) {
		return ibeacons.get(position);
	}

	public void clear() {
		ibeacons.clear();
	}

	@Override
	public int getCount() {
		return ibeacons.size();
	}

	@Override
	public Object getItem(int i) {
		return ibeacons.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder;
		if (view == null) {
			view = mInflator.inflate(R.layout.listitem_device, null);
			viewHolder = new ViewHolder();
			// viewHolder.deviceAddress = (TextView)
			// view.findViewById(R.id.device_address);
			viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
			viewHolder.rssi = (TextView) view.findViewById(R.id.rssi);
			viewHolder.major = (TextView) view.findViewById(R.id.major);
			viewHolder.minor = (TextView) view.findViewById(R.id.minor);
			viewHolder.batteryLevel = (ImageView) view.findViewById(R.id.battery_level);
			// viewHolder.battery = (TextView) view.findViewById(R.id.battery);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		Beacon beacon = ibeacons.get(i);
		beacon.populateView(viewHolder);
		return view;
	}

	public static class ViewHolder {
		public ImageView batteryLevel;
		public TextView deviceName;
		public TextView deviceAddress;
		public TextView major;
		public TextView minor;
		public TextView rssi;
		public TextView battery;
	}
}
