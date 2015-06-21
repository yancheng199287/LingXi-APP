package com.example.lxvoip.domain.fragment;

import java.util.ArrayList;

import com.example.application.MyApplication;
import com.example.lxvoip.Login;
import com.example.lxvoip.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PersonCenter extends Activity {
	
	
ImageView headImage,back;	
TextView nicheng;
Button destoryAccount;
ListView personLv;


	
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			MyApplication.addActivity(this);
			setContentView(R.layout.domain_personcenter);
			init();
			
			destoryAccount.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					zhuxiao();
					
				}
			});
			
			
			back.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				finish();
					
				}
			});
		}
	
	
public void init(){
	headImage=(ImageView) findViewById(R.id.headImage);
	nicheng=(TextView) findViewById(R.id.nicheng);
	personLv=(ListView) findViewById(R.id.personLv);
	destoryAccount=(Button) findViewById(R.id.destoryAccount);
	back=(ImageView) findViewById(R.id.back);
	 setData();
}	



public void setData(){
	nicheng.setText("�ǳ��ǣ�"+MyApplication.myinfo.getUserName());
	ArrayList<String> list = new ArrayList<String>();
	list.add("�˻�ID:"+MyApplication.myinfo.getId()*10000);
	list.add("��ǰ�˻�:"+MyApplication.myinfo.getPhoneNumber());
	list.add("�ܻ���:"+MyApplication.myinfo.getScore());
	
	if(MyApplication.myinfo.getIsVIP()==1){
		list.add("��Ŀǰ������VIP�û�");
	}else{
		list.add("��������VIP�û�");
	}
	
	
	
	ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.domain_personcenter_item,list);
	personLv.setAdapter(adapter2);
}

	
	public void zhuxiao(){
		//�������
		SharedPreferences sp=getSharedPreferences("loginConfig", MODE_PRIVATE);
		 SharedPreferences.Editor edit = sp.edit();
		 edit.clear();  
		 edit.commit();  
	        //�ύ.  
	        edit.commit();  
	        
	       Intent intent=new Intent(this,Login.class);
	       startActivity(intent);
	        
	        //MyApplication.finishActivity();    

	}
	
	

}
