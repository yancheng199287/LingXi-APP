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
			//幻灯片图片资源
			private int [] imgArray={R.drawable.guide_0,R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
			//图片数组
			private ArrayList<View> viewlist;

			private ImageView imageView;
			//点点
			private ViewGroup pointgroup;
			private ImageView[] pointarr;
     
			 //设置开启进入按钮
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
				
				
				
				//初始化
				viewpager =(ViewPager) findViewById(R.id.viewpager);
				pointgroup =(ViewGroup) findViewById(R.id.pointgroup);
				
				start_enter=(Button) findViewById(R.id.start_enter);	
				
				viewlist = new ArrayList<View>();
				
				//将图片装载到数组	
				for(int i=0;i<imgArray.length;i++){
					imageView =new ImageView(this);
					//设置图片
					imageView.setBackgroundResource(imgArray[i]);
					//设置图片id
					imageView.setId(imgArray[i]);
					viewlist.add(imageView);
					
				}
				
				/*
				 * viewpager加载适配器
				 * MyPageAdapter为自定义适配器
				 * */
				
				//传递上下文
				Context context = StartDoor.this;
				MyPageAdapter pageadapter2 =new MyPageAdapter(viewlist,context);
				viewpager.setAdapter(pageadapter2);		
				
				
				//点数组
				pointarr=new ImageView[imgArray.length];
				//把点加入布局，和设置点状态
				for(int i=0;i<imgArray.length;i++){
					ImageView point=new ImageView(this);		
					
					//设置点大小
					point.setLayoutParams(new LayoutParams(25,25)); 
				
					pointarr[i]=point;
					//状态处理
					if(i==0){
						pointarr[i].setBackgroundResource(R.drawable.viewpage_point_focused);
					}else{
						pointarr[i].setBackgroundResource(R.drawable.viewpage_point_unfocused);
					}
					//加入到容器
					pointgroup.addView(pointarr[i]);
				}
				
			
				//viewpager设置监听器
				pointChangeListener pointListener=new pointChangeListener();
				viewpager.setOnPageChangeListener(pointListener);
				//点击图片
				
				
				
				
				start_enter.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent=new Intent(StartDoor.this,Login.class);
						
						startActivity(intent);
						finish();
						
					Toast.makeText(StartDoor.this, "好了，打开程序啦", 3000).show();
						
					}
				});
				
			}	
			
			
			
			
			
			/*
			 * viewpage监听器
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
				//比如切换到第二张图片，相应索引的点为选择状态，其他为未选中状态
				public void onPageSelected(int arg0) {
					
					if(arg0==3){
						
						start_enter.setVisibility(View.VISIBLE);//mlistview1处于可见状态
					}else{
						start_enter.setVisibility(View.GONE);//mlistview1处于bu可见状态
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
				
			
			
			
			
			
		//判断当前是否是第一次进入程序
		public boolean isFirst(){
		  boolean is=true;
			
		 //获取到sharepreference 对象， 参数一为xml文件名，参数为文件的可操作模式  当前是私有的
			SharedPreferences  sp=getSharedPreferences("isfirst",MODE_PRIVATE);
			
			//获得之前存取的值。第二个参数是一个默认值，如果不存在就取出这个
			
			is=sp.getBoolean("isfirst", false);
			
		if(is==false){
			//如果取不到值，那么就是默认是第一次登录，开始赋值
			//获取到编辑对象  
	        SharedPreferences.Editor edit = sp.edit();
	        
	        //添加新的值，可见是键值对的形式添加  
	        edit.putBoolean("isfirst", true); 
	 
	        //提交.  
	        edit.commit();  
			
	        
			return false;
		}	
			
		
			return true;
		}	
			
			
			
			
		
	}
	

	



