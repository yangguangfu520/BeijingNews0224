package com.atguigu.myapplication.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.atguigu.myapplication.activity.MainActivity;
import com.atguigu.myapplication.base.BasePager;
import com.atguigu.myapplication.domain.NewsCenterBean;
import com.atguigu.myapplication.fragment.LeftMenuFragment;
import com.atguigu.myapplication.utils.ConstantUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * 作者：杨光福 on 2017/6/2 16:33
 * QQ：541433511
 * 微信：yangguangfu520
 * 作用：
 */

public class NewsPager extends BasePager {

    /**
     * 数据集合
     */
    private List<NewsCenterBean.DataBean> datas;

    public NewsPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        //把数据绑定到视图上
        Log.e("TAG", "NewsPager-数据初始化...");
        //设置标题
        tv_title.setText("新闻");

        //创建子类的视图
        TextView textView = new TextView(context);
        textView.setText("新闻页面的内容");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);

        //添加到布局上
        fl_content.addView(textView);

        //联网请求
        getDataFromNet();
    }

    private void getDataFromNet() {
        //新闻中心的网络路径
        String url = ConstantUtils.NEWSCENTER_PAGER_URL;
        OkHttpUtils
                .get()
                .url(url)
//                .addParams("username", "hyman")
//                .addParams("password", "123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "请求失败==" + e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "请求成功==" + response);
                        processData(response);
                    }


                });
    }

    private void processData(String json) {
        NewsCenterBean newsCenterBean = new Gson().fromJson(json, NewsCenterBean.class);
        Log.e("TAG", "解析成功了哦==" + newsCenterBean.getData().get(0).getChildren().get(0).getTitle());
        datas = newsCenterBean.getData();

        //传到左侧菜单
        MainActivity mainActivity = (MainActivity) context;
        //得到左侧菜单Fragment
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
        //设置数据
        leftMenuFragment.setData(datas);

    }
}
