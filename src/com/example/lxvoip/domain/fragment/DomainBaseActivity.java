package com.example.lxvoip.domain.fragment;

import com.example.lxvoip.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DomainBaseActivity extends Activity {
	TextView tv;
	Button topCallsButton;
	ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.domain_helpcenter);
		tv=(TextView) findViewById(R.id.useKnow);
		topCallsButton=(Button) findViewById(R.id.topCallsButton);
		back=(ImageView) findViewById(R.id.back);
		
	
back.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				finish();
					
				}
			});
		
	}
	
	
	
	public void setMyData(String bu,String txt){
		topCallsButton.setText(bu);
		tv.setText(txt);
	}
	
	
	
}
