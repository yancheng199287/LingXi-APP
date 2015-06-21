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
	
	//��ȡ�绰����༭�����
	EditText phoneNumber;
	//��ȡ����༭�����
	EditText phonePwd;
	//��ȡ��ס���븴ѡ�����
	CheckBox rember;
	//��ȡ�Զ���¼��ѡ�����
	CheckBox auto;
	//��ȡ��¼��ť����
	Button login;
	//��ȡע��������ı������
	TextView reg;
	
	ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		MyApplication.addActivity(this);
		//��ʼ�������������
		init();
		
		if(isLogined()==false){
			LoginListener();
		}
		
		
		//Ϊ��¼��ť����¼�
	login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//ִ�е�¼��������ȡ�ֻ��ź����룬�ж��Ƿ��ס���룬�ж��Ƿ��Զ���¼
				LoginListener();
			}
		});
	
	
	//Ϊע��������ı�����Ӽ����¼�
	
	reg.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			
		}
	});
	
	
	
	}
	
	
	


	//Ϊ��¼��ť�����ϸ��ִ�й���
	public void LoginListener(){
		
	
		
		pn=phoneNumber.getText().toString();
		pwd=phonePwd.getText().toString();
		remState=rember.isChecked();
		
		autotate=auto.isChecked();
	System.out.println("��ס����״̬��"+remState+",�Զ���¼״̬��"+autotate);	
		
		//ִ�е�¼
		validateLogin(pn,pwd);
	
		}
		
		
		
		
	
	
	
	
	
	
	
	//����˻�������ȷ��ִ�б������ö������Զ���¼�ͼ�ס����
	public void saveConfig(){
		SharedPreferences sp=getSharedPreferences("loginConfig", MODE_PRIVATE);
		
		 SharedPreferences.Editor edit = sp.edit();
		 
		//����µ�ֵ���ɼ��Ǽ�ֵ�Ե���ʽ���  
	
		//���ֻ�Ǽ�ס���룬
		 if(remState==true && autotate==false){
			 edit.putBoolean("rem", true); 
			 edit.putString("pn", pn); 
			 edit.putString("pwd", pwd); 
			 
			 //�����ס���Զ���¼
		 }else if(remState==true && autotate==true){
		
			 edit.putString("pn", pn); 
			 edit.putString("pwd", pwd); 	
			 edit.putBoolean("rem", remState); 
			 edit.putBoolean("auto", autotate); 
		 }
		 

	        //�ύ.  
	        edit.commit();  
		
	}
	


	//�жϵ�¼����ס������Զ���¼
	public boolean isLogined(){
		
		SharedPreferences sp=getSharedPreferences("loginConfig", MODE_PRIVATE);
		
		//ȡ����ס����״̬�����û����ôĬ����false
		remState=sp.getBoolean("rem", false);
		autotate=sp.getBoolean("auto", false);
		
		System.out.println("ȡ�����ļ�ס����״̬�ǣ�"+remState+",�Զ�"+autotate);
		
		
		//���ֻ�Ǽ�ס���룬
		 if(remState==true && autotate==false){
			
			 phoneNumber.setText(sp.getString("pn", ""));
			 phonePwd.setText(sp.getString("pwd" ,""));
				System.out.println("ȡ�������˻��ǣ�"+sp.getString("pn", "")+",mm"+sp.getString("pwd" ,""));
			 
				return true;
			 //�����ס���Զ���¼
		 }else if(remState==true && autotate==true){
				 pn=sp.getString("pn", "");
				 pwd=sp.getString("pwd" ,"");
				 
					System.out.println("����trueȡ�������˻��ǣ�"+sp.getString("pn", "")+",mm"+sp.getString("pwd" ,""));
					 
				 //��ʼ��¼
				 validateLogin(pn,pwd);
				 
				 return true;
		 }
		 
		 
		return false;
		

		
	}
	
	
	
	

	
	
	public void validateLogin(final String username,final String password){
		pd=new ProgressDialog(this);
		 pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);// ���ý�������񣬷��ΪԲ�Σ���ת��    
     pd.setTitle("��ʾ");// ����ProgressDialog ����     
    pd.setMessage("���ڵ�¼��...");// ����ProgressDialog��ʾ��Ϣ         
    //  pd.setIcon(R.drawable.icon);// ����ProgressDialog����ͼ��         
      // ����ProgressDialog �Ľ������Ƿ���ȷ false ���ǲ�����Ϊ����ȷ     
       pd.setIndeterminate(false);                   
      pd.setCancelable(true); // ����ProgressDialog �Ƿ���԰��˻ؼ�ȡ��                          
   
      pd.show(); // ��ProgressDialog��ʾ      
            
		
	
			
			Toast.makeText(Login.this, "������post", 3000).show();
			
			
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
							System.out.println("��¼���ص������ǣ�"+arg0);
							if(arg0.indexOf("true")!=-1){
								pd.dismiss();
								saveConfig();
								Toast.makeText(Login.this, "��¼�ɹ����Ѿ�����������Ϣ!", 3000).show();
								
								GetAppAndMy getinfo=new GetAppAndMy(getApplicationContext());
								getinfo.getMyinfo(pn);
								getinfo.getAppinfo();
								
							Intent intent=new Intent(Login.this,MainActivity.class);
							startActivity(intent);
							//finish();
							}else{
								pd.dismiss();
								Toast.makeText(Login.this, "�ֻ��Ż������벻��ȷ��", 3000).show();
							}
							
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							 VolleyLog.d("MainActivity", "Error: " + error.getMessage());	
						}		
					})
					
					{	
						//��Ҫ��StringRequestһ�£������JsonObjectRequest�Ͳ����ύ��
						@Override
						protected Map<String, String> getParams()throws AuthFailureError {
							// ������������Ҫpost�Ĳ���
			                Map<String, String> params = new HashMap<String, String>();
			                System.out.println("�˻��ǣ�"+pn);
			                System.out.println("�����ǣ�"+pwd);
			                params.put("phoneNumber", pn);
			                params.put("password", pwd);
							return params;
						}
					};
						{
	//����������ŵ������������ �㿪ʼִ��
				MyRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
		
					}

			
		}else{
			pd.dismiss();
			Toast.makeText(Login.this, "����д������ȷ�ĵ��ֻ��Ż������룡",3000).show();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	//��ʼ�����
	public void init(){
		phoneNumber=(EditText) findViewById(R.id.phoneNumber);
		phonePwd=(EditText) findViewById(R.id.phonePwd);
		rember=(CheckBox) findViewById(R.id.rember);
		auto=(CheckBox) findViewById(R.id.auto);
		login=(Button) findViewById(R.id.loginButton);
		reg=(TextView) findViewById(R.id.reg);
	}
	
	
	
	
}
