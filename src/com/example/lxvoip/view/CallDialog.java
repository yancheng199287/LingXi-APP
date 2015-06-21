package com.example.lxvoip.view;

import com.example.http.ReduceScore;
import com.example.lxvoip.R;

import android.app.AlertDialog;
import android.content.Context;

public class CallDialog {
	public Context context;
	
	
	public CallDialog(Context contexta){
		this.context=contexta;
		System.out.println("构造当前的上下文是"+contexta);
	}
	
	public void showCallDialog(String response){
		String temp="默认值";
		if(response.indexOf("0")!=-1){
			temp="拨打成功,请接听来电！";
			
			//拨打成功开始扣除积分
			new ReduceScore(context).reduce("10");
			
			
			
		}else if(response.indexOf("2")!=-1){
			temp="您的余额不足，请充值！";
		}else if(response.indexOf("3")!=-1){
			temp="号码格式不正确，请检查！";
		}else if(response.indexOf("-1")!=-1){
			temp="回拨失败，请再此尝试！";
		}
		
		System.out.println("showCallDialog当前的上下文是"+context);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);           
	
		builder.setIcon(R.drawable.lxlogo);  
		builder.setTitle("拨打提示");  
		builder.setMessage(temp);  
		builder.create().show(); 
	}
	
	
	public void showReduceScoreDialog(String response){
		String temp="默认值";
		if(response.indexOf("0")!=-1){
			temp="扣除积分成功";
		}else if(response.indexOf("1")!=-1){
			temp="未知失败";
		}else if(response.indexOf("2")!=-1){
			temp="账户不存在！";
		}
		
		
		System.out.println("showReduceScoreDialog当前的上下文是"+context);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);           
		builder.setIcon(R.drawable.lxlogo);  
		builder.setTitle("拨打提示");  
		builder.setMessage(temp);  
		builder.create().show(); 
	}
	
	

}
