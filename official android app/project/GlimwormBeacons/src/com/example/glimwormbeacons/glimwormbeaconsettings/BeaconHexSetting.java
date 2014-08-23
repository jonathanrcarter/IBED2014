package com.example.glimwormbeacons.glimwormbeaconsettings;

public class BeaconHexSetting extends BeaconTextSetting {

	public BeaconHexSetting(String name, String commandString, String returnString, String defaultValue,boolean readOnly,boolean visible, String description) {
		super(name, commandString, returnString, defaultValue,readOnly,visible,description);
	}

	public String formatOutput(String input) {
		return String.valueOf(Integer.decode(input));
	}
	
	public String formatInput(String input) {
		String hex_string = Integer.toHexString(Integer.valueOf(input));
		while (hex_string.length() < 4) {
			hex_string = "0" + hex_string;
		}
		return "0x"+hex_string; 
	}

}
