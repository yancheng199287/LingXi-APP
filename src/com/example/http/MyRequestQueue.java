package com.example.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;
//请求消息队列，负责处理http请求的 使用的是Volley框架


public class MyRequestQueue {
	private static MyRequestQueue mInstance;  
    private ImageLoader mImageLoader;  
    private RequestQueue mRequestQueue;  
    private static Context mCtx;  

    //构造方法，初始化上下文对象  初始化图片缓存
    private MyRequestQueue(Context context) {  
        mCtx = context;  
        mRequestQueue = getRequestQueue();  
        
        mImageLoader =new ImageLoader(mRequestQueue, new ImageCache(){
            // 这里我们设置10M的缓存大小
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

          
  
  
    
    //同步获得本对象，如果存在直接返回  不存在就创建
    public static synchronized MyRequestQueue getInstance(Context context) {  
        if (mInstance == null) {  
            mInstance = new MyRequestQueue(context);  
        }  
        return mInstance;  
    }  
  
    //获取请求队列对象，如果为空就开始创建，不为空直接返回
    public RequestQueue getRequestQueue() {  
        if (mRequestQueue == null) {  
            mRequestQueue = Volley  
                    .newRequestQueue(mCtx.getApplicationContext());  
        }  
        return mRequestQueue;  
    }  
  
    //调用这个方法 可以将一个request请求  加入到请求队列 并开始执行
    public <T> void addToRequestQueue(Request<T> req) {  
        getRequestQueue().add(req);  
    }  
  
    public ImageLoader getImageLoader() {  
        return mImageLoader;  
    }  
  
	
}
