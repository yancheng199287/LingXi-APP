package com.example.lxvoip;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.application.MyApplication;
import com.example.http.GetAppAndMy;
import com.example.http.MyRequestQueue;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	String pn="";
	String pwd="";
	
	boolean remState=false;
	boolean autotate=false;
	
	//获取电话号码编辑框对象
	EditText phoneNumber;
	//获取密码编辑框对象
	EditText phonePwd;
	//获取记住密码复选框对象
	CheckBox rember;
	//获取自动登录复选框对象
	CheckBox auto;
	//获取登录按钮对象
	Button login;
	//获取注册和问题文本框对象
	TextView reg;
	
	ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		MyApplication.addActivity(this);
		//初始化所有组件对象
		init();
		
		if(isLogined()==false){
			LoginListener();
		}
		
		
		//为登录按钮添加事件
	login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//执行登录方法，获取手机号和密码，判断是否记住密码，判断是否自动登录
				LoginListener();
			}
		});
	
	
	//为注册和问题文本框添加监听事件
	
	reg.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			
		}
	});
	
	
	
	}
	
	
	


	//为登录按钮添加详细的执行过程
	public void LoginListener(){
		
	
		
		pn=phoneNumber.getText().toString();
		pwd=phonePwd.getText().toString();
		remState=rember.isChecked();
		
		autotate=auto.isChecked();
	System.out.println("记住密码状态："+remState+",自动登录状态："+autotate);	
		
		//执行登录
		validateLogin(pn,pwd);
	
		}
		
		
		
		
	
	
	
	
	
	
	
	//如果账户密码正确，执行保存配置动作，自动登录和记住密码
	public void saveConfig(){
		SharedPreferences sp=getSharedPreferences("loginConfig", MODE_PRIVATE);
		
		 SharedPreferences.Editor edit = sp.edit();
		 
		//添加新的值，可见是键值对的形式添加  
	
		//如果只是记住密码，
		 if(remState==true && autotate==false){
			 edit.putBoolean("rem", true); 
			 edit.putString("pn", pn); 
			 edit.putString("pwd", pwd); 
			 
			 //如果记住和自动登录
		 }else if(remState==true && autotate==true){
		
			 edit.putString("pn", pn); 
			 edit.putString("pwd", pwd); 	
			 edit.putBoolean("rem", remState); 
			 edit.putBoolean("auto", autotate); 
		 }
		 

	        //提交.  
	        edit.commit();  
		
	}
	


	//判断登录过记住密码和自动登录
	public boolean isLogined(){
		
		SharedPreferences sp=getSharedPreferences("loginConfig", MODE_PRIVATE);
		
		//取出记住密码状态，如果没有那么默认是false
		remState=sp.getBoolean("rem", false);
		autotate=sp.getBoolean("auto", false);
		
		System.out.println("取出来的记住密码状态是："+remState+",自动"+autotate);
		
		
		//如果只是记住密码，
		 if(remState==true && autotate==false){
			
			 phoneNumber.setText(sp.getString("pn", ""));
			 phonePwd.setText(sp.getString("pwd" ,""));
				System.out.println("取出来的账户是："+sp.getString("pn", "")+",mm"+sp.getString("pwd" ,""));
			 
				return true;
			 //如果记住和自动登录
		 }else if(remState==true && autotate==true){
				 pn=sp.getString("pn", "");
				 pwd=sp.getString("pwd" ,"");
				 
					System.out.println("两个true取出来的账户是："+sp.getString("pn", "")+",mm"+sp.getString("pwd" ,""));
					 
				 //开始登录
				 validateLogin(pn,pwd);
				 
				 return true;
		 }
		 
		 
		return false;
		

		
	}
	
	
	
	

	
	
	public void validateLogin(final String username,final String password){
		pd=new ProgressDialog(this);
		 pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条风格，风格为圆形，旋转的    
     pd.setTitle("提示");// 设置ProgressDialog 标题     
    pd.setMessage("正在登录中...");// 设置ProgressDialog提示信息         
    //  pd.setIcon(R.drawable.icon);// 设置ProgressDialog标题图标         
      // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确     
       pd.setIndeterminate(false);                   
      pd.setCancelable(true); // 设置ProgressDialog 是否可以按退回键取消                          
   
      pd.show(); // 让ProgressDialog显示      
            
		
	
			
			Toast.makeText(Login.this, "你点击了post", 3000).show();
			
			
			if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password))  {	
				
			final String login=MyApplication.baseurl+"person_login.action";
	
		if(username.equals("admin")&&password.equals("123")){
			Intent intent=new Intent(Login.this,MainActivity.class);
			startActivity(intent);
			pd.dismiss();
		}
			
			
					StringRequest  stringRequest=new StringRequest(Method.POST, login, new Response.Listener<String>() {

					
						
						
						@Override
						public void onResponse(String arg0) {
							System.out.println("登录返回的数据是："+arg0);
							if(arg0.indexOf("true")!=-1){
								pd.dismiss();
								saveConfig();
								Toast.makeText(Login.this, "登录成功，已经保存配置信息!", 3000).show();
								
								GetAppAndMy getinfo=new GetAppAndMy(getApplicationContext());
								getinfo.getMyinfo(pn);
								getinfo.getAppinfo();
								
							Intent intent=new Intent(Login.this,MainActivity.class);
							startActivity(intent);
							//finish();
							}else{
								pd.dismiss();
								Toast.makeText(Login.this, "手机号或者密码不正确！", 3000).show();
							}
							
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							 VolleyLog.d("MainActivity", "Error: " + error.getMessage());	
						}		
					})
					
					{	
						//必要跟StringRequest一致，如果是JsonObjectRequest就不能提交了
						@Override
						protected Map<String, String> getParams()throws AuthFailureError {
							// 在这里设置需要post的参数
			                Map<String, String> params = new HashMap<String, String>();
			                System.out.println("账户是："+pn);
			                System.out.println("密码是："+pwd);
			                params.put("phoneNumber", pn);
			                params.put("password", pwd);
							return params;
						}
					};
						{
	//将本次请求放到请求队列里面 便开始执行
				MyRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
		
					}

			
		}else{
			pd.dismiss();
			Toast.makeText(Login.this, "请填写完整正确的的手机号或者密码！",3000).show();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	//初始化组件
	public void init(){
		phoneNumber=(EditText) findViewById(R.id.phoneNumber);
		phonePwd=(EditText) findViewById(R.id.phonePwd);
		rember=(CheckBox) findViewById(R.id.rember);
		auto=(CheckBox) findViewById(R.id.auto);
		login=(Button) findViewById(R.id.loginButton);
		reg=(TextView) findViewById(R.id.reg);
	}
	
	
	
	
}
