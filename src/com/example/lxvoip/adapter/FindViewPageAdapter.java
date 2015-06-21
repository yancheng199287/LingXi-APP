package com.example.lxvoip.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

public class FindViewPageAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fragmentList =new  ArrayList<Fragment> ()  ;// Tabҳ���б�
	
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
		
		System.out.println("find��ǰѡ��"+ fragmentList.get(arg0).toString());
		return (fragmentList == null || fragmentList.size() == 0) ? null
				: fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		
		System.out.println("find�ܹ�����ҳ��"+fragmentList.size());
		// TODO Auto-generated method stub
		return fragmentList == null ? 0 : fragmentList.size();
	}
/*	
 * ���ǧ��Ҫ��  ��Ȼ����ʾ��ͼ��
	@Override
	public boolean isViewFromObject(View view, Object obj) {
	return view == obj;
	}*/

}
