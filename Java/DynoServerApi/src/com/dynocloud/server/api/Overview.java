package com.dynocloud.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Overview {
	
	int userID;
	int enclosureNodeID;
	int centralNodeID;
	
	String dateTime;
	
	@JsonProperty("OPTIONAL_LOAD_TYPE")
	int OPTIONAL_LOAD_TYPE;
	
	@JsonProperty("TEMP")
	float TEMP;
	@JsonProperty("RH")
	float RH;
	@JsonProperty("OPTIONAL_LOAD")
	float OPTIONAL_LOAD;
	@JsonProperty("HEAT_LOAD")
	float HEAT_LOAD;
	@JsonProperty("UV_STATUS")
	int UV_STATUS;
	@JsonProperty("HUM_STATUS")
	int HUMI_STATUS;
	
	String enclosureName;
	int petProfileID;
	String profileName;
	
	float day_Temperature_SP;
	float day_Humidity_SP;
	float night_Temperature_SP;
	float night_Humidity_SP;
	float temperature_TH;
	float humidity_TH;
	
	
	public float getDay_Temperature_SP() {
		return day_Temperature_SP;
	}
	public void setDay_Temperature_SP(float day_Temperature_SP) {
		this.day_Temperature_SP = day_Temperature_SP;
	}
	public float getDay_Humidity_SP() {
		return day_Humidity_SP;
	}
	public void setDay_Humidity_SP(float day_Humidity_SP) {
		this.day_Humidity_SP = day_Humidity_SP;
	}
	public float getNight_Temperature_SP() {
		return night_Temperature_SP;
	}
	public void setNight_Temperature_SP(float night_Temperature_SP) {
		this.night_Temperature_SP = night_Temperature_SP;
	}
	public float getNight_Humidity_SP() {
		return night_Humidity_SP;
	}
	public void setNight_Humidity_SP(float night_Humidity_SP) {
		this.night_Humidity_SP = night_Humidity_SP;
	}
	public float getTemperature_TH() {
		return temperature_TH;
	}
	public void setTemperature_TH(float temperature_TH) {
		this.temperature_TH = temperature_TH;
	}
	public float getHumidity_TH() {
		return humidity_TH;
	}
	public void setHumidity_TH(float humidity_TH) {
		this.humidity_TH = humidity_TH;
	}
	@JsonProperty("OPTIONAL_LOAD_TYPE")
	public int getOPTIONAL_LOAD_TYPE() {
		return OPTIONAL_LOAD_TYPE;
	}
	@JsonProperty("OPTIONAL_LOAD_TYPE")
	public void setOPTIONAL_LOAD_TYPE(int oPTIONAL_LOAD_TYPE) {
		OPTIONAL_LOAD_TYPE = oPTIONAL_LOAD_TYPE;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getEnclosureNodeID() {
		return enclosureNodeID;
	}
	public void setEnclosureNodeID(int enclosureNodeID) {
		this.enclosureNodeID = enclosureNodeID;
	}
	public int getCentralNodeID() {
		return centralNodeID;
	}
	public void setCentralNodeID(int centralNodeID) {
		this.centralNodeID = centralNodeID;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	@JsonProperty("TEMP")
	public float getTEMP() {
		return TEMP;
	}
	@JsonProperty("TEMP")
	public void setTEMP(float tEMP) {
		TEMP = tEMP;
	}
	@JsonProperty("RH")
	public float getRH() {
		return RH;
	}
	@JsonProperty("RH")
	public void setRH(float rH) {
		RH = rH;
	}
	@JsonProperty("OPTIONAL_LOAD")
	public float getOPTIONAL_LOAD() {
		return OPTIONAL_LOAD;
	}
	@JsonProperty("OPTIONAL_LOAD")
	public void setOPTIONAL_LOAD(float oPTIONAL_LOAD) {
		OPTIONAL_LOAD = oPTIONAL_LOAD;
	}
	@JsonProperty("HEAT_LOAD")
	public float getHEAT_LOAD() {
		return HEAT_LOAD;
	}
	@JsonProperty("HEAT_LOAD")
	public void setHEAT_LOAD(float hEAT_LOAD) {
		HEAT_LOAD = hEAT_LOAD;
	}
	@JsonProperty("UV_STATUS")
	public int getUV_STATUS() {
		return UV_STATUS;
	}
	@JsonProperty("UV_STATUS")
	public void setUV_STATUS(int uV_STATUS) {
		UV_STATUS = uV_STATUS;
	}
	@JsonProperty("HUMI_STATUS")
	public int getHUMI_STATUS() {
		return HUMI_STATUS;
	}
	@JsonProperty("HUMI_STATUS")
	public void setHUMI_STATUS(int hUMI_STATUS) {
		HUMI_STATUS = hUMI_STATUS;
	}
	public String getEnclosureName() {
		return enclosureName;
	}
	public void setEnclosureName(String enclosureName) {
		this.enclosureName = enclosureName;
	}
	public int getPetProfileID() {
		return petProfileID;
	}
	public void setPetProfileID(int petProfileID) {
		this.petProfileID = petProfileID;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	@Override
	public String toString() {
		return "Overview [userID=" + userID + ", enclosureNodeID=" + enclosureNodeID + ", centralNodeID="
				+ centralNodeID + ", dateTime=" + dateTime + ", TEMP=" + TEMP + ", RH=" + RH + ", OPTIONAL_LOAD="
				+ OPTIONAL_LOAD + ", HEAT_LOAD=" + HEAT_LOAD + ", UV_STATUS=" + UV_STATUS + ", HUMI_STATUS="
				+ HUMI_STATUS + ", enclosureName=" + enclosureName + ", petProfileID=" + petProfileID + ", profileName="
				+ profileName + "]";
	}
		
}
