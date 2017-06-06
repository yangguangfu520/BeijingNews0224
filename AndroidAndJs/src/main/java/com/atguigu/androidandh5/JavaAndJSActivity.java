package com.atguigu.androidandh5;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 作者：尚硅谷-杨光福 on 2016/7/28 11:19
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：java和js互调
 */
public class JavaAndJSActivity extends Activity implements View.OnClickListener {
    private EditText etNumber;
    private EditText etPassword;
    private Button btnLogin;
    /**
     * 加载网页或者说H5页面
     */
    private WebView webView;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-07-28 11:43:37 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        setContentView(R.layout.activity_java_and_js);
        etNumber = (EditText) findViewById(R.id.et_number);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-07-28 11:43:37 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            // Handle clicks for btnLogin
            login();
        }
    }

    private void login() {
        String name = etNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            Toast.makeText(JavaAndJSActivity.this, "账号或者密码为空", Toast.LENGTH_SHORT).show();
        } else {
            //登录
            login(name);
        }
    }

    private void  login(String name){
        //调用javascript的javaCallJs
        webView.loadUrl("javascript:javaCallJs("+"'"+name+"'"+")");
        //只显示WebView
        setContentView(webView);

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        initWebView();
    }

    private void initWebView() {
        webView = new WebView(this);
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
        webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");
        //加载网络资源
//        webView.loadUrl("http://10.0.2.2:8080/assets/JavaAndJavaScriptCall.html");


        //设置javaScript接口监听
        webView.addJavascriptInterface(new MyInterface(),"Android");



    }

    class MyInterface{
        @JavascriptInterface
        public void showToast(){
            Toast.makeText(JavaAndJSActivity.this, "我被javaScript调用了哦", Toast.LENGTH_SHORT).show();
        }

    }


}
