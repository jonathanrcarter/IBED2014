package com.example.glimwormbeacons.beacon;

import java.util.ArrayList;
import java.util.List;

import com.example.glimwormbeacons.R;
import com.example.glimwormbeacons.scanning.BeaconListAdapter.ViewHolder;

import android.bluetooth.BluetoothDevice;

public abstract class AbstractBeacon implements Beacon, Comparable<AbstractBeacon> {

	protected BluetoothDevice device;
	String name ="";
	protected String UUID;
	protected int major, minor, measuredPower;
	ArrayList<Integer> point_avg = new ArrayList<Integer>();
	
	public AbstractBeacon(BluetoothDevice device,int measuredPower)
	{
		this.device = device;
		this.measuredPower = measuredPower;
	}

	public String getName() {
		return name;
	}

	public abstract int getMajor();

	public abstract int getMinor();
	
	public int getRSSI()
	{
	 return measuredPower;	
	}

	public abstract double getDistance();

	public abstract BluetoothDevice getDevice();

	public boolean equals(Object o) {
		if (o instanceof AbstractBeacon) {
			AbstractBeacon b = (AbstractBeacon) o;
			if (b.getDevice().getAddress().trim()
					.equals(device.getAddress().trim())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(AbstractBeacon another) {
		if (another instanceof AbstractBeacon) {
			AbstractBeacon b = (AbstractBeacon) another;
			int comp = b.getName().compareTo(name);
			return comp;
		}
		return 0;
	}

	
	public void populateView(ViewHolder vh) {
		if (getName() != null && getName().length() > 0)
			vh.deviceName.setText(getName());
		else
			vh.deviceName.setText(R.string.unknown_device);
		vh.deviceAddress.setText(getDevice().getAddress());
		vh.rssi.setText(String.valueOf(getDistance()));
	}
	
	public void update(AbstractBeacon b)
	{
		if(b.getDevice().getAddress().trim().equals(device.getAddress().trim()))
		{
			System.out.println(b.getRSSI());
			this.name = b.getName();
			this.major = b.getMajor();
			this.minor = b.getMinor();
			this.measuredPower = b.getRSSI();
		}
	}
	

}
