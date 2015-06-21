package com.example.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;
//������Ϣ���У�������http����� ʹ�õ���Volley���


public class MyRequestQueue {
	private static MyRequestQueue mInstance;  
    private ImageLoader mImageLoader;  
    private RequestQueue mRequestQueue;  
    private static Context mCtx;  

    //���췽������ʼ�������Ķ���  ��ʼ��ͼƬ����
    private MyRequestQueue(Context context) {  
        mCtx = context;  
        mRequestQueue = getRequestQueue();  
        
        mImageLoader =new ImageLoader(mRequestQueue, new ImageCache(){
            // ������������10M�Ļ����С
	      int  maxSize= 10 * 1024 * 1024;
        	  private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(  
        			  maxSize);
        	  
			@Override
			public Bitmap getBitmap(String url) {
				// TODO Auto-generated method stub
				 return cache.get(url);  
			}

			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				cache.put(url, bitmap);  
				
			}
        	
        }); 
	
    
    }

          
  
  
    
    //ͬ����ñ������������ֱ�ӷ���  �����ھʹ���
    public static synchronized MyRequestQueue getInstance(Context context) {  
        if (mInstance == null) {  
            mInstance = new MyRequestQueue(context);  
        }  
        return mInstance;  
    }  
  
    //��ȡ������ж������Ϊ�վͿ�ʼ��������Ϊ��ֱ�ӷ���
    public RequestQueue getRequestQueue() {  
        if (mRequestQueue == null) {  
            mRequestQueue = Volley  
                    .newRequestQueue(mCtx.getApplicationContext());  
        }  
        return mRequestQueue;  
    }  
  
    //����������� ���Խ�һ��request����  ���뵽������� ����ʼִ��
    public <T> void addToRequestQueue(Request<T> req) {  
        getRequestQueue().add(req);  
    }  
  
    public ImageLoader getImageLoader() {  
        return mImageLoader;  
    }  
  
	
}
