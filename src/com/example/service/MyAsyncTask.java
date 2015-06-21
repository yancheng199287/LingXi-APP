package com.example.service;

import java.util.ArrayList;

import com.example.entity.ContactPerson;
import com.example.lxvoip.utils.ToPinYin;



import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;



import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MyAsyncTask extends AsyncTask<Cursor, Void, ArrayList<ContactPerson>> {
	public static String TAG = "MyAsyncTask";
	
	public Handler handler;
	public Context context;
	
	
	//ͨ�����췽��,���handler����(��Ҫ�Ƕ��䷢����Ϣ�����䴦��)���������Ķ���
	public MyAsyncTask(Context context,Handler handler){
		this.handler=handler;
		this.context=context;
	}
	
	
	
	

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Log.i(TAG, "�첽ִ�п�ʼ׼����");
		startMessage();

	}

	//���Ҫ�����첽���ȴ������ݿ�ȡ����cursor����ͨ�������Ի�ȡ��ϵ����Ϣ
	@Override
	protected ArrayList<ContactPerson> doInBackground(Cursor... params) {
		Log.i(TAG, "�첽��ִ̨��������....");
System.out.println("�첽��ִ̨��������....");
		//��Ϊ�ǿɱ������ͨ�������±��ȡ���Ӧ�Ĳ����������ǻ�ȡ��һ������
		Cursor cursor=params[0];
		
		ArrayList<ContactPerson> pList=new ArrayList<ContactPerson>();
		
		if(cursor!=null && cursor.getCount()>0){
			
			while(cursor.moveToNext()){
				String name=cursor.getString(1);
				String number = cursor.getString(2);	
				
				String newnumber=number.replace(" ", "");
				
				newnumber=newnumber.replace("-", "");
				
				int id = cursor.getInt(4);
				
				ContactPerson p=new ContactPerson();
				
				p.setId(id);
				p.setPhoneNumber(newnumber);
				
				if(name==null || name.equals("")){
					p.setPerisonName("İ����");
				}else{
					p.setPerisonName(name);
				}
				
				//��õ�ǰ�����Ĵ�д��ĸƴ�����ָ�һ������
			//���籾���绰  ���� bjdh  Ȼ���Ӧ��һ������,��Ҫ������ T9���ּ����ټ�����
				p.setFormattedNumber(getNameNum(p.getPerisonName() + ""));
			
				//��õ�ǰ������ƴ��
				try {
					p.setPinyin(ToPinYin.getPinYin(p.getPerisonName() + ""));
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				try {
					p.setSortkey(ToPinYin.getFirstkey(p.getPerisonName()+""));
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				Log.i(TAG, "id��:"+id);
				Log.i(TAG, "������:"+name);
				
				Log.i(TAG, "�绰��:"+number);
				Log.i(TAG, "FormattedNumber��:"+p.getFormattedNumber());
				Log.i(TAG, "Pinyin:"+p.getPinyin());
				Log.i(TAG, "sortkey����ĸ:"+p.getSortkey());
				
				pList.add(p);
				
				}
			
			
			
		}
		
		
		
		
		
		return pList;
		

	}

	@Override
	protected void onPostExecute(ArrayList<ContactPerson> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.i(TAG, "�첽����ִ����ɣ����ڴ�����");
		//��һ����doInBackground(Cursor... params)ִ������ˣ������
				//֪ͨhandler�߳���ӵ���������ˣ�
				//������һ������Ϣ��Я����������ϵĽ��ֵ�����
				//��ֵ��Application����ϵ�˼���
		  //0x101�������
				sendEndMessage(0x101, result);
		

	}

	
	public void startMessage(){
		Message msg=new Message();
		
		//׼����ʼ��
		msg.what=0x102;
			handler.sendMessage(msg);
		
	}
	
	
	
	
	
	
	
	public void sendEndMessage(final int flag,final ArrayList<ContactPerson> result){

			Message msg=new Message();
			
				msg.what=flag;
				Bundle bundle = new Bundle();
				bundle.putSerializable("���", result);
				msg.setData(bundle);
					handler.sendMessage(msg);
					
		}
		
		

	
	
	
	
	
	
	
	
	
	
	
	
	private String getNameNum(String name) {
		try {
			//������ֲ�Ϊ�ջ��߳��ȴ���0
			if (name != null && name.length() != 0) {
				//��ȡname�ĳ���
				int len = name.length();
				//newһ���ַ����飬�������ַ����ĳ���
				char[] nums = new char[len];
				
				for (int i = 0; i < len; i++) {
					//��õ�ǰ�ַ����ĵ�i���ַ�
					String tmp = name.substring(i);
					//ȡ���ѵ�ǰ���ַ��ĵ�һ��ƴ����ĸ
					
					System.out.println("�����⵽����ʲô��"+ToPinYin.getPinYin(tmp).toLowerCase().charAt(0));	
						
					
					nums[i] = getOneNumFromAlpha(ToPinYin.getPinYin(tmp).toLowerCase().charAt(0));
			
				}
				return new String(nums);
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return null;
	}

	private char getOneNumFromAlpha(char firstAlpha) {
		switch (firstAlpha) {
		case 'a':
		case 'b':
		case 'c':
			return '2';
		case 'd':
		case 'e':
		case 'f':
			return '3';
		case 'g':
		case 'h':
		case 'i':
			return '4';
		case 'j':
		case 'k':
		case 'l':
			return '5';
		case 'm':
		case 'n':
		case 'o':
			return '6';
		case 'p':
		case 'q':
		case 'r':
		case 's':
			return '7';
		case 't':
		case 'u':
		case 'v':
			return '8';
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			return '9';
		default:
			return '0';
		}
	}





	//�������������ʼִ���첽
	public static void startRequestServerData(MyService myService,
			Handler handlerInsertOrder, Cursor cursor) {
	
		new MyAsyncTask(myService, handlerInsertOrder).execute(cursor);
		
	}
	
	
	
	
	
	
}
