package com.example.entity;

public class ContactPerson {

	//���к���
	private String phoneNumber;
	//����
	private String perisonName;
	//��ϵ��id
	private	int id;
	//ͷ��ͼƬ
	private int imageHead;

	//��Ҫ������ T9���ּ����ټ�����
	private String FormattedNumber;
	
	//���ֵ�ƴ��
	private String Pinyin;
	
	//����ĸ
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
