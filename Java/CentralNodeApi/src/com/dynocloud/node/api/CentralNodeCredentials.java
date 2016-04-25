package com.dynocloud.node.api;

public class CentralNodeCredentials {
	
	int centralNodeID;
	int userID;
	String token;
	
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "CentralNodeCredentials [centralNodeID=" + centralNodeID + ", userID=" + userID + ", token=" + token
				+ "]";
	}
}
