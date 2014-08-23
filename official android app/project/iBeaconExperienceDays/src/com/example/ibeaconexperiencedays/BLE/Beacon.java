package com.example.ibeaconexperiencedays.BLE;

import android.bluetooth.BluetoothDevice;

public interface Beacon {
	public String getName();
	public int getMajor();
	public int getMinor();
	public double getDistance();
	public BluetoothDevice getDevice();

}
