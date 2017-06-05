package com.atguigu.myapplication.detailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.myapplication.base.MenuDetailBasePager;
import com.atguigu.myapplication.domain.NewsCenterBean;

/**
 * 作者：杨光福 on 2017/6/5 09:24
 * QQ：541433511
 * 微信：yangguangfu520
 * 作用：
 */

public class TabDetailPager extends MenuDetailBasePager {
    private final NewsCenterBean.DataBean.ChildrenBean childrenBean;
    private TextView textView;
    public TabDetailPager(Context context, NewsCenterBean.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;
    }

    @Override
    public View initView() {
        //创建子类的视图
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        //设置数据
        textView.setText(childrenBean.getTitle());
    }
}
