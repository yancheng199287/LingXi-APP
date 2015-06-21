package com.example.lxvoip.view;

import com.example.http.ReduceScore;
import com.example.lxvoip.R;

import android.app.AlertDialog;
import android.content.Context;

public class CallDialog {
	public Context context;
	
	
	public CallDialog(Context contexta){
		this.context=contexta;
		System.out.println("���쵱ǰ����������"+contexta);
	}
	
	public void showCallDialog(String response){
		String temp="Ĭ��ֵ";
		if(response.indexOf("0")!=-1){
			temp="����ɹ�,��������磡";
			
			//����ɹ���ʼ�۳�����
			new ReduceScore(context).reduce("10");
			
			
			
		}else if(response.indexOf("2")!=-1){
			temp="�������㣬���ֵ��";
		}else if(response.indexOf("3")!=-1){
			temp="�����ʽ����ȷ�����飡";
		}else if(response.indexOf("-1")!=-1){
			temp="�ز�ʧ�ܣ����ٴ˳��ԣ�";
		}
		
		System.out.println("showCallDialog��ǰ����������"+context);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);           
	
		builder.setIcon(R.drawable.lxlogo);  
		builder.setTitle("������ʾ");  
		builder.setMessage(temp);  
		builder.create().show(); 
	}
	
	
	public void showReduceScoreDialog(String response){
		String temp="Ĭ��ֵ";
		if(response.indexOf("0")!=-1){
			temp="�۳����ֳɹ�";
		}else if(response.indexOf("1")!=-1){
			temp="δ֪ʧ��";
		}else if(response.indexOf("2")!=-1){
			temp="�˻������ڣ�";
		}
		
		
		System.out.println("showReduceScoreDialog��ǰ����������"+context);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);           
		builder.setIcon(R.drawable.lxlogo);  
		builder.setTitle("������ʾ");  
		builder.setMessage(temp);  
		builder.create().show(); 
	}
	
	

}
