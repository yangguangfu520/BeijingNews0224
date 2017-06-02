package com.atguigu.myapplication.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.atguigu.myapplication.R;
import com.atguigu.myapplication.base.BaseFragment;
import com.atguigu.myapplication.base.BasePager;
import com.atguigu.myapplication.pager.HomePager;
import com.atguigu.myapplication.pager.NewsPager;
import com.atguigu.myapplication.pager.SettingPager;

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
    ViewPager vp;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;

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

        vp.setAdapter(new MyAdapter());
        //默认选中主页
        rgMain.check(R.id.rb_home);
    }

    class MyAdapter extends PagerAdapter{
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = pagers.get(position);//HomePager,NewsPager,SettingPager
            View rootView = basePager.rootView;
            //调用initData方法
            basePager.initData();//HomePager,NewsPager,SettingPager
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
