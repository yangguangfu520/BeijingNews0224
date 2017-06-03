package com.atguigu.myapplication.fragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.atguigu.myapplication.R;
import com.atguigu.myapplication.activity.MainActivity;
import com.atguigu.myapplication.base.BaseFragment;
import com.atguigu.myapplication.base.BasePager;
import com.atguigu.myapplication.pager.HomePager;
import com.atguigu.myapplication.pager.NewsPager;
import com.atguigu.myapplication.pager.SettingPager;
import com.atguigu.myapplication.view.NoViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：杨光福 on 2017/6/2 15:27
 * QQ：541433511
 * 微信：yangguangfu520
 * 作用：
 */

public class ContentFragment extends BaseFragment {

    @InjectView(R.id.vp)
    NoViewPager  vp;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;

    /**
     * 装页面的
     */
    private ArrayList<BasePager> pagers;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_content, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //设ViewPager的数据-适配器
        //准备数据
        pagers = new ArrayList<>();
        pagers.add(new HomePager(context));//主页面
        pagers.add(new NewsPager(context));//新闻中心
        pagers.add(new SettingPager(context));//设置中心

        //设置适配器
        vp.setAdapter(new MyAdapter());
        //设置RadioGroup的监听
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home://主页面
                        //ViewPager.setCurrentItem(position)
                        vp.setCurrentItem(0,false);
                        //调用initData方法
//                        pagers.get(0).initData();
                        break;
                    case R.id.rb_news://新闻
                        vp.setCurrentItem(1,false);
//                        pagers.get(1).initData();
                        break;
                    case R.id.rb_setting://设置
                        vp.setCurrentItem(2,false);
//                        pagers.get(2).initData();
                        break;
                }
            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagers.get(position).initData();
                if(position ==1){
                    //可以侧滑
                    isEnableSlidingMenu(context, SlidingMenu.TOUCHMODE_FULLSCREEN);
                }else{
                    //不可用侧滑
                    isEnableSlidingMenu((MainActivity) context, SlidingMenu.TOUCHMODE_NONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pagers.get(0).initData();
        //默认选中主页
        rgMain.check(R.id.rb_home);

        //不可用侧滑
        isEnableSlidingMenu((MainActivity) context, SlidingMenu.TOUCHMODE_NONE);
    }

    /**
     * 是否让SlidingMenu可以滑动
     * @param context
     * @param touchmodeFullscreen
     */
    private static void isEnableSlidingMenu(Context context, int touchmodeFullscreen) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullscreen);
    }

    class MyAdapter extends PagerAdapter{
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = pagers.get(position);//HomePager,NewsPager,SettingPager
            //视图
            View rootView = basePager.rootView;
            //调用initData方法
            //basePager.initData();//HomePager,NewsPager,SettingPager
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==object;
        }

        @Override
        public int getCount() {
            return pagers.size();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
