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
		  requestWindowFeature(Window.FEATURE_PROGRESS);// �ý�������ʾ�ڱ�������  
		setContentView(R.layout.webhttp);
		 init();
	}
	
	
	
	public void init(){
		webView = (WebView) findViewById(R.id.webView1);
        //WebView����web��Դ
      // webView.loadUrl("http://lightapp.baidu.com?appid=2639128");
  
		 String url = "http://lightapp.baidu.com?appid=2639128";
       webView.setWebChromeClient(new WebChromeClient(){
    	   
    	   
    	  
    	   
    	public void onProgressChanged(WebView view, int newProgress) {
    		// Activity��Webview���ݼ��س̶Ⱦ����������Ľ��ȴ�С
    		// �����ص�100%��ʱ�� �������Զ���ʧ
    		setTitle("Loading...");
    		setProgress(newProgress * 100);
    		
    	};

       }
       );
       
       webView.loadUrl(url);
       
      /* //����WebViewĬ��ʹ�õ�������ϵͳĬ�����������ҳ����Ϊ��ʹ��ҳ��WebView��
       webView.setWebViewClient(new WebViewClient(){

           @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
               //����ֵ��true��ʱ�����ȥWebView�򿪣�Ϊfalse����ϵͳ�����������������
             view.loadUrl(url);
            return true;
        }
       });*/
       
       
       
    }
	
	
	
	
	
}
