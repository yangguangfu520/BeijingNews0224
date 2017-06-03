package com.atguigu.myapplication.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：杨光福 on 2017/6/2 15:23
 * QQ：541433511
 * 微信：yangguangfu520
 * 作用：
 */

public abstract class BaseFragment extends Fragment {

    public Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();//MainActivity
    }

    /**
     * 初始化视图
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 由子类实现该方法，初始化控件
     * @return
     */
    public abstract View initView() ;

    /**
     * 当Activity被创建好的时候回调
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 初始化数据，
     * 当子类需要把数据绑定到控件上的时候，就重写该方法
     */
    public void initData() {

    }
}
