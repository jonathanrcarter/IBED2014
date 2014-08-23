package com.example.ibeaconexperiencedays.BLE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.example.ibeaconexperiencedays.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class BLEScan {
	private BluetoothAdapter mBluetoothAdapter;
	private boolean mScanning;
	private Handler mHandler;
	private Handler mHandler2;
	private Activity activity;

	private long SCAN_PERIOD = 10000;
	
	List<BLEAdvertisementListener> listeners = new ArrayList<BLEAdvertisementListener>();
 
	public BLEScan(Activity activity, long period) throws BLEException {
		if (!activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(activity, R.string.ble_not_supported, Toast.LENGTH_LONG).show();
			throw new BLEException("BLE NOT SUPPORTED");
		}
		final BluetoothManager bluetoothManager = (BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
			Toast.makeText(activity, R.string.error_bluetooth_not_supported, Toast.LENGTH_LONG).show();
			throw new BLEException("BLUETOOTH NOT ON");
		}
		this.activity = activity;
		this.SCAN_PERIOD = period;
		mHandler = new Handler();
		mHandler2 = new Handler();
		Log.d("SCANNING","BLE Initialized");
	}
	
	public void addBLEAdvertisementListener(BLEAdvertisementListener bal)
	{
		this.listeners.add(bal);
		Log.d("SCANNING","Advertisement listener added");
	}

	boolean periodicScan = false;

	public void startScan() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (mScanning && !periodicScan) {
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
					startScan();
				}
			}
		}, SCAN_PERIOD);

		periodicScan = false;
		mScanning = true;
		mBluetoothAdapter.startLeScan(mLeScanCallback);
	}

	public void stopScan() {
		if (mScanning) {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
	}

	
	public void startIntervalScan(int interval) {
		final int new_interval = interval;
		mHandler2.postDelayed(new Runnable() {
			@Override
			public void run() {
				if(periodicScan){
					System.out.println("Restarting interval");
					startIntervalScan(new_interval);
					
				}
			}
		}, interval);
		
		
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (mScanning) {
					System.out.println("Restarting scan");
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
				}
			}
		}, SCAN_PERIOD);

		periodicScan=true;
		mScanning = true;
		mBluetoothAdapter.startLeScan(mLeScanCallback);
	}
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

		@Override
		public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Log.d("SCANNING","Found Device");
					Log.d("BLE Scanner","BLE Device found "+device.getAddress());
					for(BLEAdvertisementListener listener:listeners) listener.AdvertisementFound(device, rssi, scanRecord);
					/*
					int i;
					int startByte = 5;
					int battery = 0;
				//	System.out.println(bytesToHex(scanRecord));

					String sensor1 = null, sensor2 = null;

					for (i = 0; i < scanRecord.length; i++) {

						if (scanRecord[i] == 0x07) {
							String outstr = "";
							for (int j = i; j < scanRecord.length; j++) {
								int l = (int) scanRecord[j] & 0x000000FF;
								outstr += l + " ";
							}
							battery = scanRecord[i + 7];
							if (scanRecord[i + 5] > 0)
								sensor1 = String.valueOf(scanRecord[i + 5]);
							if (scanRecord[i + 6] > 0)
								sensor2 = String.valueOf(scanRecord[i + 6]);
							// System.out.println(outstr);
							break;
						}
					}
					int power = (int) scanRecord[startByte + 24];
				//	System.out.println("Sensor 1:" + sensor1 + ", Sensor 2:" + sensor2);

					byte[] proximityUuidBytes = new byte[16];
					System.arraycopy(scanRecord, startByte + 4, proximityUuidBytes, 0, 16);
					String hexString = bytesToHex(proximityUuidBytes);
					StringBuilder sb = new StringBuilder();
					sb.append(hexString.substring(0, 8));
					sb.append("-");
					sb.append(hexString.substring(8, 12));
					sb.append("-");
					sb.append(hexString.substring(12, 16));
					sb.append("-");
					sb.append(hexString.substring(16, 20));
					sb.append("-");
					sb.append(hexString.substring(20, 32));
			//		System.out.println(sb.toString());

					int major = (scanRecord[startByte + 20] & 0xff) * 0x100 + (scanRecord[startByte + 21] & 0xff);
					int minor = (scanRecord[startByte + 22] & 0xff) * 0x100 + (scanRecord[startByte + 23] & 0xff);

					String tmp_name = device.getName();
					if (tmp_name == null)
						tmp_name = "";
					
					
					AbstractBeacon ib;
					if(sensor1==null) sensor1 = "0";
					if(sensor2==null) sensor2 = "0";
					int total_val = Integer.valueOf(sensor1) * 127 + Integer.valueOf(sensor2);
					if (total_val > 0) {
						ib = new GlimwormSensor(device, sb.toString(), tmp_name, major, minor, power, rssi, battery, sensor1, sensor2);
					} else {
						ib = new GlimwormBeacon(device, sb.toString(), tmp_name, major, minor, power, rssi, battery);

					}
					for (beaconListener bl : listenerList) {
						bl.beaconFound(ib);
					}
					*/
					
				}
			});
		}
	};

}
