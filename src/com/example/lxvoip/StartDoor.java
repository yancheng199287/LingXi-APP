package com.example.lxvoip;

import java.util.ArrayList;

import com.example.application.MyApplication;
import com.example.lxvoip.adapter.MyPageAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class StartDoor extends Activity {
	//ViewPager 
			private ViewPager viewpager;	
			//�õ�ƬͼƬ��Դ
			private int [] imgArray={R.drawable.guide_0,R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
			//ͼƬ����
			private ArrayList<View> viewlist;

			private ImageView imageView;
			//���
			private ViewGroup pointgroup;
			private ImageView[] pointarr;
     
			 //���ÿ������밴ť
			Button start_enter;
			

			@Override
			protected void onCreate(Bundle savedInstanceState) {
				// TODO Auto-generated method stub
				super.onCreate(savedInstanceState);
				setContentView(R.layout.start_group);
				MyApplication.addActivity(this);
			if(isFirst()){
				Intent intent=new Intent(StartDoor.this,Login.class);
				
				startActivity(intent);
				finish();
			}
				
				
				
				//��ʼ��
				viewpager =(ViewPager) findViewById(R.id.viewpager);
				pointgroup =(ViewGroup) findViewById(R.id.pointgroup);
				
				start_enter=(Button) findViewById(R.id.start_enter);	
				
				viewlist = new ArrayList<View>();
				
				//��ͼƬװ�ص�����	
				for(int i=0;i<imgArray.length;i++){
					imageView =new ImageView(this);
					//����ͼƬ
					imageView.setBackgroundResource(imgArray[i]);
					//����ͼƬid
					imageView.setId(imgArray[i]);
					viewlist.add(imageView);
					
				}
				
				/*
				 * viewpager����������
				 * MyPageAdapterΪ�Զ���������
				 * */
				
				//����������
				Context context = StartDoor.this;
				MyPageAdapter pageadapter2 =new MyPageAdapter(viewlist,context);
				viewpager.setAdapter(pageadapter2);		
				
				
				//������
				pointarr=new ImageView[imgArray.length];
				//�ѵ���벼�֣������õ�״̬
				for(int i=0;i<imgArray.length;i++){
					ImageView point=new ImageView(this);		
					
					//���õ��С
					point.setLayoutParams(new LayoutParams(25,25)); 
				
					pointarr[i]=point;
					//״̬����
					if(i==0){
						pointarr[i].setBackgroundResource(R.drawable.viewpage_point_focused);
					}else{
						pointarr[i].setBackgroundResource(R.drawable.viewpage_point_unfocused);
					}
					//���뵽����
					pointgroup.addView(pointarr[i]);
				}
				
			
				//viewpager���ü�����
				pointChangeListener pointListener=new pointChangeListener();
				viewpager.setOnPageChangeListener(pointListener);
				//���ͼƬ
				
				
				
				
				start_enter.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent=new Intent(StartDoor.this,Login.class);
						
						startActivity(intent);
						finish();
						
					Toast.makeText(StartDoor.this, "���ˣ��򿪳�����", 3000).show();
						
					}
				});
				
			}	
			
			
			
			
			
			/*
			 * viewpage������
			 */
			
			public class pointChangeListener implements OnPageChangeListener{

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}

				@Override
				//�����л����ڶ���ͼƬ����Ӧ�����ĵ�Ϊѡ��״̬������Ϊδѡ��״̬
				public void onPageSelected(int arg0) {
					
					if(arg0==3){
						
						start_enter.setVisibility(View.VISIBLE);//mlistview1���ڿɼ�״̬
					}else{
						start_enter.setVisibility(View.GONE);//mlistview1����bu�ɼ�״̬
					}
					
					
					
					// TODO Auto-generated method stub
					for(int i=0;i<pointarr.length;i++){
						
						
						pointarr[arg0].setBackgroundResource(R.drawable.viewpage_point_focused);	
						
						if(arg0 !=i){
						pointarr[i].setBackgroundResource(R.drawable.viewpage_point_unfocused);	
						}
						
						
					}
					
				}
				
				
			}
				
			
			
			
			
			
		//�жϵ�ǰ�Ƿ��ǵ�һ�ν������
		public boolean isFirst(){
		  boolean is=true;
			
		 //��ȡ��sharepreference ���� ����һΪxml�ļ���������Ϊ�ļ��Ŀɲ���ģʽ  ��ǰ��˽�е�
			SharedPreferences  sp=getSharedPreferences("isfirst",MODE_PRIVATE);
			
			//���֮ǰ��ȡ��ֵ���ڶ���������һ��Ĭ��ֵ����������ھ�ȡ�����
			
			is=sp.getBoolean("isfirst", false);
			
		if(is==false){
			//���ȡ����ֵ����ô����Ĭ���ǵ�һ�ε�¼����ʼ��ֵ
			//��ȡ���༭����  
	        SharedPreferences.Editor edit = sp.edit();
	        
	        //����µ�ֵ���ɼ��Ǽ�ֵ�Ե���ʽ���  
	        edit.putBoolean("isfirst", true); 
	 
	        //�ύ.  
	        edit.commit();  
			
	        
			return false;
		}	
			
		
			return true;
		}	
			
			
			
			
		
	}
	

	



