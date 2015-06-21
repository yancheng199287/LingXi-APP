package com.example.lxvoip;





import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.example.application.MyApplication;
import com.example.http.MyRequestQueue;
import com.example.http.ReduceScore;
import com.example.lxvoip.view.CallDialog;

import android.support.v4.app.FragmentManager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	// 通话记录
	RadioButton dialerButton;
	// 联系人
	RadioButton contactButton;
	// 发现
	RadioButton findButton;
	// 我的地盘
	RadioButton domainButton;

	//四个界面的Fragment
	DialerFragment dialerFragment;
	ContactFragment contactFragment;
	FindFragment findFragment;
	DomainFragment domainFragment;
	
	//声明FragmentManager管理器
		private FragmentManager fm; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MyApplication.addActivity(this);
		init();
	    //默认显示拨号盘
		setChoiceFragment(R.id.dialerButton);
	}
	
	
	
	
	
	
	
	
	boolean kg=true;
	
	/**对用户点击的底部进行处理 */
	public void setChoiceFragment(int id){
		//开启FragmentTransaction事务,最后需要提交事务
		android.support.v4.app.FragmentTransaction transaction=fm.beginTransaction(); 
		
		//首先重置+隐藏所有的Fragment,避免出现界面不流畅的现象发生
		
	
		hideAllFragment(transaction);//隐藏 
		
	
		
		switch(id){
		case R.id.dialerButton:

		 //如果当前通话记录页面对象不为空则创建新的
			if(dialerFragment==null){
				dialerFragment=new DialerFragment();
				transaction.add(R.id.main_framelayout, dialerFragment,"dialerFragment");
				
				//如果不为空且可以显示的，再次单击就要显示拨号界面了
			} else if(dialerFragment!=null&&dialerFragment.isVisible()){
				View v=findViewById(R.id.main_radio);
				View view=findViewById(R.id.bohaopanView);
			
				//隐藏底部菜单
				//v.setVisibility(View.GONE);
				//显示拨号界面
				
		
			if(kg){
				view.setVisibility(View.VISIBLE);
				transaction.show(dialerFragment);
				kg=false;
				System.out.println("开启显示拨号视图");
			}else{
				view.setVisibility(View.GONE);
				transaction.show(dialerFragment);
				System.out.println("关闭显示拨号视图");
				kg=true;
			}
				
				
				
			}else{
				transaction.show(dialerFragment);
			}
			
			
			break;
		case R.id.contactButton:
			if(contactFragment==null){
				contactFragment=new ContactFragment();
				
				transaction.add(R.id.main_framelayout, contactFragment,"contactFragment");
			}else{
				transaction.show(contactFragment);
			}
			break;
		case R.id.findButton:
			
			if(findFragment==null){
				findFragment=new FindFragment();
				
				transaction.add(R.id.main_framelayout, findFragment,"findFragment");
				
			}else{
				transaction.show(findFragment);
			}
			break;
		case R.id.domainButton:
			
			if(domainFragment==null){
				domainFragment=new DomainFragment();
				
				transaction.add(R.id.main_framelayout, domainFragment,"domainFragment");
				
			}else{
				transaction.show(domainFragment);
			}
			break;	
			
			
		}
		
		
		
		//切记需要提交事务，否则会不显示界面
		transaction.commit();
	}
	
	
	
	
	  
		
		/** 把Fragment页面全部隐藏 */
		public void hideAllFragment(android.support.v4.app.FragmentTransaction transaction){
			
			if(dialerFragment!=null){
				transaction.hide(dialerFragment);
			}
			if(contactFragment!=null){
				transaction.hide(contactFragment);
			}
			if(findFragment!=null){
				transaction.hide(findFragment);
			}
			if(domainFragment!=null){
				transaction.hide(domainFragment);
			}
		}
	
	

	public void init() {
		   fm=getSupportFragmentManager();//获取到FragmentManager管理器
		
		// 通话记录
		dialerButton = (RadioButton) findViewById(R.id.dialerButton);
		dialerButton.setOnClickListener(myListener);
		
		// 联系人
		contactButton = (RadioButton) findViewById(R.id.contactButton);
	
		contactButton.setOnClickListener(myListener);
		// 发现
		findButton = (RadioButton) findViewById(R.id.findButton);
	
		findButton.setOnClickListener(myListener);
		// 我的地盘
		domainButton = (RadioButton) findViewById(R.id.domainButton);
		domainButton.setOnClickListener(myListener);
	}
	
	
	View.OnClickListener myListener=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			setChoiceFragment(v.getId());
			
		}
	};
	
	
	
	
	
	
	
	
	
	
	
	
	String callurl=MyApplication.baseurl+"person_callback.action";
	
	
	String reduceurl=MyApplication.baseurl+"person_reduceScore.action";
	
	
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
						
					//	Toast.makeText(new MainActivity().getApplicationContext(), temp, 3000).show();		
						Intent intent=new Intent(MainActivity.this,Success.class);
						startActivity(intent);	
						
						
				//显示拨打是否成功的对话框		
		/*	CallDialog cal=new CallDialog(MainActivity.this);
			
					cal.showCallDialog(response);
					*/
					
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
				
				MyRequestQueue.getInstance(MainActivity.this).addToRequestQueue(sre);	
		
				
				
				
				
				
				
				
				
				
				
				/*
				Uri uri = Uri.parse("tel:" + phone);
				Intent it = new Intent(Intent.ACTION_CALL, uri);
				startActivity(it);*/

			}
			

	
	
	
	
			public void showCallDialog(String response){
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
				
			
				
				
				
			//	Toast.makeText(MainActivity.this, temp, 3000).show();
				System.out.println("showCallDialog当前的上下文是"+MainActivity.this);
				
			/*	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);           
			
				builder.setIcon(R.drawable.lxlogo);  
				builder.setTitle("拨打提示");  
				builder.setMessage(temp);  
				builder.create().show(); */
			}
	
	
	
	
	
	
	
	
	
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	        	MyApplication.finishActivity();  
	           // System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
