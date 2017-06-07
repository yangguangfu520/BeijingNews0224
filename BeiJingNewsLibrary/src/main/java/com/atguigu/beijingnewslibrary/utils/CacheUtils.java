package com.atguigu.beijingnewslibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 作者：杨光福 on 2017/6/2 14:00
 * QQ：541433511
 * 微信：yangguangfu520
 * 作用：
 */

public class CacheUtils {
    /**
     * 保存boolean类型的数据
     * @param context
     * @param key
     * @param b
     */
    public static void putBoolean(Context context,String key, boolean b) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,b).commit();
    }

    /**
     * 得到保存的值
     * @param context
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }

    /**
     * 保存文本数据
     * @param context
     * @param key
     * @param values
     */
    public static void putString(Context context, String key, String values) {
        //是使用SharedPreferences
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        sp.edit().putString(key,values).commit();

        //使用文件存储
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){


            try {
                //sdcard可用
                //sdcard/beijingnews/files/ljsk;l;;llkkljhjjsk
                String dir = Environment.getExternalStorageDirectory()+"/beijingnews/files";
                //文件名称
                String fileName = MD5Encoder.encode(key);
                //sdcard/beijingnews/files/ljsk;l;;llkkljhjjsk
                File file = new File(dir, fileName);
                //得到 //sdcard/beijingnews/
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    //创建多级目录
                    parentFile.mkdirs();
                }
                //创建文件
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(values.getBytes());
                fos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }





    }

    /**
     * 得到缓存的数据
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        String values = "";
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        values = sp.getString(key,"");

        //从文件中获取
        //使用文件存储
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){


            try {
                //sdcard可用
                //sdcard/beijingnews/files/ljsk;l;;llkkljhjjsk
                String dir = Environment.getExternalStorageDirectory()+"/beijingnews/files";
                //文件名称
                String fileName = MD5Encoder.encode(key);
                //sdcard/beijingnews/files/ljsk;l;;llkkljhjjsk
                File file = new File(dir, fileName);
                //得到 //sdcard/beijingnews/

                if(file.exists()){

                    //读取文件

                    FileInputStream fis = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int length;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    while ((length=fis.read(buffer)) != -1){
                        baos.write(buffer,0,length);
                    }

                    String content = baos.toString();

                    if(!TextUtils.isEmpty(content)){
                        values = content;
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }



        return values;
    }
}
