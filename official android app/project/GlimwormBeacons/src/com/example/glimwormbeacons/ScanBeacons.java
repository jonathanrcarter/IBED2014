package com.example.glimwormbeacons;


import com.example.glimwormbeacons.beacon.AbstractBeacon;
import com.example.glimwormbeacons.configuring.ConfigureBeacon;
import com.example.glimwormbeacons.scanning.BLEScan;
import com.example.glimwormbeacons.scanning.BeaconListAdapter;
import com.example.glimwormbeacons.scanning.beaconListener;

import android.app.ActionBar;
import android.app.ListActivity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ScanBeacons extends ListActivity implements beaconListener {

	BLEScan leScanner;
	BeaconListAdapter bla;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		ab.setTitle(Html
				.fromHtml("<font color='#99CC00'>Glimworm</font> <font color='#000000'>Beacons</font>"));
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		setContentView(R.layout.beaconlist);

		ListView lv = getListView();
		lv.setCacheColorHint(0);
		lv.setBackgroundResource(R.drawable.grid);
		bla = new BeaconListAdapter(this);
		setListAdapter(bla);
		leScanner = new BLEScan(this, 2000);
		leScanner.addBeaconListener(this);
		leScanner.startScan();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.scan_beacons, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        final BluetoothDevice device = bla.getDevice(position).getDevice();
        if (device == null) return;
        final Intent intent = new Intent(this, ConfigureBeacon.class);
        intent.putExtra(ConfigureBeacon.EXTRAS_DEVICE_NAME, device.getName());
        intent.putExtra(ConfigureBeacon.EXTRAS_DEVICE_ADDRESS, device.getAddress());
        leScanner.stopScan();
        startActivity(intent);
    }


	@Override
	public void beaconFound(AbstractBeacon b) {
		bla.addDevice(b);
		bla.notifyDataSetChanged();
	}

	@Override
	protected void onPause() {
		leScanner.stopScan();
		bla.clear();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (bla != null) {
			bla = new BeaconListAdapter(this);
			setListAdapter(bla);
			leScanner.startScan();
		}
	}

}
