package com.example.lxvoip;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.example.application.MyApplication;
import com.example.entity.CallsLog;
import com.example.entity.ContactPerson;
import com.example.http.MyRequestQueue;
import com.example.lxvoip.adapter.DialerAdapter;
import com.example.lxvoip.adapter.T9Adapter;
import com.example.lxvoip.view.CallDialog;


import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class DialerFragment extends Fragment implements OnClickListener {
	

	private AsyncQueryHandler asyncQuery;
	
	String callurl=MyApplication.baseurl+"person_callback.action";
	
	
	String reduceurl=MyApplication.baseurl+"person_reduceScore.action";
	
	
	MyApplication app;
	ArrayList<CallsLog> list;

	DialerAdapter adapter;
	
	Context contex;
	
	//�����������ͼ
	View bohaopanView;
	
	//ͨ����¼�����ť,������˲����̰�����Ҫ�滻���ֻ�����,������ҳ��ť����
	Button topCallsButton;
	//��ҳ��ť������û���ʼ�ڻ���
	Button home;
	
Context context;
	
	//listview װ��ͨ����¼��
	ListView callLogListView;
	
	//���غ���ʾ���������һ������չ���ܵ���ͼ
//	 View t9_function;
	
	 //switch_dialer���غ���ʾ�����̵Ŀ���
	ImageView switch_dialer;
	
	//���еİ�ť
	Button calling;
	//ɾ����ť
	ImageView delete;
	
	//���ְ�ť
	ImageView dialNum1,dialNum2,dialNum3,dialNum4,dialNum5,dialNum6,dialNum7,dialNum8,dialNum9,
	dialNum0,dialx,dialj;
	
	//����������ͼ
	View vv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 vv=inflater.inflate(R.layout.dialer, container, false);
		 
		 init();
		 MyApplication.context=getActivity();
		 
System.out.println("��ʼ���õ���context��"+getActivity());
		return vv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void init(){
		asyncQuery = new MyAsyncQueryHandler(getActivity().getContentResolver());
		
		
		topCallsButton=(Button) vv.findViewById(R.id.topCallsButton);
		
		
		//�����������ͼ
		 bohaopanView=vv.findViewById(R.id.bohaopanView);
		
		
		//��ҳ��ť������û���ʼ�ڻ���
		 home=(Button) vv.findViewById(R.id.home);
		

		
		//listview װ��ͨ����¼��
		 callLogListView=(ListView) vv.findViewById(R.id.listView1);
		
	
		
		 //switch_dialer���غ���ʾ�����̵Ŀ���
		 switch_dialer=(ImageView) vv.findViewById(R.id.switch_dialer);
		 switch_dialer.setOnClickListener(this);
		//���еİ�ť
		 calling=(Button) vv.findViewById(R.id.calling);
		 calling.setOnClickListener(this);
		//ɾ����ť
		 delete=(ImageView) vv.findViewById(R.id.delete);
		 delete.setOnClickListener(this);
		//���ְ�ť
		int[] image12=new int[]{
				R.id.dialNum1,R.id.dialNum2,R.id.dialNum3,R.id.dialNum4,R.id.dialNum5,R.id.dialNum6,R.id.dialNum7
				,R.id.dialNum8,R.id.dialNum9,R.id.dialNum0,R.id.dialNum10,R.id.dialNum11
		};
		
					
		for (int i = 0; i <12; i++) {
			//������ʼ���������
			ImageView imageView12=(ImageView) vv.findViewById(image12[i]);
			imageView12.setOnClickListener(this);
		}
		
		
		 initQuery();
	
		
		System.out.println("��ǰ�õ���context"+context);
		 app = (MyApplication) getActivity().getApplicationContext(); 
	}




@Override
public void onDestroy() {

	super.onDestroy();
	
	System.out.println("��ǰ�����̵�framg���٣�");
}


	
	
	
	
	
	
	
	
	
	
	
	
	//Ϊ�����첽��ѯ��ʼ��ContentProvider�ṩ������Ϣ
	private void initQuery(){
		Uri uri = CallLog.Calls.CONTENT_URI;
		
		String[] projection = { 
				CallLog.Calls.DATE,
				CallLog.Calls.NUMBER,
				CallLog.Calls.TYPE,
				CallLog.Calls.CACHED_NAME,
				CallLog.Calls._ID,
				CallLog.Calls.DURATION
				
		}; // ��ѯ����
		asyncQuery.startQuery(0, null, uri, projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);  
	}
	

		//�첽��ѯ����
		private class MyAsyncQueryHandler extends AsyncQueryHandler {

			public MyAsyncQueryHandler(ContentResolver cr) {	
				super(cr);
			
			}

			@Override
			protected void onQueryComplete(int token, Object cookie,Cursor cursor) {
				System.out.println("��ѯ��ϣ�"+cursor);
				//�����ѯ�Ľ����Ϊ��ִ�д˷���
				if (cursor != null && cursor.getCount() > 0) {
					
					list = new ArrayList<CallsLog>();
					
					SimpleDateFormat sfd = new SimpleDateFormat("MM-dd hh:mm");
					
					Date date;
					
					//��ʼ����Cursor�е���Ϣ
					cursor.moveToFirst();
					for (int i = 0; i < cursor.getCount(); i++) {
						cursor.moveToPosition(i);
						date = new Date(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)));
//						String date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
						
						String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
						int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
						String cachedName = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));//�����������绰���룬������Ĵ���
						int id = cursor.getInt(cursor.getColumnIndex(CallLog.Calls._ID));
					//���ͨ��ʱ��
						int callsecond = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));

						
				System.out.println("��ϵ�˵�������"+cachedName+",�绰�ǣ�"+number+",ʱ���ǣ�"+callsecond);		
						
						
						
						
						CallsLog clb = new CallsLog();
						clb.setId(id);
						clb.setPhoneNumber(number);
						clb.setPerisonName(cachedName);
						if(null == cachedName || "".equals(cachedName)){
							clb.setPerisonName("δ֪��");
						}
						//������������
						clb.setType(type);
						clb.setDate(sfd.format(date));
						
					
						clb.setTime(callsecond);
						list.add(clb);
					}
					if (list.size() > 0) {
						setAdapter(list);
					}
				}
			
				super.onQueryComplete(token, cookie, cursor);
			}
			
			
			//���첽��ѯ��ϣ����ô˷���
			
			
				

		}


		private void setAdapter(ArrayList<CallsLog> list) {
			adapter = new DialerAdapter(getActivity(), list);
//			TextView tv = new TextView(this);
//			tv.setBackgroundResource(R.drawable.dial_input_bg2);
//			callLogList.addFooterView(tv);
			callLogListView.setAdapter(adapter);
			
			callLogListView.setOnScrollListener(new OnScrollListener() {

				public void onScrollStateChanged(AbsListView view, int scrollState) {
					
	               //��������ͼ���ڽ��й�����ʱ����ô�����ز�����
			
					if(scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
						View bohaoview=vv.findViewById(R.id.bohaopanView);
						if(bohaoview.getVisibility() == View.VISIBLE){
							
							bohaoview.setVisibility(View.GONE);
							
						}
					}
				}
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
				}
			});
			
			callLogListView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					
					Toast.makeText(getActivity(), "�����˵�"+position+"������", 3000).show();
				}
			});
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	View t9last;

	@Override
	public void onClick(View v) {
		View view=vv.findViewById(R.id.bohaopanView);
		 t9last=view.findViewById(R.id.t9_function);
		
		//topCallsButton.setVisibility(View.GONE);
		home.setVisibility(View.GONE);
		
		t9last.setVisibility(View.VISIBLE);
		
	switch (v.getId()) {
		
		case R.id.dialNum0:
			//�жϵ�ǰ�绰�����Ƿ񳬹�12λ
			if (topCallsButton.getText().length() <11) {
				
				//0�������׷�ӵ���ť��text�����һλ
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum1:
			if (topCallsButton.getText().length() <11) {
			
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum2:
			if (topCallsButton.getText().length() <11) {
				
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum3:
			if (topCallsButton.getText().length() <11) {
				
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum4:
			if (topCallsButton.getText().length() <11) {
			
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum5:
			if (topCallsButton.getText().length() <=11) {
				
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum6:
			if (topCallsButton.getText().length() <11) {
			
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum7:
			if (topCallsButton.getText().length() <11) {
			
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum8:
			if (topCallsButton.getText().length() <11) {
		
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum9:
			if (topCallsButton.getText().length() <11) {
			
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum10:
			if (topCallsButton.getText().length() <11) {
	
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum11:
			if (topCallsButton.getText().length() <11) {
		
				input(v.getTag().toString());
			}
			break;
		case R.id.delete:
			delete();
			break;
		case R.id.calling:
			if (topCallsButton.getText().toString().length() >=8&&topCallsButton.getText().toString().length() <=12) {
				call(topCallsButton.getText().toString());
				
				
			}
			break;
			
		case R.id.switch_dialer:
			switchBohaopan();
			break;
		
		default:
			break;
		}
		
		
	}
	
	
	
	//���ռ������룬�����׷���ַ���������Ҫ��ȡ���ſ�ť���ַ�����Ȼ����Ӵ��������ַ��������
		private void input(String str) {
			
			String p = topCallsButton.getText().toString();
			if(p.indexOf("ͨ����¼")!=-1){
				p="";
				topCallsButton.setText(p + str);
				
			}else{
				topCallsButton.setText(p + str);
			}
			
			//��������ĺ��������ϵ��
			searchResult(p);
		
			
		}
		
		
		//ÿ��ɾ�����һλ
		private void delete() {
			
			String p = topCallsButton.getText().toString();
			System.out.println("p.length():"+p.length());
			if(p.length()>1){
				topCallsButton.setText(p.substring(0, p.length()-1));
			}else if(p.length()==1){
				home.setVisibility(View.VISIBLE);
				topCallsButton.setText("ͨ����¼");
				t9last.setVisibility(View.GONE);
				
				setAdapter(list);
				
			}
			
			
		}
	
		//����绰
		public void call(final String called) {
		

				
				StringRequest sre=new StringRequest(Method.POST,callurl,new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						System.out.println("���򷵻صĴ��룺"+response);
						//showCallDialog( response);
						
						String temp="Ĭ��ֵ";
						if(response.indexOf("0")!=-1){
							temp="����ɹ�,��������磡";
							
							//����ɹ���ʼ�۳�����
							//new ReduceScore(context).reduce("10");
							
							
							
						}else if(response.indexOf("2")!=-1){
							temp="�������㣬���ֵ��";
						}else if(response.indexOf("3")!=-1){
							temp="�����ʽ����ȷ�����飡";
						}else if(response.indexOf("-1")!=-1){
							temp="�ز�ʧ�ܣ����ٴ˳��ԣ�";
						}
						
		//Toast.makeText(MyApplication.context, temp, 3000).show();		
			
						
				//��ʾ�����Ƿ�ɹ��ĶԻ���		
		CallDialog cal=new CallDialog(MyApplication.context);
			
					cal.showCallDialog(response);
				
					}
				},new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						//��������ԭ��������
						 VolleyLog.d("GetAppAndMy", "�ز��ύ���� " + error.getMessage());
					
						
					}
				})
				
				{
					@Override
					protected Map<String, String> getParams() throws AuthFailureError {
					    Map<String, String> params = new HashMap<String, String>();
		                params.put("phoneNumber", MyApplication.myinfo.getPhoneNumber());
		                params.put("called", called); 
						return params;
					}
				};
				
				MyRequestQueue.getInstance(getActivity()).addToRequestQueue(sre);	
		
				System.out.println("call�����ģ�"+getActivity());
				
				
				
				

			
			/*
			Uri uri = Uri.parse("tel:" + phone);
			Intent it = new Intent(Intent.ACTION_CALL, uri);
			startActivity(it);*/

		}
		

		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
		
		public void switchBohaopan(){
			View v=getActivity().findViewById(R.id.main_radio);
			View view=vv.findViewById(R.id.bohaopanView);
			
				//������Ž����ǲ���ʾ�ģ���ô�������ذ�ť����ʾ
				if(!view.isShown()){
				
					view.setVisibility(view.VISIBLE);
				
				}else{
				
					view.setVisibility(view.GONE);
		
					//v.setVisibility(View.VISIBLE);
				
				}
				
			}
			
		//��ͨ����¼������������ϵ��
		ArrayList<ContactPerson> newList=new ArrayList<ContactPerson>();
		
		T9Adapter t9=new T9Adapter(getActivity(), newList);
		public void searchResult(String p){
			
			newList.clear();
			for (int i = 0; i <app.pList.size() ; i++) {
				if(app.pList.get(i).getPhoneNumber().indexOf(p)!=-1){
					newList.add(app.pList.get(i));
				}
			}
			
			 t9.pList=newList;
			 t9.context=getActivity();
			 callLogListView.setAdapter(t9);
			 
			 t9.notifyDataSetChanged();
			
		}
		
	
		@Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			super.onAttach(activity);
			this.contex=activity;
	System.out.println("��ʼ����actiity�в�����Ƭ"+context);	
		}
		
		@Override
		public void onDetach() {
			// TODO Auto-generated method stub
			super.onDetach();
			System.out.println("actiity�в�����Ƭ���з���");	
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			
			System.out.println("������Ƭ״̬");
		}
		
		
}
