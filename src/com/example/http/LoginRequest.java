package com.example.http;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.anim;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.application.MyApplication;
import com.lx.httpurl.AllUrl;


public class LoginRequest {

	
	public static boolean login(Context context){
		//����һ��request�������
		JsonObjectRequest  stringRequest=new JsonObjectRequest(Method.POST, AllUrl.LOGINURL, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
			
				//���صĽ������ui�߳�ִ�е�  ���Կ��Բ���ui����
				 Log.i("MainActivity", response.toString());
				
				
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				//��������ԭ��������
				 VolleyLog.d("MainActivity", "Error: " + error.getMessage());
            
				
			}
		})
		
		{
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// ������������Ҫpost�Ĳ���
                Map<String, String> params = new HashMap<String, String>();
                params.put("phoneNumber", "15927961644");
                params.put("password", "199287yan");
				return params;
			}
		};
		
		//����������ŵ������������ �㿪ʼִ��	
		MyRequestQueue.getInstance(context).addToRequestQueue(stringRequest);
		return false;
	
		
		
	}
	
	
	
	
	
	
	
	
	
	

	

}
