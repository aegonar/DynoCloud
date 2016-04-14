package com.dynocloud.node.api;

public class PetProfile {
	
	int PetProfileID ;
	//int UserID;
	String  Name;
	float  Day_Temperature_SP;
	float Day_Humidity_SP;
	float Night_Temperature_SP;
	float Night_Humidity_SP;
	float Temperature_TH;
	float Humidity_TH;
	
	public int getPetProfileID() {
		return PetProfileID;
	}

	public void setPetProfileID(int petProfileID) {
		PetProfileID = petProfileID;
	}

//	public int getUserID() {
//		return UserID;
//	}
//
//	public void setUserID(int userID) {
//		UserID = userID;
//	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public float getDay_Temperature_SP() {
		return Day_Temperature_SP;
	}

	public void setDay_Temperature_SP(float day_Temperature_SP) {
		Day_Temperature_SP = day_Temperature_SP;
	}

	public float getDay_Humidity_SP() {
		return Day_Humidity_SP;
	}

	public void setDay_Humidity_SP(float day_Humidity_SP) {
		Day_Humidity_SP = day_Humidity_SP;
	}

	public float getNight_Temperature_SP() {
		return Night_Temperature_SP;
	}

	public void setNight_Temperature_SP(float night_Temperature_SP) {
		Night_Temperature_SP = night_Temperature_SP;
	}

	public float getNight_Humidity_SP() {
		return Night_Humidity_SP;
	}

	public void setNight_Humidity_SP(float night_Humidity_SP) {
		Night_Humidity_SP = night_Humidity_SP;
	}

	public float getTemperature_TH() {
		return Temperature_TH;
	}

	public void setTemperature_TH(float temperature_TH) {
		Temperature_TH = temperature_TH;
	}

	public float getHumidity_TH() {
		return Humidity_TH;
	}

	public void setHumidity_TH(float humidity_TH) {
		Humidity_TH = humidity_TH;
	}

	@Override
	public String toString() {
		return "PetProfiles [ID=" + PetProfileID + ", Name=" + Name + ", Day_Temperature_SP="
				+ Day_Temperature_SP + ", Day_Humidity_SP=" + Day_Humidity_SP + ", Night_Temperature_SP="
				+ Night_Temperature_SP + ", Night_Humidity_SP=" + Night_Humidity_SP + ", Temperature_TH="
				+ Temperature_TH + ", Humidity_TH=" + Humidity_TH + "]";
	}
	
	
}
