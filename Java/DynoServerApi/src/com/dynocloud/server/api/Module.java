package com.dynocloud.server.api;

public class Module {

	int EnclosureNodeID;
	int CentralNodeID;
	int UserID;
	String Name;
	int dev_IR;
	int PetProfileID;
	
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
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getDev_IR() {
		return dev_IR;
	}
	public void setDev_IR(int dev_IR) {
		this.dev_IR = dev_IR;
	}
	public int getPetProfileID() {
		return PetProfileID;
	}
	public void setPetProfileID(int petProfileID) {
		PetProfileID = petProfileID;
	}
	
	@Override
	public String toString() {
		return "Module [EnclosureNodeID=" + EnclosureNodeID + ", CentralNodeID=" + CentralNodeID + ", UserID=" + UserID
				+ ", Name=" + Name + ", DEV_IR=" + dev_IR + ", PetProfileID=" + PetProfileID + "]";
	}
	
	
	
}
