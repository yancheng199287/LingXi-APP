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
	
	//拨号盘这个视图
	View bohaopanView;
	
	//通话记录这个按钮,如果按了拨号盘按键就要替换成手机号码,并将首页按钮隐藏
	Button topCallsButton;
	//首页按钮，如果用户开始壁画就
	Button home;
	
Context context;
	
	//listview 装载通话记录的
	ListView callLogListView;
	
	//隐藏和显示拨号盘最后一栏的扩展功能的视图
//	 View t9_function;
	
	 //switch_dialer隐藏和显示拨号盘的开关
	ImageView switch_dialer;
	
	//呼叫的按钮
	Button calling;
	//删除按钮
	ImageView delete;
	
	//数字按钮
	ImageView dialNum1,dialNum2,dialNum3,dialNum4,dialNum5,dialNum6,dialNum7,dialNum8,dialNum9,
	dialNum0,dialx,dialj;
	
	//整个布局视图
	View vv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 vv=inflater.inflate(R.layout.dialer, container, false);
		 
		 init();
		 MyApplication.context=getActivity();
		 
System.out.println("初始化得到的context："+getActivity());
		return vv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void init(){
		asyncQuery = new MyAsyncQueryHandler(getActivity().getContentResolver());
		
		
		topCallsButton=(Button) vv.findViewById(R.id.topCallsButton);
		
		
		//拨号盘这个视图
		 bohaopanView=vv.findViewById(R.id.bohaopanView);
		
		
		//首页按钮，如果用户开始壁画就
		 home=(Button) vv.findViewById(R.id.home);
		

		
		//listview 装载通话记录的
		 callLogListView=(ListView) vv.findViewById(R.id.listView1);
		
	
		
		 //switch_dialer隐藏和显示拨号盘的开关
		 switch_dialer=(ImageView) vv.findViewById(R.id.switch_dialer);
		 switch_dialer.setOnClickListener(this);
		//呼叫的按钮
		 calling=(Button) vv.findViewById(R.id.calling);
		 calling.setOnClickListener(this);
		//删除按钮
		 delete=(ImageView) vv.findViewById(R.id.delete);
		 delete.setOnClickListener(this);
		//数字按钮
		int[] image12=new int[]{
				R.id.dialNum1,R.id.dialNum2,R.id.dialNum3,R.id.dialNum4,R.id.dialNum5,R.id.dialNum6,R.id.dialNum7
				,R.id.dialNum8,R.id.dialNum9,R.id.dialNum0,R.id.dialNum10,R.id.dialNum11
		};
		
					
		for (int i = 0; i <12; i++) {
			//批量初始化拨号组件
			ImageView imageView12=(ImageView) vv.findViewById(image12[i]);
			imageView12.setOnClickListener(this);
		}
		
		
		 initQuery();
	
		
		System.out.println("当前得到的context"+context);
		 app = (MyApplication) getActivity().getApplicationContext(); 
	}




@Override
public void onDestroy() {

	super.onDestroy();
	
	System.out.println("当前拨号盘的framg销毁！");
}


	
	
	
	
	
	
	
	
	
	
	
	
	//为设置异步查询初始化ContentProvider提供参数信息
	private void initQuery(){
		Uri uri = CallLog.Calls.CONTENT_URI;
		
		String[] projection = { 
				CallLog.Calls.DATE,
				CallLog.Calls.NUMBER,
				CallLog.Calls.TYPE,
				CallLog.Calls.CACHED_NAME,
				CallLog.Calls._ID,
				CallLog.Calls.DURATION
				
		}; // 查询的列
		asyncQuery.startQuery(0, null, uri, projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);  
	}
	

		//异步查询助手
		private class MyAsyncQueryHandler extends AsyncQueryHandler {

			public MyAsyncQueryHandler(ContentResolver cr) {	
				super(cr);
			
			}

			@Override
			protected void onQueryComplete(int token, Object cookie,Cursor cursor) {
				System.out.println("查询完毕！"+cursor);
				//如果查询的结果不为空执行此方法
				if (cursor != null && cursor.getCount() > 0) {
					
					list = new ArrayList<CallsLog>();
					
					SimpleDateFormat sfd = new SimpleDateFormat("MM-dd hh:mm");
					
					Date date;
					
					//开始遍历Cursor中的信息
					cursor.moveToFirst();
					for (int i = 0; i < cursor.getCount(); i++) {
						cursor.moveToPosition(i);
						date = new Date(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)));
//						String date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
						
						String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
						int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
						String cachedName = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));//缓存的名称与电话号码，如果它的存在
						int id = cursor.getInt(cursor.getColumnIndex(CallLog.Calls._ID));
					//获得通话时间
						int callsecond = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));

						
				System.out.println("联系人的姓名："+cachedName+",电话是："+number+",时间是："+callsecond);		
						
						
						
						
						CallsLog clb = new CallsLog();
						clb.setId(id);
						clb.setPhoneNumber(number);
						clb.setPerisonName(cachedName);
						if(null == cachedName || "".equals(cachedName)){
							clb.setPerisonName("未知人");
						}
						//这是来电类型
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
			
			
			//当异步查询完毕，调用此方法
			
			
				

		}


		private void setAdapter(ArrayList<CallsLog> list) {
			adapter = new DialerAdapter(getActivity(), list);
//			TextView tv = new TextView(this);
//			tv.setBackgroundResource(R.drawable.dial_input_bg2);
//			callLogList.addFooterView(tv);
			callLogListView.setAdapter(adapter);
			
			callLogListView.setOnScrollListener(new OnScrollListener() {

				public void onScrollStateChanged(AbsListView view, int scrollState) {
					
	               //当滚动视图正在进行滚动的时候，那么就隐藏拨号盘
			
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
					
					Toast.makeText(getActivity(), "你点击了第"+position+"——个", 3000).show();
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
			//判断当前电话号码是否超过12位
			if (topCallsButton.getText().length() <11) {
				
				//0这个数字追加到按钮的text的最后一位
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
	
	
	
	//接收键盘输入，这个是追加字符串，首先要获取拨号框按钮的字符串，然后添加传过来的字符串在最后
		private void input(String str) {
			
			String p = topCallsButton.getText().toString();
			if(p.indexOf("通话记录")!=-1){
				p="";
				topCallsButton.setText(p + str);
				
			}else{
				topCallsButton.setText(p + str);
			}
			
			//根据输入的号码检索联系人
			searchResult(p);
		
			
		}
		
		
		//每次删除最后一位
		private void delete() {
			
			String p = topCallsButton.getText().toString();
			System.out.println("p.length():"+p.length());
			if(p.length()>1){
				topCallsButton.setText(p.substring(0, p.length()-1));
			}else if(p.length()==1){
				home.setVisibility(View.VISIBLE);
				topCallsButton.setText("通话记录");
				t9last.setVisibility(View.GONE);
				
				setAdapter(list);
				
			}
			
			
		}
	
		//拨打电话
		public void call(final String called) {
		

				
				StringRequest sre=new StringRequest(Method.POST,callurl,new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						System.out.println("拨打返回的代码："+response);
						//showCallDialog( response);
						
						String temp="默认值";
						if(response.indexOf("0")!=-1){
							temp="拨打成功,请接听来电！";
							
							//拨打成功开始扣除积分
							//new ReduceScore(context).reduce("10");
							
							
							
						}else if(response.indexOf("2")!=-1){
							temp="您的余额不足，请充值！";
						}else if(response.indexOf("3")!=-1){
							temp="号码格式不正确，请检查！";
						}else if(response.indexOf("-1")!=-1){
							temp="回拨失败，请再此尝试！";
						}
						
		//Toast.makeText(MyApplication.context, temp, 3000).show();		
			
						
				//显示拨打是否成功的对话框		
		CallDialog cal=new CallDialog(MyApplication.context);
			
					cal.showCallDialog(response);
				
					}
				},new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						//请求出错的原因在这里
						 VolleyLog.d("GetAppAndMy", "回拨提交出错： " + error.getMessage());
					
						
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
		
				System.out.println("call方法的："+getActivity());
				
				
				
				

			
			/*
			Uri uri = Uri.parse("tel:" + phone);
			Intent it = new Intent(Intent.ACTION_CALL, uri);
			startActivity(it);*/

		}
		

		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
		
		public void switchBohaopan(){
			View v=getActivity().findViewById(R.id.main_radio);
			View view=vv.findViewById(R.id.bohaopanView);
			
				//如果拨号界面是不显示的，那么单击开关按钮就显示
				if(!view.isShown()){
				
					view.setVisibility(view.VISIBLE);
				
				}else{
				
					view.setVisibility(view.GONE);
		
					//v.setVisibility(View.VISIBLE);
				
				}
				
			}
			
		//在通话记录输入号码检索联系人
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
	System.out.println("开始创建actiity中拨号碎片"+context);	
		}
		
		@Override
		public void onDetach() {
			// TODO Auto-generated method stub
			super.onDetach();
			System.out.println("actiity中拨号碎片进行分离");	
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			
			System.out.println("保存碎片状态");
		}
		
		
}
