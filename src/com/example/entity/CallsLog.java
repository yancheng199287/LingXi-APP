package com.example.entity;

public class CallsLog {
	//���к���
	private String phoneNumber;
	//����
	private String perisonName;
	//��ϵ��id
	private	int id;
	//ͷ��ͼƬ
	private int imageHead;
	
	//�������ͣ�ȥ�� ��δ�� �ѽ�
	int type;
	
	//ʱ��
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
