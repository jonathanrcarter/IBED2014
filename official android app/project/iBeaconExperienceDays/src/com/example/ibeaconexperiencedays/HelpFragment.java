package com.example.ibeaconexperiencedays;

import java.util.ArrayList;
import java.util.List;

import com.example.ibeaconexperiencedays.BLE.BLEAdvertisementListener;
import com.example.ibeaconexperiencedays.BLE.BLEException;
import com.example.ibeaconexperiencedays.BLE.BLEScan;
import com.example.ibeaconexperiencedays.BLE.GlimwormBeacon;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class HelpFragment extends Fragment implements BLEAdvertisementListener {

	WebView webView;
	Button button;
	ProgressBar pgb;

	boolean scanning = false;
	Handler handler;
	int totalScanTime = 0;
	ImageView img;
	BLEScan bles;
	
	List<GlimwormBeacon> foundBeacons = new ArrayList<GlimwormBeacon>();

	private void startScan() {
		foundBeacons.clear();
		try {
			bles = new BLEScan(getActivity(), 120);
		} catch (BLEException e) {
			return;
		}
		img.setImageResource(R.drawable.abuttong_down);
		bles.addBLEAdvertisementListener(this);
		bles.startScan();
		if (pgb != null)
			pgb.setVisibility(View.VISIBLE);
		scanning = true;
		handler = new Handler();
		pgb.setProgress(0);
		handler.postDelayed(scanStatusUpdate, 100);
		Log.d("SCANNING","Starting Scan");
	}

	Runnable scanStatusUpdate = new Runnable() {
		@Override
		public void run() {
			double progress = (((double) totalScanTime) / ((double) 4000)) * 100;
			pgb.setProgress((int) progress);
			totalScanTime += 100;
			if (totalScanTime < 4100)
				handler.postDelayed(scanStatusUpdate, 100);
			else {
				stopScan();
			}
		}
	};

	private void stopScan() {
		if (pgb != null)
			pgb.setVisibility(View.GONE);
		if (img != null)
			img.setImageResource(R.drawable.abuttong);
		bles.stopScan();
		totalScanTime = 0;
		Log.d("SCANNING","Stopping scan");
		String outstr = "http://jon651.glimworm.com/ibeacon/api-expdays.php?action=array&array=";
		for(GlimwormBeacon gb:foundBeacons)
		{
			Log.d("SCANNING","Found beacon: "+ gb.getName());
			int prox = 0;
			if(gb.getDistance()<0.5) prox = 0;
			else if(gb.getDistance()<2) prox = 1;
			else if(gb.getDistance()<10) prox = 2;
			else prox = 3;
			outstr += "a,"+gb.getMajor()+","+gb.getMinor()+","+Math.abs(gb.getRSSI())+","+gb.getDistance()+","+prox+":";
		}
		if(foundBeacons.size()>0) outstr.substring(0, outstr.length()-2);
		Log.d("SCANNING",outstr);
		webView.loadUrl(outstr);
		scanning=false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup bla, Bundle savedInstanceState) {
		View rootView2 = inflater.inflate(R.layout.help, bla, false);

		webView = (WebView) rootView2.findViewById(R.id.webView1);
		webView.setBackgroundColor(0x323232);
		webView.getSettings().setJavaScriptEnabled(true);

		img = (ImageView) rootView2.findViewById(R.id.scanButton);
		// img.setLayoutParams(new LinearLayout.LayoutParams(400,400));
		pgb = (ProgressBar) rootView2.findViewById(R.id.scanProgress);
		final Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

		img.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (!scanning) {
						ImageView iv = ((ImageView) v.findViewById(R.id.scanButton));
						
						vib.vibrate(100);

						startScan();
					}

				}
				return false;
			}
		});
		return rootView2;
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

	@Override
	public void AdvertisementFound(BluetoothDevice device, int rssi, byte[] scanRecord) {
		Log.d("SCANNING","Found Beacons");
		int i;
		int startByte = 5;
		int battery = 0;
		System.out.println(bytesToHex(scanRecord));

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
		System.out.println("Sensor 1:" + sensor1 + ", Sensor 2:" + sensor2);

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
		System.out.println(sb.toString());

		int major = (scanRecord[startByte + 20] & 0xff) * 0x100 + (scanRecord[startByte + 21] & 0xff);
		int minor = (scanRecord[startByte + 22] & 0xff) * 0x100 + (scanRecord[startByte + 23] & 0xff);

		String tmp_name = device.getName();
		if (tmp_name == null)
			tmp_name = "";
	
		GlimwormBeacon ib = new GlimwormBeacon(device, sb.toString(), tmp_name, major, minor, power, rssi, battery);
		if (foundBeacons.contains(ib)) {
			foundBeacons.get(foundBeacons.indexOf(ib)).update(ib);
		} else {
			foundBeacons.add(ib);
		}
	}

}