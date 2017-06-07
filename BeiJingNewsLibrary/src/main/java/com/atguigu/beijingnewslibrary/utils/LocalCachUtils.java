package com.atguigu.beijingnewslibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 作者：杨光福 on 2017/6/7 14:10
 * QQ：541433511
 * 微信：yangguangfu520
 * 作用：
 */

public class LocalCachUtils {
    private final MemoryCachUtils momenyCachUtils;

    public LocalCachUtils(MemoryCachUtils momenyCachUtils) {
        this.momenyCachUtils = momenyCachUtils;
    }

    /**
     * 保存图片
     *
     * @param imageUrl
     * @param bitmap
     */
    public void putBitmap2Local(String imageUrl, Bitmap bitmap) {
        try {
            //sdcard/beijingnews/ljsk;l;;llkkljhjjsk
            String dir = Environment.getExternalStorageDirectory()+"/beijingnews";
            //文件名称
            String fileName = MD5Encoder.encode(imageUrl);
            //sdcard/beijingnews/ljsk;l;;llkkljhjjsk
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

            Log.e("TAG","file=="+file.getAbsolutePath());

            //保存图片
            FileOutputStream fos = new FileOutputStream(file);
            //写入数据
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

//            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 根据url获取对应的图片
     *
     * @param imageUrl
     * @return
     */
    public Bitmap getBitmap(String imageUrl) {
        try {
            //sdcard/beijingnews/ljsk;l;;llkkljhjjsk
            String dir = Environment.getExternalStorageDirectory()+"/beijingnews";
            //文件名称
            String fileName = MD5Encoder.encode(imageUrl);
            //sdcard/beijingnews/ljsk;l;;llkkljhjjsk
            File file = new File(dir, fileName);

            if (file.exists()) {

                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));

                if(bitmap != null){
                    momenyCachUtils.putBitmap2Memory(imageUrl,bitmap);
                }

                return bitmap;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
