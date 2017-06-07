package com.atguigu.beijingnewslibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者：杨光福 on 2017/6/7 11:37
 * QQ：541433511
 * 微信：yangguangfu520
 * 作用：
 */

public class NetCachUtils {
    /**
     * 请求图片成功
     */
    public static final int SUCESS = 1;
    /*
    请求图片失败
     */
    public static final int FAIL = 2;
    private final Handler handler;
    private final ExecutorService executorService;
    /**
     * 本地缓存工具类
     */
    private final LocalCachUtils localCachUtils;

    public NetCachUtils(Handler handler, LocalCachUtils localCachUtils) {
        this.handler = handler;
        this.localCachUtils = localCachUtils;
        executorService = Executors.newFixedThreadPool(10);
    }

    public void getBitmapFromNet(String imageUrl, int position) {
        //开启子线程
//        new Thread(new MyRunnable(imageUrl, position)).start();
        executorService.execute(new MyRunnable(imageUrl, position));
    }

    class MyRunnable implements Runnable {

        private final String imageUrl;
        private final int position;

        public MyRunnable(String imageUrl, int position) {
            this.imageUrl = imageUrl;
            this.position = position;
        }

        @Override
        public void run() {
            //使用原生的方式请求图片
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) new URL(imageUrl).openConnection();
                urlConnection.setRequestMethod("GET");//大小
                urlConnection.setReadTimeout(5000);
                urlConnection.setConnectTimeout(5000);
                int code = urlConnection.getResponseCode();
                if (200 == code) {
                    //请求图片成功
                    InputStream is = urlConnection.getInputStream();
                    //把流转换成bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(is);

                    //发送到主线程
                    Message msg = Message.obtain();
                    msg.obj = bitmap;//图片
                    msg.what = SUCESS;//成功信息
                    msg.arg1 = position;
                    handler.sendMessage(msg);

                    //在内存中保存一份
                    //在本地中保存一份
                    localCachUtils.putBitmap2Local(imageUrl,bitmap);

                }
            } catch (IOException e) {
                e.printStackTrace();
                Message msg = Message.obtain();
                msg.what = FAIL;//成功信息
                msg.arg1 = position;
                handler.sendMessage(msg);
            }

        }
    }
}
