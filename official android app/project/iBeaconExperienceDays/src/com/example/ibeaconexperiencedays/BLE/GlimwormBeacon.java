package com.example.ibeaconexperiencedays.BLE;

import java.util.List;

import android.bluetooth.BluetoothDevice;

public class GlimwormBeacon extends AbstractBeacon {
	private int power, batteryLevel;
	private String UUID, deviceAddress;

	public GlimwormBeacon(BluetoothDevice device, String UUID, String name, int major, int minor, int power, int measuredPower, int batteryLevel) {
		super(device, measuredPower);
		this.UUID = UUID;
		this.name = name;
		this.major = major;
		this.minor = minor;
		this.power = power;
		this.batteryLevel = batteryLevel;
	}

	public int getBatteryLevel() {
		return batteryLevel;
	}

	public BluetoothDevice getDevice() {
		return device;
	}

	public void updateMeasuredPower(int rssi) {
		this.measuredPower = rssi;
	}

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public String getUUID() {
		return UUID;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPower() {

		return power;
	}

	public int getMeasuredPower() {
		return measuredPower;
	}

	int Sum = 0, Filtered_Value = 0, Raw_Value = 0;
	double Filter_Strength = 1.2;
	double avg = 0;

	public double getDistance(int acc) {
		double meters = 0;
		int i;
		if (point_avg.size() > 0) {
			if (getMeasuredPower() < point_avg.get(0) - 40 || getMeasuredPower() > point_avg.get(0) + 40) {
				// System.out.println("Discarding");
			} else {
				point_avg.add(0, getMeasuredPower());
			}
		} else
			point_avg.add(0, getMeasuredPower());
		if (point_avg.size() > 40)
			point_avg.remove(40);

		avg = 0;
		for (i = 0; i < point_avg.size(); i++) {
			avg = avg + point_avg.get(i);
		}
		avg /= i;

		double ratio = avg * 1.0 / getPower();
		if (ratio < 1.0) {
			// meters = Math.pow(ratio, 10);
			double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
			meters = accuracy;
		} else {
			double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
			meters = accuracy;
		}
		meters = Math.floor(meters * acc) / acc;
		if (meters < 0)
			meters = 0.01;
		return meters;
	}

	public boolean equals(Object o) {

		if (o instanceof GlimwormBeacon) {
			GlimwormBeacon b = (GlimwormBeacon) o;
			if (b.getDevice().getAddress().trim().equals(device.getAddress().trim())) {
				return true;
			}

		}
		return false;
	}

	@Override
	public double getDistance() {
		// TODO Auto-generated method stub
		return getDistance(100);
	}

	@Override
	public int compareTo(AbstractBeacon another) {
		if (another instanceof GlimwormBeacon) {
			GlimwormBeacon b = (GlimwormBeacon) another;
			int comp = b.getName().compareTo(name);
			if (comp != 0)
				return comp;
			Integer major = b.getMajor();
			comp = major.compareTo((Integer) major);
			if (comp != 0)
				return comp;
			Integer minor = b.getMinor();
			comp = minor.compareTo((Integer) minor);
			return comp;
		}
		return 0;
	}

}
