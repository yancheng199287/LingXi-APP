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
	
	
	//通过构造方法,获得handler对象(主要是对其发送消息，让其处理)，和上下文对象
	public MyAsyncTask(Context context,Handler handler){
		this.handler=handler;
		this.context=context;
	}
	
	
	
	

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Log.i(TAG, "异步执行开始准备！");
		startMessage();

	}

	//如果要开启异步，先传从数据库取来的cursor对象，通过它可以获取联系人信息
	@Override
	protected ArrayList<ContactPerson> doInBackground(Cursor... params) {
		Log.i(TAG, "异步后台执行任务中....");
System.out.println("异步后台执行任务中....");
		//因为是可变参数，通过数组下标获取其对应的参数，这里是获取第一个参数
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
					p.setPerisonName("陌生人");
				}else{
					p.setPerisonName(name);
				}
				
				//获得当前人名的大写字母拼音，分割一个个的
			//比如本机电话  就是 bjdh  然后对应有一个数字,主要用来做 T9数字键快速检索的
				p.setFormattedNumber(getNameNum(p.getPerisonName() + ""));
			
				//获得当前人名的拼音
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
				
				
				
				Log.i(TAG, "id是:"+id);
				Log.i(TAG, "姓名是:"+name);
				
				Log.i(TAG, "电话是:"+number);
				Log.i(TAG, "FormattedNumber是:"+p.getFormattedNumber());
				Log.i(TAG, "Pinyin:"+p.getPinyin());
				Log.i(TAG, "sortkey首字母:"+p.getSortkey());
				
				pList.add(p);
				
				}
			
			
			
		}
		
		
		
		
		
		return pList;
		

	}

	@Override
	protected void onPostExecute(ArrayList<ContactPerson> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.i(TAG, "异步任务执行完成，正在处理结果");
		//这一步是doInBackground(Cursor... params)执行完毕了，会调用
				//通知handler线程添加到集合完毕了！
				//发送了一个空消息，携带了这个集合的结果值，最后
				//赋值给Application的联系人集合
		  //0x101代表完成
				sendEndMessage(0x101, result);
		

	}

	
	public void startMessage(){
		Message msg=new Message();
		
		//准备开始的
		msg.what=0x102;
			handler.sendMessage(msg);
		
	}
	
	
	
	
	
	
	
	public void sendEndMessage(final int flag,final ArrayList<ContactPerson> result){

			Message msg=new Message();
			
				msg.what=flag;
				Bundle bundle = new Bundle();
				bundle.putSerializable("完成", result);
				msg.setData(bundle);
					handler.sendMessage(msg);
					
		}
		
		

	
	
	
	
	
	
	
	
	
	
	
	
	private String getNameNum(String name) {
		try {
			//如果名字不为空或者长度大于0
			if (name != null && name.length() != 0) {
				//获取name的长度
				int len = name.length();
				//new一个字符数组，长度是字符串的长度
				char[] nums = new char[len];
				
				for (int i = 0; i < len; i++) {
					//获得当前字符串的第i个字符
					String tmp = name.substring(i);
					//取出把当前的字符的第一个拼音字母
					
					System.out.println("看看这到底是什么："+ToPinYin.getPinYin(tmp).toLowerCase().charAt(0));	
						
					
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





	//调用这个方法开始执行异步
	public static void startRequestServerData(MyService myService,
			Handler handlerInsertOrder, Cursor cursor) {
	
		new MyAsyncTask(myService, handlerInsertOrder).execute(cursor);
		
	}
	
	
	
	
	
	
}
