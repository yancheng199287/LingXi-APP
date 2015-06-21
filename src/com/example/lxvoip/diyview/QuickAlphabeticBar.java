package com.example.lxvoip.diyview;

import java.util.HashMap;

import com.example.lxvoip.R;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


public class QuickAlphabeticBar extends ImageButton{
	private TextView mDialogText;
	private Handler mHandler;
	private ListView mList;
	//就是侧边字母竖条的高度
	private float mHight;
	private String[] letters = new String[] { "#", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };
	//带有字母的
	private HashMap<String, Integer> alphaIndexer;
	
	
	public QuickAlphabeticBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public QuickAlphabeticBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void init(View vv) {
		//就是正中间小方框显示字母的，默认隐藏
		System.out.println("边角初始化");
		mDialogText = (TextView) vv.findViewById(R.id.fast_position);
		mDialogText.setVisibility(View.INVISIBLE);
		mHandler = new Handler();
	}
	public void setListView(ListView mList) {
		this.mList = mList;
	}

	public void setAlphaIndexer(HashMap<String, Integer> alphaIndexer) {
		this.alphaIndexer = alphaIndexer;
	}

	public void setHight(float mHight) {
		this.mHight = mHight;
	}
	
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//获取触摸事件
		int act = event.getAction();
		//获取当前的高度值
		float y = event.getY();
		
		//默认设置-1
		final int oldChoose = choose;
		
		// 计算手指位置，找到对应的段，让mList移动段开头的位置上
		
	/*
	 * 如果mhight=270
	 * 
	 * 每个字母的高度是270/27=10
	 * 
	 * 如果当前触摸的是35
	 * 
	 * 35/10=3.5==3
	 * 
	 * 
	 * **/
		
		int selectIndex = (int) (y / (mHight / letters.length));
		
		//防止越界
		if(selectIndex>26){
			selectIndex=26;
		}else if(selectIndex<0){
			selectIndex=0;
		}
		
		
		
		System.out.println(mHight+"-mHight--获得触摸事件的Y值是:"+y);
		System.out.println("计算要获取的数组下标是："+selectIndex);
		System.out.println("得到的字母是："+letters[selectIndex]);
		
		if (selectIndex > -1 && selectIndex < letters.length) {// 防止越界
			
			String key = letters[selectIndex];
			
			if (alphaIndexer.containsKey(key)) {
				
				//获得第一次出现的这个字母所在的集合位置
				int pos = alphaIndexer.get(key);
				
				if (mList.getHeaderViewsCount() > 0) {// 防止ListView有标题栏，本例中没有。
				/*	setSelectionFromTop()的作用是设置ListView选中的位置，同时在Y轴设置一个偏移量。

					而setSelection()方法，传入一个index整型数值，就可以让ListView定位到指定Item的位置。*/
					//就是找到这个字母所在的item定位显示
					this.mList.setSelectionFromTop(
							pos + mList.getHeaderViewsCount(), 0);
				System.out.println("获得第一次出现的这个字母所在的集合位置"+pos + mList.getHeaderViewsCount());	
				} else {
					this.mList.setSelectionFromTop(pos, 0);
					System.out.println("获得第一次出现的这个字母所在的集合位置"+pos );	
					
				}
				mDialogText.setText(letters[selectIndex]);
			}
		}

		switch (act) {
		//将小的dialog显示出来
		case MotionEvent.ACTION_DOWN:
			showBkg = true;

			if (oldChoose != selectIndex) {
				if (selectIndex > 0 && selectIndex < letters.length) {
					choose = selectIndex;
					invalidate();
				}
			}

			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if (mDialogText != null
								&& mDialogText.getVisibility() == View.INVISIBLE) {
							mDialogText.setVisibility(VISIBLE);
						}
					}
				});
			}
			break;
			//重置视图
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != selectIndex) {
				if (selectIndex > 0 && selectIndex < letters.length) {
					choose = selectIndex;
					invalidate();
				}
			}
			break;
			//进行隐藏
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if (mDialogText != null
								&& mDialogText.getVisibility() == View.VISIBLE) {
							mDialogText.setVisibility(INVISIBLE);
						}
					}
				});
			}
			break;
		default:
			break;
		}

		return super.onTouchEvent(event);
	}

	//TODO:
	Paint paint = new Paint();
	boolean showBkg = false;
	int choose = -1;

	
	
	
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/*if (showBkg) {//设置边栏背景色
			canvas.drawColor(Color.parseColor("#b20264"));
		}*/

		//获取这个View的高度
		int height = getHeight();
		
mHight=height;

		//获取这个View的宽度
		int width = getWidth();
		
		//每个字母的平均高度=总高度除以27个
		int singleHeight = height / letters.length;
		
		for (int i = 0; i < letters.length; i++) {
			//设置画笔的颜色为白色
			paint.setColor(Color.WHITE);
			//设置绘制文字大小为20
			paint.setTextSize(20);
			//设置字体
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			//设置抗锯齿
			paint.setAntiAlias(true);
			
			if (i == choose) {
				paint.setColor(Color.parseColor("#00BFFF"));//滑动时按下的字母颜色
            //设置粗体
				paint.setFakeBoldText(true);
			}
		//measureText取得字符串显示的宽度值
			//用总22dp宽度的一半减去字显示宽度的一半   显示中间
			float xPos = width / 2 - paint.measureText(letters[i]) / 2;
			
			//第二字母在第一个下面
			float yPos = singleHeight * i + singleHeight;
			
			canvas.drawText(letters[i], xPos, yPos, paint);
			
			paint.reset();
		}

	}
	
	
	
	
}