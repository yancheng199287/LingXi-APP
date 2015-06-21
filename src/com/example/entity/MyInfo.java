package com.example.entity;

public class MyInfo {
	//用户的个人信息
	private int id;
	private String userName;
	private String phoneNumber;
	private int score;
	private int isVIP;
	private String headPhoto;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getIsVIP() {
		return isVIP;
	}
	public void setIsVIP(int isVIP) {
		this.isVIP = isVIP;
	}
	public String getHeadPhoto() {
		return headPhoto;
	}
	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
