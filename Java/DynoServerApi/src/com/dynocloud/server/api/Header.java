package com.dynocloud.server.api;

public class Header {
	
	String key;
	String value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "header [key=" + key + ", value=" + value + "]";
	}
	
	

}
