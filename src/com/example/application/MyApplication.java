package com.example.application;




import java.util.ArrayList;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.entity.AppInfo;
import com.example.entity.ContactPerson;
import com.example.entity.MyInfo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

//类似全局静态变量，整个程序可以共享这里的数据
public class MyApplication extends Application {
	
	
	
	
	public static Context context;
	
	
	public static String baseurl="http://192.168.1.100:8080/lxvoip/";
	public static ArrayList<Activity> activities = new ArrayList<Activity>();
	
	public static void addActivity(Activity activity)	{
	activities.add(activity);
	}
	
	public static void finishActivity(){
	for (int i = 0; i < activities.size(); i++){
	activities.get(i).finish();
	}

	android.os.Process.killProcess(android.os.Process.myPid());//所有activity关闭后，结束进程

	}
	
	
	
	
	
	//通过服务把数据传过来
public static ArrayList<ContactPerson> pList=new ArrayList<ContactPerson>() ;
		

	public static AppInfo appinfo=new AppInfo();
	public static MyInfo myinfo=new MyInfo();

	
	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("app-开始启动服务！");
		Intent intent=new Intent(MyApplication.this,com.example.service.MyService.class);
		
		startService(intent);
		

	}
	
	

}
