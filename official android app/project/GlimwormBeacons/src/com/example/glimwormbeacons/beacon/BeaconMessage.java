package com.example.glimwormbeacons.beacon;

public class BeaconMessage {
	private String message = "";
	private String response = "";
	
	public BeaconMessage(String message, String response)
	{
		this.message = message;
		this.response = response;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public String getResponse()
	{
		return response;
	}
}
