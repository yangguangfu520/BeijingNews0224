package com.atguigu.beijingnewslibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

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
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        sp.edit().putString(key,values).commit();
    }

    /**
     * 得到缓存的数据
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }
}
