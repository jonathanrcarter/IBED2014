package com.example.glimwormbeacons.configuring;

import com.example.glimwormbeacons.beacon.BeaconMessage;

public interface BeaconConnectionListener {
	public void beaconConnected();
	public void beaconSystemDisconnected();
	public void beaconUserDisconnected();
	public void dataReceived(BeaconMessage bm);
}
