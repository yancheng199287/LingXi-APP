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
	


	private ViewPager viewPager;// ҳ������
	private ImageView imageView;// ����ͼƬ
	private TextView payTv, adsTv, newsTv;// ѡ������
	private ArrayList<Fragment> fragmentList;// Tabҳ���б�
	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int bmpW;// ����ͼƬ���
	private int selectedColor, unSelectedColor;
	
	private Pay pay;
	private AdsWall adswall;
	private LxNews news;
	
	/** ҳ������ **/
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
	 * ��ʼ��Viewpagerҳ
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
	 * ��ʼ��ͷ��
	 * 
	 */
	private void InitTextView() {
		payTv = (TextView) vv.findViewById(R.id.tab_1);
		adsTv = (TextView)vv. findViewById(R.id.tab_2);
		newsTv = (TextView) vv.findViewById(R.id.tab_3);

		payTv.setTextColor(selectedColor);
		adsTv.setTextColor(unSelectedColor);
		newsTv.setTextColor(unSelectedColor);

		/*payTv.setText("�����ʴ�");
		adsTv.setText("�����ٿ�");
		newsTv.setText("�������");*/

		payTv.setOnClickListener(new MyOnClickListener(0));
		adsTv.setOnClickListener(new MyOnClickListener(1));
		newsTv.setOnClickListener(new MyOnClickListener(2));
	}

	
	
	/**
	 * ͷ��������
	 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}
	
	

		public void onClick(View v) {
			System.out.print("��ǰѡ���˵ڣ�"+index);
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
	 * ��ʼ���������������ҳ������ʱ������ĺ���Ҳ������Ч������������Ҫ����һЩ����
	 */

	private void InitImageView() {
		imageView = (ImageView)vv. findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_selected_bg).getWidth();// ��ȡͼƬ���
		DisplayMetrics dm = new DisplayMetrics();
		
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
		offset = (screenW / pageSize - bmpW) / 2;// ����ƫ����--(��Ļ���/ҳ������-ͼƬʵ�ʿ��)/2
													// = ƫ����
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// ���ö�����ʼλ��
	}

	
	
	

	/**
	 * Ϊѡ��󶨼�����
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
		int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����

		public void onPageScrollStateChanged(int index) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int index) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* index, 0, 0);// ��Ȼ����Ƚϼ�ֻ࣬��һ�д��롣
			currIndex = index;
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
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
