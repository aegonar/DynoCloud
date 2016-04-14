package com.dynocloud.server.api;

public class Metrics {
	
	String DateTime;
	int EnclosureNodeID;
	int CentralNodeID;
	int UserID;
	float Temperature;
	float Humidity;
	float Load_IR;
	float Load_IC;
	int State_UV;
	int State_HUM;
	
	public String getDateTime() {
		return DateTime;
	}
	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}
	public int getEnclosureNodeID() {
		return EnclosureNodeID;
	}
	public void setEnclosureNodeID(int enclosureNodeID) {
		EnclosureNodeID = enclosureNodeID;
	}
	public int getCentralNodeID() {
		return CentralNodeID;
	}
	public void setCentralNodeID(int centralNodeID) {
		CentralNodeID = centralNodeID;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
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
	@Override
	public String toString() {
		return "Metrics [EnclosureNodeID=" + EnclosureNodeID + ", CentralNodeID=" + CentralNodeID + ", UserID=" + UserID
				+ ", Temperature=" + Temperature + ", Humidity=" + Humidity + ", Load_IR=" + Load_IR + ", Load_IC="
				+ Load_IC + ", State_UV=" + State_UV + ", State_HUM=" + State_HUM + "]";
	}
	
	

}
