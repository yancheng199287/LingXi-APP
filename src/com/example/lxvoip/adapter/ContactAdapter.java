package com.example.lxvoip.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;


import com.example.entity.ContactPerson;
import com.example.lxvoip.R;
import com.example.lxvoip.adapter.DialerAdapter.ViewHolder;
import com.example.lxvoip.diyview.QuickAlphabeticBar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter {
	 HashMap<String, Integer> alphaIndexer;

	 String[] sections;
	Context context;
	
	public ArrayList<ContactPerson> pList;
	String name;
	String number;
	
	public ContactAdapter(Context context,ArrayList<ContactPerson> pList,QuickAlphabeticBar alpha){
		this.context=context;
		this.pList=pList;
		this.alphaIndexer = new HashMap<String, Integer>();
		this.sections = new String[pList.size()];
		
		for (int i =0; i <pList.size(); i++) {
			//获取拼音字母的第一个字母，如果是空的或者长度为0或者匹配不了a-z字母设置为#
			String name = getAlpha(pList.get(i).getPinyin());
			
		
			if(!alphaIndexer.containsKey(name)){ 
				
				alphaIndexer.put(name, i);
			}
		}
		
		
		
		//那这个hashmap转换成一个集合
		Set<String> sectionLetters = alphaIndexer.keySet();
		
		//转成ArrayList
		ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
	
		//降序排序字母 a-z
		Collections.sort(sectionList);
		
		
		sections = new String[sectionList.size()];
		
		sectionList.toArray(sections);

		System.out.println("alpha:"+alpha);
		alpha.setAlphaIndexer(alphaIndexer);
		
		
		
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
			
			convertView=LayoutInflater.from(context).inflate(R.layout.contact_lv_item, null);
			vh=new ViewHolder();
			
			vh.tvName=(TextView) convertView.findViewById(R.id.contactName);
			vh.tvNumber=(TextView) convertView.findViewById(R.id.contactNum);
			vh.alpha=(TextView) convertView.findViewById(R.id.alpha);
			
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
		}
		
		vh.tvName.setText(name);
		vh.tvNumber.setText(number);
		
		// 首次获得当前联系人的拼音字母，然后又获取第一个字母
				String currentStr = getAlpha(cp.getPinyin());
				
				//这个就是下面的，如果下面的不等于上面的字母，就是显示首次获得
				//否则就不显示
				String previewStr = (position - 1) >= 0 ? getAlpha(pList.get(position - 1).getPinyin()) : " ";
				/**
				 *
				 */
				
				if (!previewStr.equals(currentStr)) { 
					vh.alpha.setVisibility(View.VISIBLE);
					vh.alpha.setText(currentStr);
				} else {
					vh.alpha.setVisibility(View.GONE);
				}
				
				
		
		
		
		
		
		
		
		
		return convertView;
	}
	
	
	private class ViewHolder{
		TextView tvName;
		TextView tvNumber;
		TextView alpha;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//获取拼音字母的第一个字母，如果是空的或者长度为0或者匹配不了a-z字母设置为#
			private String getAlpha(String str) {
				if (str == null) {
					return "#";
				}
				if (str.trim().length() == 0) {
					return "#";
				}
				char c = str.trim().substring(0, 1).charAt(0);
				// 
				Pattern pattern = Pattern.compile("^[A-Za-z]+$");
				
				if (pattern.matcher(c + "").matches()) {
					
					return (c + "").toUpperCase(); // 
				} else {
					return "#";
				}
			}
	

}
