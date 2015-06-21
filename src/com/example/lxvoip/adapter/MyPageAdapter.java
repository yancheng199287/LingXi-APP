package com.example.lxvoip.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;


public class MyPageAdapter extends PagerAdapter {
	private List<View> viewlist;
	private Context context;
	public MyPageAdapter(List<View> viewlist,Context context){
		this.viewlist =viewlist;
		this.context=context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return viewlist.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	
	//����Ҳ�Ǳ�Ҫ����������Ҫ�Լ����
	//ʵ����
	
	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager)container).addView(viewlist.get(position));
		
		View view =viewlist.get(position);
		view.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i =v.getId();
				String t="������ͼƬ��ͼƬidΪ��"+i;
				Toast.makeText(context, t, Toast.LENGTH_LONG).show();
				
				/*��תҳ��
				*Intent intent =new Intent(context,NavigationActivity.class);
				*context.startActivity(intent); 
				 */ 
			
			}
			
		});
		return viewlist.get(position);				
	}
	 

	//����
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(viewlist.get(position));
	}
	
	

}
