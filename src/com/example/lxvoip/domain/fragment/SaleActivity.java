package com.example.lxvoip.domain.fragment;

import com.example.application.MyApplication;
import com.example.lxvoip.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SaleActivity extends DomainBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setMyData("�Żݻ", "\n\t\t"+MyApplication.appinfo.getSaleActivity());

	}
}
