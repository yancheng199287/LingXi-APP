package com.example.lxvoip.domain.fragment;

import com.example.lxvoip.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Http extends Activity {

	WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		  requestWindowFeature(Window.FEATURE_PROGRESS);// 让进度条显示在标题栏上  
		setContentView(R.layout.webhttp);
		 init();
	}
	
	
	
	public void init(){
		webView = (WebView) findViewById(R.id.webView1);
        //WebView加载web资源
      // webView.loadUrl("http://lightapp.baidu.com?appid=2639128");
  
		 String url = "http://lightapp.baidu.com?appid=2639128";
       webView.setWebChromeClient(new WebChromeClient(){
    	   
    	   
    	  
    	   
    	public void onProgressChanged(WebView view, int newProgress) {
    		// Activity和Webview根据加载程度决定进度条的进度大小
    		// 当加载到100%的时候 进度条自动消失
    		setTitle("Loading...");
    		setProgress(newProgress * 100);
    		
    	};

       }
       );
       
       webView.loadUrl(url);
       
      /* //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
       webView.setWebViewClient(new WebViewClient(){

           @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
             view.loadUrl(url);
            return true;
        }
       });*/
       
       
       
    }
	
	
	
	
	
}
