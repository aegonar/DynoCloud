package com.dynocloud.node.api;

public class Module {

	int EnclosureNodeID;
	String Name;
	int dev_IR;
	int PetProfileID;
	
	public int getEnclosureNodeID() {
		return EnclosureNodeID;
	}
	public void setEnclosureNodeID(int enclosureNodeID) {
		EnclosureNodeID = enclosureNodeID;
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
	
}
