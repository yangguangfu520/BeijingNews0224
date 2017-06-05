package com.atguigu.myapplication.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者：杨光福 on 2017/6/5 16:15
 * QQ：541433511
 * 微信：yangguangfu520
 * 作用：
 */

public class HorizontalScrollViewPager extends ViewPager {
    public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float startX;
    private float startY;

    /**
     * 在屏幕方向滑动分为：竖直方向和水平方向
     * <p>
     * 一、竖直方向
     * getParent().requestDisallowInterceptTouchEvent(false);
     * <p>
     * 二、水平方向滑动
     * <p>
     * 1.滑动方向是从左到右，并且是第0个位置，设置默认，让父层视图不禁用拦截方法
     * getParent().requestDisallowInterceptTouchEvent(false);
     * <p>
     * <p>
     * 2.滑动方向是从右到左，并且是第最后一个位置，设置默认，让父层视图不禁用拦截方法
     * getParent().requestDisallowInterceptTouchEvent(false);
     * <p>
     * 3.其他就是中间部分
     * getParent().requestDisallowInterceptTouchEvent(true);
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //让父层视图不拦截事件
                getParent().requestDisallowInterceptTouchEvent(true);
                //记录坐标
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //来到新的坐标
                float endX = ev.getX();
                float endY = ev.getY();


                //计算在X轴和Y轴滑动的绝对距离

                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);

                if (distanceX > distanceY && distanceX > 8) {
                    //                二、水平方向滑动
//
//                1.滑动方向是从左到右，并且是第0个位置，设置默认，让父层视图不禁用拦截方法
                    if((endX - startX > 0) &&getCurrentItem()==0 ){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
//
//
//
//                2.滑动方向是从右到左，并且是第最后一个位置，设置默认，让父层视图不禁用拦截方法
                    else if((startX - endX >0) && getCurrentItem()==getAdapter().getCount()-1){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
//
//
//                3.其他就是中间部分
                    else{
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
//
                } else {

//                一、竖直方向
                    getParent().requestDisallowInterceptTouchEvent(false);
//

                }


                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
