package com.dynocloud.server.api;

public class Telemetry {
	
	String DateTime;
	private int CLIENTID;
	private float TEMP;
	private float RH;
	private float IR_PW;
	private float IC_PW;
	private int UV_STATUS;
	private int HUMI_STATUS;
	private int CentralNodeID;
	private int UserID;
	
	
	
	@Override
	public String toString() {
		return "Telemetry [DateTime=" + DateTime + ", CLIENTID=" + CLIENTID + ", TEMP=" + TEMP + ", RH=" + RH
				+ ", IR_PW=" + IR_PW + ", IC_PW=" + IC_PW + ", UV_STATUS=" + UV_STATUS + ", HUMI_STATUS=" + HUMI_STATUS
				+ ", CentralNodeID=" + CentralNodeID + ", UserID=" + UserID + "]";
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

	public String getDateTime() {
		return DateTime;
	}
	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}
	public int getCLIENTID() {
		return CLIENTID;
	}
	public void setCLIENTID(int cLIENTID) {
		CLIENTID = cLIENTID;
	}
	public float getTEMP() {
		return TEMP;
	}
	public void setTEMP(float tEMP) {
		TEMP = tEMP;
	}
	public float getRH() {
		return RH;
	}
	public void setRH(float rH) {
		RH = rH;
	}
	public float getIR_PW() {
		return IR_PW;
	}
	public void setIR_PW(float iR_PW) {
		IR_PW = iR_PW;
	}
	public float getIC_PW() {
		return IC_PW;
	}
	public void setIC_PW(float iC_PW) {
		IC_PW = iC_PW;
	}
	public int getUV_STATUS() {
		return UV_STATUS;
	}
	public void setUV_STATUS(int uV_STATUS) {
		UV_STATUS = uV_STATUS;
	}
	public int getHUMI_STATUS() {
		return HUMI_STATUS;
	}
	public void setHUMI_STATUS(int hUMI_STATUS) {
		HUMI_STATUS = hUMI_STATUS;
	}
	
}
