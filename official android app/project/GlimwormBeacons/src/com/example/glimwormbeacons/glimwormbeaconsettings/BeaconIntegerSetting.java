package com.example.glimwormbeacons.glimwormbeaconsettings;
public class BeaconIntegerSetting extends BeaconTextSetting {

	public BeaconIntegerSetting(String name, String commandString, String returnString, String defaultValue,boolean readOnly,boolean visible, String description) {
		super(name, commandString, returnString, defaultValue,readOnly,visible,description);
	}

	public String formatOutput(String input) {
		return input.replaceFirst("^0+(?!$)", "")+"%";
	}

}
