package com.dynocloud.node.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Module {

	int enclosureNodeID;
	String name;
	@JsonProperty("OPTIONAL_LOAD")
	int OPTIONAL_LOAD;
	int petProfileID;
	
	@Override
	public String toString() {
		return "Module [enclosureNodeID=" + enclosureNodeID + ", name=" + name + ", OPTIONAL_LOAD=" + OPTIONAL_LOAD
				+ ", petProfileID=" + petProfileID + "]";
	}
	
	public int getEnclosureNodeID() {
		return enclosureNodeID;
	}
	public void setEnclosureNodeID(int enclosureNodeID) {
		this.enclosureNodeID = enclosureNodeID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonProperty("OPTIONAL_LOAD")
	public int getOPTIONAL_LOAD() {
		return OPTIONAL_LOAD;
	}
	@JsonProperty("OPTIONAL_LOAD")
	public void setOPTIONAL_LOAD(int oPTIONAL_LOAD) {
		OPTIONAL_LOAD = oPTIONAL_LOAD;
	}
	public int getPetProfileID() {
		return petProfileID;
	}
	public void setPetProfileID(int petProfileID) {
		this.petProfileID = petProfileID;
	}
	
	
	

	
}
