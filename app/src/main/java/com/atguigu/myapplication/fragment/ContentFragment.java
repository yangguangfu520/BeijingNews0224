package com.atguigu.myapplication.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.atguigu.myapplication.R;
import com.atguigu.myapplication.base.BaseFragment;

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

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_content, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //默认选中主页
        rgMain.check(R.id.rb_home);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
