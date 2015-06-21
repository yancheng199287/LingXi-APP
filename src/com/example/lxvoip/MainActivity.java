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

	// ͨ����¼
	RadioButton dialerButton;
	// ��ϵ��
	RadioButton contactButton;
	// ����
	RadioButton findButton;
	// �ҵĵ���
	RadioButton domainButton;

	//�ĸ������Fragment
	DialerFragment dialerFragment;
	ContactFragment contactFragment;
	FindFragment findFragment;
	DomainFragment domainFragment;
	
	//����FragmentManager������
		private FragmentManager fm; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MyApplication.addActivity(this);
		init();
	    //Ĭ����ʾ������
		setChoiceFragment(R.id.dialerButton);
	}
	
	
	
	
	
	
	
	
	boolean kg=true;
	
	/**���û�����ĵײ����д��� */
	public void setChoiceFragment(int id){
		//����FragmentTransaction����,�����Ҫ�ύ����
		android.support.v4.app.FragmentTransaction transaction=fm.beginTransaction(); 
		
		//��������+�������е�Fragment,������ֽ��治������������
		
	
		hideAllFragment(transaction);//���� 
		
	
		
		switch(id){
		case R.id.dialerButton:

		 //�����ǰͨ����¼ҳ�����Ϊ���򴴽��µ�
			if(dialerFragment==null){
				dialerFragment=new DialerFragment();
				transaction.add(R.id.main_framelayout, dialerFragment,"dialerFragment");
				
				//�����Ϊ���ҿ�����ʾ�ģ��ٴε�����Ҫ��ʾ���Ž�����
			} else if(dialerFragment!=null&&dialerFragment.isVisible()){
				View v=findViewById(R.id.main_radio);
				View view=findViewById(R.id.bohaopanView);
			
				//���صײ��˵�
				//v.setVisibility(View.GONE);
				//��ʾ���Ž���
				
		
			if(kg){
				view.setVisibility(View.VISIBLE);
				transaction.show(dialerFragment);
				kg=false;
				System.out.println("������ʾ������ͼ");
			}else{
				view.setVisibility(View.GONE);
				transaction.show(dialerFragment);
				System.out.println("�ر���ʾ������ͼ");
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
		
		
		
		//�м���Ҫ�ύ���񣬷���᲻��ʾ����
		transaction.commit();
	}
	
	
	
	
	  
		
		/** ��Fragmentҳ��ȫ������ */
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
		   fm=getSupportFragmentManager();//��ȡ��FragmentManager������
		
		// ͨ����¼
		dialerButton = (RadioButton) findViewById(R.id.dialerButton);
		dialerButton.setOnClickListener(myListener);
		
		// ��ϵ��
		contactButton = (RadioButton) findViewById(R.id.contactButton);
	
		contactButton.setOnClickListener(myListener);
		// ����
		findButton = (RadioButton) findViewById(R.id.findButton);
	
		findButton.setOnClickListener(myListener);
		// �ҵĵ���
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
						
					//	Toast.makeText(new MainActivity().getApplicationContext(), temp, 3000).show();		
						Intent intent=new Intent(MainActivity.this,Success.class);
						startActivity(intent);	
						
						
				//��ʾ�����Ƿ�ɹ��ĶԻ���		
		/*	CallDialog cal=new CallDialog(MainActivity.this);
			
					cal.showCallDialog(response);
					*/
					
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
				
				MyRequestQueue.getInstance(MainActivity.this).addToRequestQueue(sre);	
		
				
				
				
				
				
				
				
				
				
				
				/*
				Uri uri = Uri.parse("tel:" + phone);
				Intent it = new Intent(Intent.ACTION_CALL, uri);
				startActivity(it);*/

			}
			

	
	
	
	
			public void showCallDialog(String response){
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
				
			
				
				
				
			//	Toast.makeText(MainActivity.this, temp, 3000).show();
				System.out.println("showCallDialog��ǰ����������"+MainActivity.this);
				
			/*	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);           
			
				builder.setIcon(R.drawable.lxlogo);  
				builder.setTitle("������ʾ");  
				builder.setMessage(temp);  
				builder.create().show(); */
			}
	
	
	
	
	
	
	
	
	
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();                                
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
