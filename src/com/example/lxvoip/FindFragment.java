package com.example.lxvoip;

import java.util.ArrayList;
import java.util.List;

import com.example.lxvoip.adapter.FindViewPageAdapter;
import com.example.lxvoip.find.fragment.AdsWall;
import com.example.lxvoip.find.fragment.LxNews;
import com.example.lxvoip.find.fragment.Pay;




import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;




public class FindFragment extends Fragment {
	


	private ViewPager viewPager;// 页卡内容
	private ImageView imageView;// 动画图片
	private TextView payTv, adsTv, newsTv;// 选项名称
	private ArrayList<Fragment> fragmentList;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private int selectedColor, unSelectedColor;
	
	private Pay pay;
	private AdsWall adswall;
	private LxNews news;
	
	/** 页卡总数 **/
	private static final int pageSize = 3;
	View vv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 vv=inflater.inflate(R.layout.find, null);
		 initView();
		 
		 return vv;
	}
	
	private void initView() {
		
	//	fmg=getActivity().getChildFragmentManager();
		
		selectedColor = getResources()
				.getColor(R.color.tab_title_pressed_color);
		unSelectedColor = getResources().getColor(
				R.color.tab_title_normal_color);

		InitImageView();
		InitTextView();
		InitViewPager();
	}
	
	/**
	 * 初始化Viewpager页
	 */
	private void InitViewPager() {
		viewPager = (ViewPager) vv.findViewById(R.id.vPager);
		fragmentList = new ArrayList<Fragment>();
		
		pay=new Pay();
		adswall=new AdsWall();
		news=new LxNews();
		
		fragmentList.add(pay);
		fragmentList.add(adswall);
		fragmentList.add(news);
		
		viewPager.setAdapter(new FindViewPageAdapter(getChildFragmentManager(),fragmentList));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	
	
	
	
	/**
	 * 初始化头标
	 * 
	 */
	private void InitTextView() {
		payTv = (TextView) vv.findViewById(R.id.tab_1);
		adsTv = (TextView)vv. findViewById(R.id.tab_2);
		newsTv = (TextView) vv.findViewById(R.id.tab_3);

		payTv.setTextColor(selectedColor);
		adsTv.setTextColor(unSelectedColor);
		newsTv.setTextColor(unSelectedColor);

		/*payTv.setText("语音问答");
		adsTv.setText("健康百科");
		newsTv.setText("育儿检测");*/

		payTv.setOnClickListener(new MyOnClickListener(0));
		adsTv.setOnClickListener(new MyOnClickListener(1));
		newsTv.setOnClickListener(new MyOnClickListener(2));
	}

	
	
	/**
	 * 头标点击监听
	 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}
	
	

		public void onClick(View v) {
			System.out.print("当前选择了第："+index);
			switch (index) {
			case 0:
				payTv.setTextColor(selectedColor);
				adsTv.setTextColor(unSelectedColor);
				newsTv.setTextColor(unSelectedColor);
				break;
			case 1:
				adsTv.setTextColor(selectedColor);
				payTv.setTextColor(unSelectedColor);
				newsTv.setTextColor(unSelectedColor);
				break;
			case 2:
				newsTv.setTextColor(selectedColor);
				payTv.setTextColor(unSelectedColor);
				adsTv.setTextColor(unSelectedColor);
				break;
			}
			viewPager.setCurrentItem(index);
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	 */

	private void InitImageView() {
		imageView = (ImageView)vv. findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_selected_bg).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / pageSize - bmpW) / 2;// 计算偏移量--(屏幕宽度/页卡总数-图片实际宽度)/2
													// = 偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}

	
	
	

	/**
	 * 为选项卡绑定监听器
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		public void onPageScrollStateChanged(int index) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int index) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* index, 0, 0);// 显然这个比较简洁，只有一行代码。
			currIndex = index;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);

			switch (index) {
			case 0:
				payTv.setTextColor(selectedColor);
				adsTv.setTextColor(unSelectedColor);
				newsTv.setTextColor(unSelectedColor);
				break;
			case 1:
				adsTv.setTextColor(selectedColor);
				payTv.setTextColor(unSelectedColor);
				newsTv.setTextColor(unSelectedColor);
				break;
			case 2:
				newsTv.setTextColor(selectedColor);
				payTv.setTextColor(unSelectedColor);
				adsTv.setTextColor(unSelectedColor);
				break;
			}
		}
	}

	
	
	
}
