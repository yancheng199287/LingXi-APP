package com.example.lxvoip.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

public class FindViewPageAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fragmentList =new  ArrayList<Fragment> ()  ;// Tab页面列表
	
	  public FindViewPageAdapter(FragmentManager fm) {
	        super(fm);
	    }
	
	public FindViewPageAdapter(FragmentManager fmg,ArrayList<Fragment> fragmentList) {
		super(fmg);
		this.fragmentList=fragmentList;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		
		System.out.println("find当前选择："+ fragmentList.get(arg0).toString());
		return (fragmentList == null || fragmentList.size() == 0) ? null
				: fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		
		System.out.println("find总共多少页："+fragmentList.size());
		// TODO Auto-generated method stub
		return fragmentList == null ? 0 : fragmentList.size();
	}
/*	
 * 这个千万不要加  不然不显示视图的
	@Override
	public boolean isViewFromObject(View view, Object obj) {
	return view == obj;
	}*/

}
