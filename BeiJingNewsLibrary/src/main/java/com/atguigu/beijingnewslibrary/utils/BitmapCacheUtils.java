package com.atguigu.beijingnewslibrary.utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

/**
 * 作者：杨光福 on 2017/6/7 11:32
 * QQ：541433511
 * 微信：yangguangfu520
 * 作用：
 */

public class BitmapCacheUtils {

    /**
     * 网络缓存工具类
     */

    private NetCachUtils netCachUtils;
    /**
     * 本地缓存工具类
     */
    private LocalCachUtils localCachUtils;

    /**
     * 内存环境工具类
     */
    private MemoryCachUtils memoryCachUtils;

    public BitmapCacheUtils(Handler handler) {
        memoryCachUtils = new MemoryCachUtils();
        localCachUtils = new LocalCachUtils(memoryCachUtils);
        netCachUtils = new NetCachUtils(handler, localCachUtils, memoryCachUtils);
    }

    /**
     * 三级缓存设计步骤：
     *   * 从内存中取图片
     *   * 从本地文件中取图片
     *        向内存中保持一份
     *   * 请求网络图片，获取图片，显示到控件上
     *      * 向内存存一份
     *      * 向本地文件中存一份
     *
     * @param imageUrl
     * @param position
     * @return
     */
    public Bitmap getBitmap(String imageUrl, int position) {
        // 从内存中取图片
        if(memoryCachUtils != null){
            Bitmap bitmap = memoryCachUtils.getBitmapFromMemory(imageUrl);
            if(bitmap != null){
                Log.e("TAG", "图片是从内存获取的哦==" + position);
                return  bitmap;
            }
        }

        //从本地文件中取图片
        if (localCachUtils != null) {
            Bitmap bitmap = localCachUtils.getBitmap(imageUrl);
            if (bitmap != null) {
                Log.e("TAG", "图片是从本地获取的哦==" + position);
                return bitmap;
            }
        }

        //请求网络图片，获取图片，显示到控件上
        netCachUtils.getBitmapFromNet(imageUrl, position);

        return null;
    }
}
