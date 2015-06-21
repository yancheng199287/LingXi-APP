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
	//���ǲ����ĸ�����ĸ߶�
	private float mHight;
	private String[] letters = new String[] { "#", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };
	//������ĸ��
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
		//�������м�С������ʾ��ĸ�ģ�Ĭ������
		System.out.println("�߽ǳ�ʼ��");
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
		//��ȡ�����¼�
		int act = event.getAction();
		//��ȡ��ǰ�ĸ߶�ֵ
		float y = event.getY();
		
		//Ĭ������-1
		final int oldChoose = choose;
		
		// ������ָλ�ã��ҵ���Ӧ�ĶΣ���mList�ƶ��ο�ͷ��λ����
		
	/*
	 * ���mhight=270
	 * 
	 * ÿ����ĸ�ĸ߶���270/27=10
	 * 
	 * �����ǰ��������35
	 * 
	 * 35/10=3.5==3
	 * 
	 * 
	 * **/
		
		int selectIndex = (int) (y / (mHight / letters.length));
		
		//��ֹԽ��
		if(selectIndex>26){
			selectIndex=26;
		}else if(selectIndex<0){
			selectIndex=0;
		}
		
		
		
		System.out.println(mHight+"-mHight--��ô����¼���Yֵ��:"+y);
		System.out.println("����Ҫ��ȡ�������±��ǣ�"+selectIndex);
		System.out.println("�õ�����ĸ�ǣ�"+letters[selectIndex]);
		
		if (selectIndex > -1 && selectIndex < letters.length) {// ��ֹԽ��
			
			String key = letters[selectIndex];
			
			if (alphaIndexer.containsKey(key)) {
				
				//��õ�һ�γ��ֵ������ĸ���ڵļ���λ��
				int pos = alphaIndexer.get(key);
				
				if (mList.getHeaderViewsCount() > 0) {// ��ֹListView�б�������������û�С�
				/*	setSelectionFromTop()������������ListViewѡ�е�λ�ã�ͬʱ��Y������һ��ƫ������

					��setSelection()����������һ��index������ֵ���Ϳ�����ListView��λ��ָ��Item��λ�á�*/
					//�����ҵ������ĸ���ڵ�item��λ��ʾ
					this.mList.setSelectionFromTop(
							pos + mList.getHeaderViewsCount(), 0);
				System.out.println("��õ�һ�γ��ֵ������ĸ���ڵļ���λ��"+pos + mList.getHeaderViewsCount());	
				} else {
					this.mList.setSelectionFromTop(pos, 0);
					System.out.println("��õ�һ�γ��ֵ������ĸ���ڵļ���λ��"+pos );	
					
				}
				mDialogText.setText(letters[selectIndex]);
			}
		}

		switch (act) {
		//��С��dialog��ʾ����
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
			//������ͼ
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != selectIndex) {
				if (selectIndex > 0 && selectIndex < letters.length) {
					choose = selectIndex;
					invalidate();
				}
			}
			break;
			//��������
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
		/*if (showBkg) {//���ñ�������ɫ
			canvas.drawColor(Color.parseColor("#b20264"));
		}*/

		//��ȡ���View�ĸ߶�
		int height = getHeight();
		
mHight=height;

		//��ȡ���View�Ŀ��
		int width = getWidth();
		
		//ÿ����ĸ��ƽ���߶�=�ܸ߶ȳ���27��
		int singleHeight = height / letters.length;
		
		for (int i = 0; i < letters.length; i++) {
			//���û��ʵ���ɫΪ��ɫ
			paint.setColor(Color.WHITE);
			//���û������ִ�СΪ20
			paint.setTextSize(20);
			//��������
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			//���ÿ����
			paint.setAntiAlias(true);
			
			if (i == choose) {
				paint.setColor(Color.parseColor("#00BFFF"));//����ʱ���µ���ĸ��ɫ
            //���ô���
				paint.setFakeBoldText(true);
			}
		//measureTextȡ���ַ�����ʾ�Ŀ��ֵ
			//����22dp��ȵ�һ���ȥ����ʾ��ȵ�һ��   ��ʾ�м�
			float xPos = width / 2 - paint.measureText(letters[i]) / 2;
			
			//�ڶ���ĸ�ڵ�һ������
			float yPos = singleHeight * i + singleHeight;
			
			canvas.drawText(letters[i], xPos, yPos, paint);
			
			paint.reset();
		}

	}
	
	
	
	
}