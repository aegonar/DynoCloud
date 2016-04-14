package com.dynocloud.node.api;

public class Overview {
	
	int EnclosureNodeID;
	String DateTime;
	float Temperature;
	float Humidity;
	float Load_IR;
	float Load_IC;
	int State_UV;
	int State_HUM;
	String EnclosureName;
	int PetProfileID;
	String ProfileName;
	
	public int getEnclosureNodeID() {
		return EnclosureNodeID;
	}
	public void setEnclosureNodeID(int enclosureNodeID) {
		EnclosureNodeID = enclosureNodeID;
	}
	public String getDateTime() {
		return DateTime;
	}
	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}
	public float getTemperature() {
		return Temperature;
	}
	public void setTemperature(float temperature) {
		Temperature = temperature;
	}
	public float getHumidity() {
		return Humidity;
	}
	public void setHumidity(float humidity) {
		Humidity = humidity;
	}
	public float getLoad_IR() {
		return Load_IR;
	}
	public void setLoad_IR(float load_IR) {
		Load_IR = load_IR;
	}
	public float getLoad_IC() {
		return Load_IC;
	}
	public void setLoad_IC(float load_IC) {
		Load_IC = load_IC;
	}
	public int getState_UV() {
		return State_UV;
	}
	public void setState_UV(int state_UV) {
		State_UV = state_UV;
	}
	public int getState_HUM() {
		return State_HUM;
	}
	public void setState_HUM(int state_HUM) {
		State_HUM = state_HUM;
	}
	public String getEnclosureName() {
		return EnclosureName;
	}
	public void setEnclosureName(String enclosureName) {
		EnclosureName = enclosureName;
	}
	public int getPetProfileID() {
		return PetProfileID;
	}
	public void setPetProfileID(int petProfileID) {
		PetProfileID = petProfileID;
	}
	public String getProfileName() {
		return ProfileName;
	}
	public void setProfileName(String profileName) {
		ProfileName = profileName;
	}
		
}
