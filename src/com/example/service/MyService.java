package com.example.service;

import java.util.ArrayList;

import com.example.application.MyApplication;
import com.example.entity.ContactPerson;



import android.app.Service;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;

public class MyService extends Service {

	public static final String TAG="MyService";
	MyAsyncQueryHandler asyncQuery;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG,"服务开始启动");
		System.out.println("我的服务开始启动");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG,"服务开始执行任务");
		System.out.println("服务开始执行任务");
		asyncQuery=new MyAsyncQueryHandler(getContentResolver());
		initSQL();
		return super.onStartCommand(intent, flags, startId);
	}

	protected void initSQL() {
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; 
		String[] projection = { 
				ContactsContract.CommonDataKinds.Phone._ID,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.DATA1,
				"sort_key",
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
		}; 
		
		asyncQuery.startQuery(0, null, uri, projection, null, null,
				"sort_key COLLATE LOCALIZED asc");
	}

	private class MyAsyncQueryHandler extends AsyncQueryHandler {
		public MyAsyncQueryHandler(ContentResolver cr) {
			//调用父类带参数的构造方法，创建一个线程
			super(cr);
		}
		
		//asyncQuery.startQuery 查询完毕会调用这个方法
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
		System.out.println("异步查询联系人完毕，开始启动异步执行");	
			
			querying(cursor);
		}
	}

	private void querying(final Cursor cursor){

		Handler handlerInsertOrder = new Handler(){
			
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0x102:
					System.out.println("begin to asynctask");
					break;
					//拿到异步任务取出好的联系人list赋值给MyApplication
				case 0x101:
					Bundle bundle1 = msg.getData();
					ArrayList<ContactPerson> list = (ArrayList<ContactPerson>) bundle1.get("完成");
					
					MyApplication ma = (MyApplication)getApplication();
					
					System.out.println("看看有多少联系人"+list.size());
					
					ma.pList=list;
				
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};

		//这里才是真正执行调用异步加载
		MyAsyncTask.startRequestServerData(MyService.this, handlerInsertOrder, cursor);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void onDestroy() {
	System.out.println("服务销毁！");
		super.onDestroy();
	}

	
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
