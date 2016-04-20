package com.dynocloud.server.api;


public class ControlOverride {
	
	String DateTime;
	int	EnclosureNodeID ;
	int	CentralNodeID ;
	int	UserID ;
	int	ic_OW;
	int	ir_OW;
	int	uv_OW;
	int	hum_OW;
	int	ic;
	int	ir;
	int	uv;
	int	hum;
	
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
	public int getIC_OW() {
		return ic_OW;
	}
	public void setIC_OW(int iC_OW) {
		ic_OW = iC_OW;
	}
	public int getIR_OW() {
		return ir_OW;
	}
	public void setIR_OW(int iR_OW) {
		ir_OW = iR_OW;
	}
	public int getUV_OW() {
		return uv_OW;
	}
	public void setUV_OW(int uV_OW) {
		uv_OW = uV_OW;
	}
	public int getHUM_OW() {
		return hum_OW;
	}
	public void setHUM_OW(int hUM_OW) {
		hum_OW = hUM_OW;
	}
	public int getIC() {
		return ic;
	}
	public void setIC(int iC) {
		ic = iC;
	}
	public int getIR() {
		return ir;
	}
	public void setIR(int iR) {
		ir = iR;
	}
	public int getUV() {
		return uv;
	}
	public void setUV(int uV) {
		uv = uV;
	}
	public int getHUM() {
		return hum;
	}
	public void setHUM(int hUM) {
		hum = hUM;
	}
	@Override
	public String toString() {
		return "ControlOverride [DateTime=" + DateTime + ", EnclosureNodeID=" + EnclosureNodeID + ", CentralNodeID="
				+ CentralNodeID + ", UserID=" + UserID + ", ic_OW=" + ic_OW + ", ir_OW=" + ir_OW + ", uv_OW=" + uv_OW
				+ ", hum_OW=" + hum_OW + ", ic=" + ic + ", ir=" + ir + ", uv=" + uv + ", hum=" + hum + "]";
	}
}