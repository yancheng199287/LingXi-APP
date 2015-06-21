package com.example.entity;

public class ContactPerson {

	//主叫号码
	private String phoneNumber;
	//姓名
	private String perisonName;
	//联系人id
	private	int id;
	//头像图片
	private int imageHead;

	//主要用来做 T9数字键快速检索的
	private String FormattedNumber;
	
	//名字的拼音
	private String Pinyin;
	
	//首字母
	private String sortkey;
	
	
	public String getSortkey() {
		return sortkey;
	}

	public void setSortkey(String sortkey) {
		this.sortkey = sortkey;
	}

	public String getPinyin() {
		return Pinyin;
	}

	public void setPinyin(String pinyin) {
		Pinyin = pinyin;
	}

	public String getFormattedNumber() {
		return FormattedNumber;
	}

	public void setFormattedNumber(String formattedNumber) {
		FormattedNumber = formattedNumber;
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
	
	
	
}
