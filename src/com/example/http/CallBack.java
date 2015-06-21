package com.example.http;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class CallBack {
	Context context;
	
	public CallBack(Context context){
		this.context=context;
	}
	
	
	String url="http://localhost:8080/lxvoip/person_callback.action";
	public void calling(final String phoneNumber,final String called){
		StringRequest sre=new StringRequest(Method.POST,url,new Response.Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				
			
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
                params.put("phoneNumber", phoneNumber);
                params.put("called", called); 
				return params;
			}
		};
		
		MyRequestQueue.getInstance(context).addToRequestQueue(sre);	
	}
		
	

	
   
	
	

}
