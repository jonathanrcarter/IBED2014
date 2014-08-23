package com.example.glimwormbeacons.beacon;

import com.example.glimwormbeacons.scanning.BeaconListAdapter.ViewHolder;

import android.bluetooth.BluetoothDevice;

public interface Beacon {
	public String getName();
	public int getMajor();
	public int getMinor();
	public double getDistance();
	public BluetoothDevice getDevice();
	public void populateView(ViewHolder vh);
	
}
