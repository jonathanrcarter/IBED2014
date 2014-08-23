package com.example.glimwormbeacons.configuring;

import com.example.glimwormbeacons.R;
import com.example.glimwormbeacons.beacon.BeaconMessage;
import com.example.glimwormbeacons.glimwormbeaconsettings.AbstractBeaconSetting;
import com.example.glimwormbeacons.glimwormbeaconsettings.GlimwormBeaconSettings;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ConfigureBeacon extends Activity implements BeaconConnectionListener, OnItemClickListener {
	private final static String TAG = ConfigureBeacon.class.getSimpleName();

	public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
	public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
	final String actionPinRequested = "android.bluetooth.device.action.PAIRING_REQUEST";
	final String bondStateChanged = "android.bluetooth.device.action.ACTION_BOND_STATE_CHANGED";

	private String mDeviceName;
	private String mDeviceAddress;

	private boolean connected = false;
	private boolean detailedView = false;
	private boolean close_window = false;
	private boolean has_pin = false;
	private boolean intent_receivers_registered = false;

	private ListView settingsList;

	BeaconConnection beaconConnection;
	SettingsAdapter settingsAdapter;
	GlimwormBeaconSettings glbs;

	Handler mHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		final Intent intent = getIntent();
		mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
		mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
		super.onCreate(savedInstanceState);
		getActionBar().setTitle(Html.fromHtml("<font color='#99CC00'>Beacon:</font> <font color='#000000'>" + mDeviceName + "</font>"));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		settingsAdapter = new SettingsAdapter(this);
		// IntentFilter intentFilterPinRequested = new
		// IntentFilter(actionPinRequested);
		// registerReceiver(mReceiverRequiresPin, intentFilterPinRequested);
		// registerReceiver(FoundReceiver, new
		// IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
		mHandler = new Handler();
	}

	@Override
	protected void onStart() {
		super.onStart();
		setContentView(R.layout.connecting_to_beacon);
		IntentFilter intentFilterPinRequested = new IntentFilter(actionPinRequested);
		registerReceiver(mReceiverRequiresPin, intentFilterPinRequested);
		registerReceiver(FoundReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
		intent_receivers_registered = true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		detailedView = false;
		connected = false;
		close_window = false;
		setContentView(R.layout.connecting_to_beacon);
		invalidateOptionsMenu();
		if (has_pin) {
			beaconConnection.resetTransmission();
		} else {
			beaconConnection = new BeaconConnection(this, mDeviceAddress);
			beaconConnection.addListener(this, 0);
			beaconConnection.Connect();
		}
	}

	@Override
	protected void onStop() {
		System.out.println("STOPPED");
		beaconConnection.Disconnect();
		settingsAdapter.clear();
		this.unregisterReceiver(mReceiverRequiresPin);
		this.unregisterReceiver(FoundReceiver);
		intent_receivers_registered = false;
		super.onStop();
	}

	@Override
	public void beaconConnected() {
		mHandler.removeCallbacks(timeoutTask);
		if (!has_pin) {
			connected = true;
			setContentView(R.layout.configure_beacon);
			mHandler.postDelayed(timeoutTask, 10000);
			invalidateOptionsMenu();
			beaconConnection.resetTransmission();
			glbs = new GlimwormBeaconSettings();
			glbs.fetchValues(beaconConnection);
			settingsAdapter = new SettingsAdapter(this);
			settingsAdapter.addBeaconSettings(glbs.getVisibleSettings());
			setupSettingsList();
		}
	}

	@Override
	public void beaconSystemDisconnected() { // Connection fail retry
		connected = false;
		detailedView = false;
		beaconConnection.Reconnect();
		settingsAdapter.clear();
		setContentView(R.layout.connecting_to_beacon);
		invalidateOptionsMenu();
	}

	@Override
	public void beaconUserDisconnected() { // Intentional disconnect, finish
											// this activity.
		if (close_window)
			this.finish();
	}

	@Override
	public void dataReceived(BeaconMessage bm) {
		mHandler.removeCallbacks(timeoutTask);
		settingsAdapter.notifyDataSetChanged();
	}

	@Override
	public void onBackPressed() {
		if (!connected) {
			if (beaconConnection != null)
				beaconConnection.Disconnect();
			this.finish();
		}
		if (!detailedView) {
			if (connected)
				glbs.setValues(beaconConnection);
			close_window = true;
			beaconConnection.Disconnect();
			// super.onBackPressed();
		} else {
			setContentView(R.layout.configure_beacon);
			detailedView = false;
			setupSettingsList();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.connection_menu, menu);
		if (!connected) {
			menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_indeterminate_progress);
		} else {
			menu.findItem(R.id.menu_refresh).setActionView(null);
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		AbstractBeaconSetting bs = settingsAdapter.getBeaconSetting(position);
		if (bs.isReadOnly())
			return;
		setContentView(bs.populateFullView(this, view));
		detailedView = true;
	}

	public void setupSettingsList() {
		settingsList = (ListView) findViewById(R.id.settings_list);
		settingsList.setCacheColorHint(0);
		settingsList.setBackgroundResource(R.drawable.grid);
		settingsList.setAdapter(settingsAdapter);
		settingsList.setOnItemClickListener(this);
	}

	private final BroadcastReceiver mReceiverRequiresPin = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			mHandler.removeCallbacks(timeoutTask);
			System.out.println(intent.getAction());
			has_pin = true;
			setContentView(R.layout.connecting_to_beacon);
		}
	};

	private final BroadcastReceiver FoundReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			mHandler.removeCallbacks(timeoutTask);
			if (beaconConnection != null && settingsAdapter != null) {
				connected = true;
				setContentView(R.layout.configure_beacon);
				mHandler.postDelayed(timeoutTask, 10000);
				invalidateOptionsMenu();
				beaconConnection.resetTransmission();
				glbs = new GlimwormBeaconSettings();
				glbs.fetchValues(beaconConnection);
				settingsAdapter.clear();
				settingsAdapter.addBeaconSettings(glbs.getVisibleSettings());
				setupSettingsList();
			}
		}
	};

	Runnable timeoutTask = new Runnable() {
		@Override
		public void run() {
			Toast.makeText(getApplicationContext(), "Your beacon connection has timed out, please reconnect", Toast.LENGTH_LONG).show();
			beaconConnection.resetTransmission();
			connected = false;
			onBackPressed();
			
		}
	};
}
