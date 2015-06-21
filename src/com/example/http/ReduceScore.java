package com.example.http;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.example.application.MyApplication;
import com.example.lxvoip.view.CallDialog;

public class ReduceScore {
	
	Context context;
	
	public ReduceScore(Context context){
		this.context=context;
	}
	
	
	
	String reduceurl=MyApplication.baseurl+"person_reduceScore.action";
	

	//����绰
			public void reduce(final String score) {
				
				StringRequest sre=new StringRequest(Method.POST,reduceurl,new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						System.out.println("���򷵻صĴ��룺"+response);
				//��ʾ�����Ƿ�ɹ��ĶԻ���
						
			new CallDialog(context).showReduceScoreDialog(response);
					
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
		                params.put("score", score); 
						return params;
					}
				};
				
				MyRequestQueue.getInstance(context).addToRequestQueue(sre);	
		
			}
				
				
}
