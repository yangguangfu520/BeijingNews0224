package com.atguigu.androidandh5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static com.atguigu.androidandh5.R.id.webview;

/**
 * 作者：尚硅谷-杨光福 on 2016/7/28 11:19
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：java和js互调
 */
public class JsCallJavaVideoActivity extends Activity {

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
        webView.loadUrl("file:///android_asset/RealNetJSCallJavaActivity.htm");
//        webView.loadUrl("http://10.0.2.2:8080/assets/RealNetJSCallJavaActivity.htm");


        //设置javaScript接口监听
        webView.addJavascriptInterface(new MyInterface(),"android");

    }

    class MyInterface{
        @JavascriptInterface
        public void playVideo(int id,String videoUrl,String videoTitle){
            Toast.makeText(JsCallJavaVideoActivity.this, ""+videoUrl, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setDataAndType(Uri.parse(videoUrl),"video/*");
            startActivity(intent);
        }

    }

}
