package com.dynocloud.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ControlOverride {
	
	String dateTime;
	int	enclosureNodeID;
	int	centralNodeID;
	int	userID;
	
	@JsonProperty("IC_OW")
	int	IC_OW;
	@JsonProperty("IR_OW")
	int	IR_OW;
	@JsonProperty("UV_OW")
	int	UV_OW;
	@JsonProperty("HUM_OW")
	int	HUM_OW;
	@JsonProperty("IC")
	int	IC;
	@JsonProperty("IR")
	int	IR;
	@JsonProperty("UV")
	int	UV;
	@JsonProperty("HUM")
	int	HUM;
	
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
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
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	@JsonProperty("IC_OW")
	public int getIC_OW() {
		return IC_OW;
	}
	@JsonProperty("IC_OW")
	public void setIC_OW(int iC_OW) {
		IC_OW = iC_OW;
	}
	@JsonProperty("IR_OW")
	public int getIR_OW() {
		return IR_OW;
	}
	@JsonProperty("IR_OW")
	public void setIR_OW(int iR_OW) {
		IR_OW = iR_OW;
	}
	@JsonProperty("UV_OW")
	public int getUV_OW() {
		return UV_OW;
	}
	@JsonProperty("UV_OW")
	public void setUV_OW(int uV_OW) {
		UV_OW = uV_OW;
	}
	@JsonProperty("HUM_OW")
	public int getHUM_OW() {
		return HUM_OW;
	}
	@JsonProperty("HUM_OW")
	public void setHUM_OW(int hUM_OW) {
		HUM_OW = hUM_OW;
	}
	@JsonProperty("IC")
	public int getIC() {
		return IC;
	}
	@JsonProperty("IC")
	public void setIC(int iC) {
		IC = iC;
	}
	@JsonProperty("IR")
	public int getIR() {
		return IR;
	}
	@JsonProperty("IR")
	public void setIR(int iR) {
		IR = iR;
	}
	@JsonProperty("UV")
	public int getUV() {
		return UV;
	}
	@JsonProperty("UV")
	public void setUV(int uV) {
		UV = uV;
	}
	@JsonProperty("HUM")
	public int getHUM() {
		return HUM;
	}
	@JsonProperty("HUM")
	public void setHUM(int hUM) {
		HUM = hUM;
	}
	
	@Override
	public String toString() {
		return "ControlOverride [dateTime=" + dateTime + ", enclosureNodeID=" + enclosureNodeID + ", centralNodeID="
				+ centralNodeID + ", userID=" + userID + ", IC_OW=" + IC_OW + ", IR_OW=" + IR_OW + ", UV_OW=" + UV_OW
				+ ", HUM_OW=" + HUM_OW + ", IC=" + IC + ", IR=" + IR + ", UV=" + UV + ", HUM=" + HUM + "]";
	}

}
