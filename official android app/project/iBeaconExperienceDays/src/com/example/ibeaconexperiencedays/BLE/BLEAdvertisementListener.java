package com.example.ibeaconexperiencedays.BLE;

import android.bluetooth.BluetoothDevice;

public interface BLEAdvertisementListener {
	public void AdvertisementFound(final BluetoothDevice device, final int rssi, final byte[] scanRecord);
}
