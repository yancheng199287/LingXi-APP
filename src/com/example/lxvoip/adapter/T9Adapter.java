package com.example.lxvoip.adapter;

import java.util.ArrayList;

import com.example.application.MyApplication;
import com.example.entity.ContactPerson;
import com.example.lxvoip.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class T9Adapter extends BaseAdapter{

	String name;
	String number;
	public ArrayList<ContactPerson> pList;
	public Context context;

	public T9Adapter(Context context,ArrayList<ContactPerson>  pList){
		this.context=context;
		this.pList=pList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh=null;
		
		ContactPerson cp=pList.get(position);
		name=cp.getPerisonName();
		number=cp.getPhoneNumber();
		if(convertView==null){
			vh=new ViewHolder();
			
			System.out.println("t9ÊÊÅäÆ÷µÄcontext"+context);
			convertView=LayoutInflater.from(context).inflate(R.layout.t9_item, null);
			
	
			
			vh.t9Name=(TextView) convertView.findViewById(R.id.t9_contactName);
			vh.t9Number=(TextView) convertView.findViewById(R.id.t9_contactNum);
			
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		
		vh.t9Name.setText(name);
		vh.t9Number.setText(number);
		
		
		return convertView;
	}

	
	
	
	public class ViewHolder{
		TextView t9Name,t9Number;
		
	}




	
	
}
