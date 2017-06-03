package com.atguigu.myapplication.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者：杨光福 on 2017/6/3 09:13
 * QQ：541433511
 * 微信：yangguangfu520
 * 作用：
 */

public class NoViewPager extends ViewPager {
    public NoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 触摸事件
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;//把事件消费
    }
}
