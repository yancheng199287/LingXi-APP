package com.example.lxvoip.adapter;

import java.util.ArrayList;
import java.util.List;



import com.example.entity.CallsLog;
import com.example.lxvoip.DialerFragment;
import com.example.lxvoip.R;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DialerAdapter extends BaseAdapter{
	ArrayList<CallsLog> list;
	Context context;
	
	String name;
	String number;
	int type;
	String date;
	
	public DialerAdapter(Context context,ArrayList<CallsLog> list){
		this.context=context;
		this.list=list;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		CallsLog callsLog=list.get(position);
		name=callsLog.getPerisonName();
		number=callsLog.getPhoneNumber();
		type=callsLog.getType();
		date=callsLog.getDate();

		
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.dialer_lv_item, null);
			
			holder = new ViewHolder();
			holder.headImageView = (ImageView) convertView.findViewById(R.id.imageView1);
		
			holder.callActionImageView= (ImageButton) convertView.findViewById(R.id.callAction);
			holder.nameTv = (TextView) convertView.findViewById(R.id.phoneName);
			holder.numberTv = (TextView) convertView.findViewById(R.id.phoneNum);
			holder.timeTv = (TextView) convertView.findViewById(R.id.callTime);
			holder.typeTv = (TextView) convertView.findViewById(R.id.callType);
			
		
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.nameTv .setText(name);
		holder.numberTv.setText(number);
		holder.timeTv.setText(date);
		
		
		switch (type) {
		case 1:
			holder.typeTv.setText("呼出 ");
		//	holder.typeTv.setText("呼出 "+list.get(position).getTime()+"秒");
			//holder.call_type.setBackgroundResource(R.drawable.ic_calllog_outgoing_nomal);
			break;
		case 2:
			holder.typeTv.setText("呼入 ");
		//	holder.call_type.setBackgroundResource(R.drawable.ic_calllog_incomming_normal);
			break;
		case 3:
			holder.typeTv.setText("未接 ");
			//holder.call_type.setBackgroundResource(R.drawable.ic_calllog_missed_normal);
			break;
		}
		
		
		

		holder.callActionImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String called=list.get(position).getPhoneNumber();
			Toast.makeText(context, "当前号码是："+called, 3000).show();
			
				new DialerFragment().call(called);
			
			}
		});
		
		
		return convertView;
		
		

	}
	
	
	
	
	
	class ViewHolder{
		ImageView headImageView;
		ImageView callActionImageView;
		TextView numberTv;
		TextView nameTv;
		TextView timeTv;
		TextView typeTv;
		
	}
	
	
	
	
	
	
	

}
