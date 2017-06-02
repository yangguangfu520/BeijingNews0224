package com.atguigu.myapplication.activity;

import android.os.Bundle;

import com.atguigu.myapplication.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置主页面
        setContentView(R.layout.activity_main);

        //设置左侧菜单
        setBehindContentView(R.layout.left_menu);


        SlidingMenu slidingMenu = getSlidingMenu();
        //设置右侧菜单
//        slidingMenu.setSecondaryMenu(R.layout.left_menu);

        //设置模式：左侧+主页；左侧+主页+右侧；主页+右侧
        slidingMenu.setMode(SlidingMenu.LEFT);


        //设置滑动模式：不可用滑动，滑动编边缘，全屏滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        //设置主页面占的宽度
        slidingMenu.setBehindOffset(200);

    }
}
