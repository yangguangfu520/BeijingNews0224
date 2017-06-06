package com.atguigu.myapplication.detailpager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.atguigu.beijingnewslibrary.utils.ConstantUtils;
import com.atguigu.myapplication.R;
import com.atguigu.myapplication.adapter.PhotosMenuDetailPagerAdapater;
import com.atguigu.myapplication.base.MenuDetailBasePager;
import com.atguigu.myapplication.domain.NewsCenterBean;
import com.atguigu.myapplication.domain.PhotosMenuDetailPagerBean;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * 作者：杨光福 on 2017/6/3 14:18
 * QQ：541433511
 * 微信：yangguangfu520
 * 作用：
 */

public class PhotosMenuDetailPager extends MenuDetailBasePager {

    private final NewsCenterBean.DataBean dataBean;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.progressbar)
    ProgressBar progressbar;
    private String url;
    private PhotosMenuDetailPagerAdapater adapater;
    /**
     * 图组的数据
     */
    private List<PhotosMenuDetailPagerBean.DataBean.NewsBean> datas;

    public PhotosMenuDetailPager(Context context, NewsCenterBean.DataBean dataBean) {
        super(context);
        this.dataBean = dataBean;
    }


    @Override
    public View initView() {
        //创建子类的视图
        View view = View.inflate(context, R.layout.pager_photos_menu_detail, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //联网请求
        url = ConstantUtils.BASE_URL + dataBean.getUrl();
        getDataFromNet(url);
    }

    private void getDataFromNet(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "图组请求失败==" + e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "图组请求成功==");
                        processData(response);

                    }


                });
    }

    private void processData(String json) {
        PhotosMenuDetailPagerBean bean = new Gson().fromJson(json, PhotosMenuDetailPagerBean.class);
        datas = bean.getData().getNews();

        if(datas != null && datas.size() >0){
            //有数据
            progressbar.setVisibility(View.GONE);
            adapater = new PhotosMenuDetailPagerAdapater(context,datas);
            //设置适配器
            recyclerview.setAdapter(adapater);

            //布局管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        }else{
            //没有数据
            progressbar.setVisibility(View.VISIBLE);
        }

    }

    /**
     * true:显示List效果
     * false:显示Grid
     */
    private boolean isShowList = true;

    /**
     * 设置List和Grid风格的切换和按钮的设置
     * @param iv
     */
    public void swichListAndGrid(ImageButton iv) {
        if(isShowList){
            //显示Grid效果
            recyclerview.setLayoutManager(new GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false));
            isShowList = false;
            //按钮状态-List
            iv.setImageResource(R.drawable.icon_pic_list_type);
//            adapater.notifyItemChanged(0,datas.size());
        }else{
            //显示List
            //布局管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            isShowList = true;
            //按钮状态-Grid
            iv.setImageResource(R.drawable.icon_pic_grid_type);
//            adapater.notifyItemChanged(0,datas.size());
        }

    }
}
