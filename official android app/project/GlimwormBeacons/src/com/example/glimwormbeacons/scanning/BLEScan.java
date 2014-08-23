package com.example.glimwormbeacons.scanning;

import java.util.ArrayList;
import java.util.List;

import com.example.glimwormbeacons.R;
import com.example.glimwormbeacons.beacon.AbstractBeacon;
import com.example.glimwormbeacons.beacon.GlimwormBeacon;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.widget.Toast;

public class BLEScan {
	private BluetoothAdapter mBluetoothAdapter;
	private boolean mScanning;
	private Handler mHandler;
	private Activity activity;

	private long SCAN_PERIOD = 2000;

	List<beaconListener> listenerList = new ArrayList<beaconListener>();

	public BLEScan(Activity activity, long period) {
		if (!activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(activity, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
			activity.finish();
		}
		final BluetoothManager bluetoothManager = (BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		if (mBluetoothAdapter == null) {
			Toast.makeText(activity, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
			activity.finish();
			return;
		}
		this.activity = activity;
		this.SCAN_PERIOD = period;
		mHandler = new Handler();
	}

	public void addBeaconListener(beaconListener bl) {
		listenerList.add(bl);
	}

	public void startScan() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (mScanning) {
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
					startScan();
				}
			}
		}, SCAN_PERIOD);

		mScanning = true;
		mBluetoothAdapter.startLeScan(mLeScanCallback);
	}

	public void stopScan() {
		if (mScanning) {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
	}
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
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

					int i;
					int startByte = 5;
					int battery = 0;
				//	System.out.println(bytesToHex(scanRecord));
					for (i = 0; i < scanRecord.length; i++) {
						
						if (scanRecord[i] == 0x07) {
							String outstr = "";
							for (int j = i; j < scanRecord.length; j++) {
								int l = (int) scanRecord[j] & 0x000000FF;
								outstr += l + " ";
							}
							battery = scanRecord[i + 7];
							//System.out.println(outstr);
							break;
						}
					}
					int power = (int) scanRecord[startByte + 24];
					
					byte[] proximityUuidBytes = new byte[16];
					System.arraycopy(scanRecord, startByte+4, proximityUuidBytes, 0, 16); 
					String hexString = bytesToHex(proximityUuidBytes);
					StringBuilder sb = new StringBuilder();
					sb.append(hexString.substring(0,8));
					sb.append("-");
					sb.append(hexString.substring(8,12));
					sb.append("-");
					sb.append(hexString.substring(12,16));
					sb.append("-");
					sb.append(hexString.substring(16,20));
					sb.append("-");
					sb.append(hexString.substring(20,32));
					System.out.println(sb.toString());
					
					
					int major = (scanRecord[startByte + 20] & 0xff) * 0x100 + (scanRecord[startByte + 21] & 0xff);
					int minor = (scanRecord[startByte + 22] & 0xff) * 0x100 + (scanRecord[startByte + 23] & 0xff);

					String tmp_name = device.getName();
					if (tmp_name == null)
						tmp_name = "";
					
					AbstractBeacon ib = new GlimwormBeacon(device, sb.toString(), tmp_name, major, minor, power, rssi, battery);
					for (beaconListener bl : listenerList) {
						bl.beaconFound(ib);
					}

				}
			});
		}
	};

}
