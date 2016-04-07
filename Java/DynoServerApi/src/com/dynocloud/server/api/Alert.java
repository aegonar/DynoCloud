package com.dynocloud.server.api;

public class Alert {
	
	int AlertID;
	int EnclosureNodeID;
	int CentralNodeID;
	int UserID;
	String Date;
	String Message;
	String Destination;
	
	public int getAlertID() {
		return AlertID;
	}
	public void setAlertID(int alertID) {
		AlertID = alertID;
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
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getDestination() {
		return Destination;
	}
	public void setDestination(String destination) {
		Destination = destination;
	}
	@Override
	public String toString() {
		return "Alert [AlertID=" + AlertID + ", EnclosureNodeID=" + EnclosureNodeID + ", CentralNodeID=" + CentralNodeID
				+ ", UserID=" + UserID + ", Date=" + Date + ", Message=" + Message + ", Destination=" + Destination
				+ "]";
	}
	
	


}
