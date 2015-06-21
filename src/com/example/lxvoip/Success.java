package com.example.lxvoip;

import com.example.lxvoip.domain.fragment.DomainBaseActivity;

import android.app.Activity;
import android.os.Bundle;

public class Success extends DomainBaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setMyData("回拨成功", "回拨不行吗？");
	}

}
