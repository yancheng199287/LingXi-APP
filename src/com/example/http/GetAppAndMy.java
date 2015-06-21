package com.example.http;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.application.MyApplication;


//这里获取用户和手机软件版本信息，保存在MyApplication

public class GetAppAndMy {
Context context;

public GetAppAndMy(Context context){
	this.context=context;
}
	
	
	
	String myurl=MyApplication.baseurl+"person_showinfo.action";
	String appurl=MyApplication.baseurl+"app_show.action";
	public void getMyinfo(final String phoneNumber){
		
		StringRequest myRequest=new StringRequest(Method.POST, myurl, new Response.Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				Log.i("GetAppAndMy",""+arg0);
				parseMyJson(arg0);
				
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				//请求出错的原因在这里
				 VolleyLog.d("GetAppAndMy", "获取个人信息出错： " + error.getMessage());
				
			}
		})
		{
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
			    Map<String, String> params = new HashMap<String, String>();
                params.put("phoneNumber", phoneNumber);
              
				return params;
			}
		};
		
		
		MyRequestQueue.getInstance(context).addToRequestQueue(myRequest);
			
	}
	
	
	public void parseMyJson(String response){
		try {
			JSONObject myinfo=new JSONObject(response);
			MyApplication.myinfo.setId(myinfo.getInt("id"));
			MyApplication.myinfo.setUserName(myinfo.getString("userName"));
			MyApplication.myinfo.setPhoneNumber(myinfo.getString("phoneNumber"));
			MyApplication.myinfo.setScore(myinfo.getInt("score"));
			MyApplication.myinfo.setIsVIP(myinfo.getInt("isVip"));
			MyApplication.myinfo.setHeadPhoto(myinfo.getString("headPhoto"));
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
public void getAppinfo(){
		
		StringRequest myRequest=new StringRequest(Method.POST, appurl, new Response.Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				Log.i("GetAppAndMy",""+arg0);
				parseAppinfo(arg0);
				
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				//请求出错的原因在这里
				 VolleyLog.d("GetAppAndMy", "获取个人信息出错： " + error.getMessage());
				
			}
		});

		MyRequestQueue.getInstance(context).addToRequestQueue(myRequest);
			
	}
	
	
public void parseAppinfo(String response){
	try {
		JSONObject appinfo=new JSONObject(response);
		
		MyApplication.appinfo.setHelpCenter(appinfo.getString("helpCenter"));
		MyApplication.appinfo.setAbout(appinfo.getString("about"));
		MyApplication.appinfo.setUrl(appinfo.getString("url"));
		MyApplication.appinfo.setVersion(appinfo.getString("version"));
		MyApplication.appinfo.setSaleActivity(appinfo.getString("saleActivity"));

		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
