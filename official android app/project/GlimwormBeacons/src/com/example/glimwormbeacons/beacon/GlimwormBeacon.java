package com.example.glimwormbeacons.beacon;

import java.util.List;

import com.example.glimwormbeacons.R;
import com.example.glimwormbeacons.scanning.BeaconListAdapter.ViewHolder;

import android.bluetooth.BluetoothDevice;

public class GlimwormBeacon extends AbstractBeacon{
	private int power,batteryLevel;
	private String UUID,deviceAddress;
	
	public GlimwormBeacon(BluetoothDevice device,String UUID,String name,int major, int minor, int power,int measuredPower, int batteryLevel)
	{
		super(device,measuredPower);
		this.UUID = UUID;
		this.name = name;
		this.major = major;
		this.minor = minor;
		this.power = power;
		this.batteryLevel = batteryLevel;
	}
	
	public int getBatteryLevel()
	{
		return batteryLevel;
	}
	
	public BluetoothDevice getDevice()
	{
		return device;
	}
	
	public void updateMeasuredPower(int rssi)
	{
		this.measuredPower = rssi;
	}
	
	public int getMajor()
	{
		return major;
	}
	
	public int getMinor()
	{
		return minor;
	}
	
	public String getUUID()
	{
		return UUID;
	}
	
	public void setMinor(int minor)
	{
	 this.minor = minor;
	}
	
	public void setMajor(int major)
	{
		this.major = major;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

	public int getPower()
	{
		
		return power;
	}
	
	public int getMeasuredPower()
	{
		return measuredPower;
	}
	
	int Sum=0,Filtered_Value=0,Raw_Value=0;
	double Filter_Strength = 1.2;
	double avg = 0;
	
	public double getDistance(int acc)
	{
		if(point_avg.size() > 0)
		{
			if(getMeasuredPower() < point_avg.get(0)-40 || getMeasuredPower() > point_avg.get(0)+40)
			{
				System.out.println("Discarding");
			}
			else
			{
				point_avg.add(0, getMeasuredPower());
			}
		}
		else point_avg.add(0, getMeasuredPower());
		if(point_avg.size()>5) point_avg.remove(5);
		double meters=0;
	
		/*
		double ratio = getMeasuredPower()*1.0/getPower();
		if (ratio < 1.0) {
			meters= Math.pow(ratio,10);
		}
		else {
			meters =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;    
		}
		
		*/
		int i;
		avg = 0;
		for(i=0;i<point_avg.size();i++)
		{
			avg = avg+point_avg.get(i);
		//	System.out.println("Averaging");
		}
		avg/=i;
		
		
	//	Sum = Sum - Filtered_Value + getMeasuredPower();  //Make sure the Sum has a big enough variable type!!!! 
	//	Filtered_Value = (int)(((double)Sum)/(Filter_Strength));  //This trick only works for positive integers
		double ratio =avg *1.0/getPower();
		if (ratio < 1.0) {
			//meters= Math.pow(ratio,10);
			meters= 	 (0.89976)*Math.pow(ratio,7.7095) + 0.111; 
		}
		else {
			meters =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;  
		}
		
		System.out.println("POWER: "+ getPower() + " Measured: "+getMeasuredPower()+" Filtered: "+avg);
		
		
			
		double consn = 3.5;
		int cons1 = 3;
		/*
		//if(Filtered_Value>-49)
	//	{
	//		consn = 5;
	//		cons1 = 3;
	//	}
	//	else
	//		{
			consn = 5;
			cons1 = 3;
	//		}
		System.out.println("POWER: "+ getPower() + " MEASURED: "+Filtered_Value);
		meters =(1/(10*consn))*(getPower()-Filtered_Value-cons1+(Math.log(0.12)/Math.log(20))+(Math.log(4*Math.PI/Math.log(20))));
*/
		
		meters = Math.floor(meters * acc) / acc;
		if(meters<0 ) meters = 0.01;
		return meters;
	}
	
	
	public boolean equals(Object o)
	{
		
		if(o instanceof GlimwormBeacon)
		{			
			GlimwormBeacon b = (GlimwormBeacon) o;
			if(b.getDevice().getAddress().trim().equals(device.getAddress().trim()))
			{
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
	public void populateView(ViewHolder vh) {
	        if (name != null && name.length() > 0)
	            vh.deviceName.setText(name);
	        else
	            vh.deviceName.setText(R.string.unknown_device);
	   //     vh.deviceAddress.setText(getDevice().getAddress());
	        vh.rssi.setText(String.valueOf(getDistance()));
	        vh.major.setText(String.valueOf(getMajor()));
	        vh.minor.setText(String.valueOf(getMinor()));
	        if(getBatteryLevel() >= 75) vh.batteryLevel.setImageResource(R.drawable.batt_100_75);
	        else if(getBatteryLevel() >= 50) vh.batteryLevel.setImageResource(R.drawable.batt_75_75);
	        else if(getBatteryLevel() >= 25) vh.batteryLevel.setImageResource(R.drawable.batt_50_75);
	        else if(getBatteryLevel() > 0) vh.batteryLevel.setImageResource(R.drawable.batt_25_75);
	        else vh.batteryLevel.setImageResource(R.drawable.batt_0);
	     //   vh.battery.setText(String.valueOf(getBatteryLevel())+"%");
	}
	
	@Override
	public int compareTo(AbstractBeacon another) {
		if(another instanceof GlimwormBeacon)
		{
			GlimwormBeacon b = (GlimwormBeacon) another;
			int comp = b.getName().compareTo(name);
			if(comp!= 0) return comp;
			Integer major = b.getMajor();
			comp = major.compareTo((Integer)major);
			if(comp!= 0) return comp;
			Integer minor = b.getMinor();
			comp = minor.compareTo((Integer)minor);
            return comp;
		}
		return 0;
	}

}
