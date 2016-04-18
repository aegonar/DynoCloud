package com.dynocloud.node.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Telemetry {
	
	@JsonProperty("DateTime")
	String DateTime;
	@JsonProperty("CLIENTID")
	private int CLIENTID;
	@JsonProperty("TEMP")
	private float TEMP;
	@JsonProperty("RH")
	private float RH;	
	@JsonProperty("IR_PW")
	private float IR_PW;
	@JsonProperty("IC_PW")
	private float IC_PW;
	@JsonProperty("UV_STATUS")
	private int UV_STATUS;
	@JsonProperty("HUMI_STATUS")
	private int HUMI_STATUS;
	
	@Override
	public String toString() {
		return "Telemetry [DateTime=" + DateTime + ", CLIENTID=" + CLIENTID + ", TEMP=" + TEMP + ", RH=" + RH
				+ ", IR_PW=" + IR_PW + ", IC_PW=" + IC_PW + ", UV_STATUS=" + UV_STATUS + ", HUMI_STATUS=" + HUMI_STATUS
				+ "]";
	}
	
	@JsonProperty("DateTime")
	public String getDateTime() {
		return DateTime;
	}
	@JsonProperty("DateTime")
	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}
	@JsonProperty("CLIENTID")
	public int getCLIENTID() {
		return CLIENTID;
	}
	@JsonProperty("CLIENTID")
	public void setCLIENTID(int cLIENTID) {
		CLIENTID = cLIENTID;
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
	@JsonProperty("IR_PW")
	public float getIR_PW() {
		return IR_PW;
	}	
	@JsonProperty("IR_PW")
	public void setIR_PW(float iR_PW) {
		IR_PW = iR_PW;
	}
	@JsonProperty("IC_PW")
	public float getIC_PW() {
		return IC_PW;
	}
	@JsonProperty("IC_PW")
	public void setIC_PW(float iC_PW) {
		IC_PW = iC_PW;
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
	
}
