package com.example.lxvoip.domain.fragment;

import com.example.application.MyApplication;
import com.example.lxvoip.R;

import android.app.Activity;
import android.os.Bundle;

public class About extends DomainBaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		super.setMyData("关于我们", "\n\t\t"+MyApplication.appinfo.getAbout());

	}
	
	
	

}
