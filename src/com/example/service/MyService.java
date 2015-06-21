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
		Log.i(TAG,"����ʼ����");
		System.out.println("�ҵķ���ʼ����");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG,"����ʼִ������");
		System.out.println("����ʼִ������");
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
			//���ø���������Ĺ��췽��������һ���߳�
			super(cr);
		}
		
		//asyncQuery.startQuery ��ѯ��ϻ�����������
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
		System.out.println("�첽��ѯ��ϵ����ϣ���ʼ�����첽ִ��");	
			
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
					//�õ��첽����ȡ���õ���ϵ��list��ֵ��MyApplication
				case 0x101:
					Bundle bundle1 = msg.getData();
					ArrayList<ContactPerson> list = (ArrayList<ContactPerson>) bundle1.get("���");
					
					MyApplication ma = (MyApplication)getApplication();
					
					System.out.println("�����ж�����ϵ��"+list.size());
					
					ma.pList=list;
				
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};

		//�����������ִ�е����첽����
		MyAsyncTask.startRequestServerData(MyService.this, handlerInsertOrder, cursor);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void onDestroy() {
	System.out.println("�������٣�");
		super.onDestroy();
	}

	
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
