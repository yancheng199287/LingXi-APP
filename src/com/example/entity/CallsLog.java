package com.example.entity;

public class CallsLog {
	//主叫号码
	private String phoneNumber;
	//姓名
	private String perisonName;
	//联系人id
	private	int id;
	//头像图片
	private int imageHead;
	
	//来电类型，去电 ，未接 已接
	int type;
	
	//时间
	int time;
	String date;
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPerisonName() {
		return perisonName;
	}

	public void setPerisonName(String perisonName) {
		this.perisonName = perisonName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getImageHead() {
		return imageHead;
	}

	public void setImageHead(int imageHead) {
		this.imageHead = imageHead;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	
}
