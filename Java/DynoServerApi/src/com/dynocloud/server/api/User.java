package com.dynocloud.server.api;

public class User {
	private int UserID;
	private String UserName;
	private String Password;
	private String Name;
	private String LastName;
	private String Email;
	private String Phone;
	
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	
	@Override
	public String toString() {
		return "User [UserID=" + UserID + ", UserName=" + UserName + ", Name=" + Name + ", LastName=" + LastName
				+ ", Email=" + Email + ", Phone=" + Phone + "]";
	}

}
