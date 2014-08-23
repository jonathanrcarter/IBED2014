package com.example.glimwormbeacons.glimwormbeaconsettings;

import java.util.ArrayList;
import java.util.List;

import com.example.glimwormbeacons.configuring.BeaconConnection;
import com.example.glimwormbeacons.glimwormbeaconsettings.BeaconHexSetting;
import com.example.glimwormbeacons.glimwormbeaconsettings.BeaconIntegerSetting;
import com.example.glimwormbeacons.glimwormbeaconsettings.BeaconPinSetting;
import com.example.glimwormbeacons.glimwormbeaconsettings.AbstractBeaconSetting;
import com.example.glimwormbeacons.glimwormbeaconsettings.BeaconSliderSetting;
import com.example.glimwormbeacons.glimwormbeaconsettings.BeaconTextSetting;

public class GlimwormBeaconSettings {
	List<AbstractBeaconSetting> settings = new ArrayList<AbstractBeaconSetting>();
	
	public GlimwormBeaconSettings()
	{
		settings.add(new BeaconIntegerSetting("Battery","AT+BATT","OK+Get:","Fetching battery level",true,true,""));
		settings.add(new BeaconTextSetting("Name","AT+NAME","OK+NAME:","Fetching name",false,true,"Changing the device name affects the name that is broadcasted by this beacon"));
		settings.add(new BeaconHexSetting("Major","AT+MARJ","OK+Get:","0",false,true,"Changing the Major number of the beacon affects the identification of the beacon"));
		settings.add(new BeaconHexSetting("Minor","AT+MINO","OK+Get:","0",false,true,"Changing the Major number of the beacon affects the identification of the beacon"));
		String items[] = {"<2 meters","<10 meters", "<50 meters", "< 100 meters"};
		settings.add(new BeaconSliderSetting("Transmission Power","AT+POWE","OK+Get:","2",items,false,true,"Changing the transmission power affects the signal range of the beacon, a higher value negatively impact the beacon battery life."));
		String items2[] = {"100ms","152.5ms", "211.25ms", "318.75ms", "417.5ms", "546.25ms", "760ms", "852.5ms", "1022.4ms","1285ms","2000ms","3000ms","4000ms","5000ms","6000ms","7000ms"};
		settings.add(new BeaconSliderSetting("Advertisement Interval","AT+ADVI","OK+Get:","9",items2,false,true,"Changing the advertisement interval affects the speed of discovery and location determination percision of the beacon, a lower value negatively impacts the battery life. Choose a value equal to or lower than 1285 for IOS beacon support"));
		settings.add(new BeaconPinSetting("Pincode","AT+TYPE","OK+Get:","000000",false,true,"Setting a pin code secures the beacon from unauthorized settings altering. Please be aware that if this beacon is part of a network, changing this pin could make it unaccesable to the ripple programming process. Leave blank to turn off the pin code feature"));
		settings.add(new BeaconLedSetting("ledoff","OK+LOST","","",false,false,""));
		settings.add(new BeaconResetSetting("Reset","AT+RESET","OK+RESET","",false,false,""));
		
	}
	
	
	public List<AbstractBeaconSetting> getSettings()
	{
		return settings;
	}
	
	public List<AbstractBeaconSetting> getVisibleSettings()
	{
		List<AbstractBeaconSetting> visibleList = new ArrayList<AbstractBeaconSetting>();
		for(AbstractBeaconSetting abs : settings)
		{
			if(abs.isVisible()) visibleList.add(abs);
		}
		return visibleList;
	}
	
	public void fetchValues(BeaconConnection bc)
	{
		for(AbstractBeaconSetting bs:settings)
		{
			bs.getValue(bc);
		}
	}
	
	public void setValues(BeaconConnection bc)
	{
		for(AbstractBeaconSetting bs:settings)
		{
			if(!bs.isReadOnly()) bs.setValue(bc);
		}
		
	}
	
}
