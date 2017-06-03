package com.atguigu.myapplication.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.myapplication.activity.MainActivity;
import com.atguigu.myapplication.base.BasePager;
import com.atguigu.myapplication.base.MenuDetailBasePager;
import com.atguigu.myapplication.detailpager.InteractMenuDetailPager;
import com.atguigu.myapplication.detailpager.NewsMenuDetailPager;
import com.atguigu.myapplication.detailpager.PhotosMenuDetailPager;
import com.atguigu.myapplication.detailpager.TopicMenuDetailPager;
import com.atguigu.myapplication.detailpager.VoteMenuDetailPager;
import com.atguigu.myapplication.domain.NewsCenterBean;
import com.atguigu.myapplication.fragment.LeftMenuFragment;
import com.atguigu.myapplication.utils.ConstantUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
     * 左侧页面的数据集合
     */
    private List<NewsCenterBean.DataBean> datas;
    /**
     * 左侧菜单详情的页面集合
     */
    private List<MenuDetailBasePager> basePagers;

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
        ib_menu.setVisibility(View.VISIBLE);


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
//        NewsCenterBean newsCenterBean = new Gson().fromJson(json, NewsCenterBean.class);
        //使用系统的API解析json数据
        NewsCenterBean newsCenterBean = parseJson(json);


        Log.e("TAG", "解析成功了哦==" + newsCenterBean.getData().get(0).getChildren().get(0).getTitle());
        datas = newsCenterBean.getData();

        //传到左侧菜单
        MainActivity mainActivity = (MainActivity) context;
        //实例化详情页面
        basePagers = new ArrayList<>();
        basePagers.add(new NewsMenuDetailPager(context));//新闻详情页面
        basePagers.add(new TopicMenuDetailPager(context));//专题详情页面
        basePagers.add(new PhotosMenuDetailPager(context));//组图详情页面
        basePagers.add(new InteractMenuDetailPager(context));//互动详情页面
        basePagers.add(new VoteMenuDetailPager(context));//投票详情页面

//        swichPager(0);
        //得到左侧菜单Fragment
        LeftMenuFragment leftMenuFragment = mainActivity.getLeftMenuFragment();
        //设置数据-把数据传递给LeftMenuFragment
        leftMenuFragment.setData(datas);


    }

    private NewsCenterBean parseJson(String json) {
        NewsCenterBean newsCenterBean = new NewsCenterBean();
        try {
            JSONObject jsonObject = new JSONObject(json);
            //解析retcode
            int retcode = jsonObject.optInt("retcode");
            //设置数据
            newsCenterBean.setRetcode(retcode);

            JSONArray jsonArray = jsonObject.optJSONArray("data");
            //集合
            List<NewsCenterBean.DataBean> data = new ArrayList<>();
            newsCenterBean.setData(data);
            for (int i = 0; i < jsonArray.length(); i++) {

                //数据
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                if (jsonObject1 != null) {
                    NewsCenterBean.DataBean dataBean = new NewsCenterBean.DataBean();

                    dataBean.setId(jsonObject1.optInt("id"));
                    dataBean.setType(jsonObject1.optInt("type"));
                    String title = jsonObject1.optString("title");
                    dataBean.setTitle(title);
                    String url = jsonObject1.optString("url");
                    dataBean.setUrl(url);

                    JSONArray jsonArray1 = jsonObject1.optJSONArray("children");


                    if (jsonArray1 != null) {
                        List<NewsCenterBean.DataBean.ChildrenBean> children = new ArrayList<>();
                        //设置children数据的
                        dataBean.setChildren(children);
                        for (int i1 = 0; i1 < jsonArray1.length(); i1++) {

                            JSONObject jsonObject2 = jsonArray1.getJSONObject(i1);

                            NewsCenterBean.DataBean.ChildrenBean childrenBean = new NewsCenterBean.DataBean.ChildrenBean();

                            //解析数据了
                            childrenBean.setId(jsonObject2.optInt("id"));
                            childrenBean.setType(jsonObject2.optInt("type"));
                            childrenBean.setTitle(jsonObject2.optString("title"));
                            childrenBean.setUrl(jsonObject2.optString("url"));

                            //添加数据到集合中
                            children.add(childrenBean);



                        }

                    }
                    //添加到集合中
                    data.add(dataBean);

                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsCenterBean;
    }

    /**
     * 根据位置切换到不同的详情页面
     *
     * @param prePosition
     */
    public void swichPager(int prePosition) {

        MenuDetailBasePager basePager = basePagers.get(prePosition);//NewsMenuDetailPager,TopicMenuDetailPager...
        View rootView = basePager.rootView;
        fl_content.removeAllViews();//把之前显示的给移除

        fl_content.addView(rootView);

        //调用InitData
        basePager.initData();

    }
}
