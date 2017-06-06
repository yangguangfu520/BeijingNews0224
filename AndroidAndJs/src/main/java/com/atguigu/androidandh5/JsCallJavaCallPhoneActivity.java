package com.atguigu.androidandh5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static com.atguigu.androidandh5.R.id.webview;

/**
 * 作者：尚硅谷-杨光福 on 2016/7/28 11:19
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：java和js互调
 */
public class JsCallJavaCallPhoneActivity extends Activity {


    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_call_java_video);
        webView = (WebView)findViewById(webview);
        webView.getSettings().setJavaScriptEnabled(true);

        //设置客户端
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //加载点击的连接
                view.loadUrl(url);
                return true;
            }
        });

        //加载网页
        webView.loadUrl("file:///android_asset/JsCallJavaCallPhone.html");
//        webView.loadUrl("http://10.0.2.2:8080/assets/JsCallJavaCallPhone.html");


        //设置javaScript接口监听
        webView.addJavascriptInterface(new MyInterface(),"Android");

    }

    class MyInterface{

        @JavascriptInterface
        public void showcontacts(){
            // 下面的代码建议在子线程中调用
            String json = "[{\"name\":\"阿福\", \"phone\":\"18600012345\"},{\"name\":\"阿福2\", \"phone\":\"18600012346\"}]";
            // 调用JS中的方法
            webView.loadUrl("javascript:show('" + json + "')");
        }
        @JavascriptInterface
        public void call(String number){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
           startActivity(intent);
        }


    }

}
