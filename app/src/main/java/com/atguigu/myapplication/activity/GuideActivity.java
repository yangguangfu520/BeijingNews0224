package com.atguigu.myapplication.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.atguigu.myapplication.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {

    @InjectView(R.id.vp)
    ViewPager vp;
    @InjectView(R.id.btn_start_main)
    Button btnStartMain;
    @InjectView(R.id.ll_point_group)
    LinearLayout llPointGroup;
    @InjectView(R.id.activity_guide)
    RelativeLayout activityGuide;

    private ArrayList<ImageView> imageViews;
    private int[] ids = {R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.inject(this);

        //初始化数据
        initData();

        //设置适配器
        vp.setAdapter(new MyPagerAdapter());
    }

    class MyPagerAdapter extends PagerAdapter{

        /**
         * 返回总数
         * @return
         */
        @Override
        public int getCount() {
            return imageViews.size();
        }


        /**
         * 相当于ListView适配器中的getView方法，创建item布局，要把当前视图添加到容器中
         * @param container ViewPager自身
         * @param position 要添加或者要创建的位置
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            //添加到容器中
            container.addView(imageView);
            return imageView;
        }

        /**
         * 判断当前View和instantiateItem返回的是否是同一个页面
         * @param view
         * @param object instantiateItem方法返回的
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==object;
        }

        /**
         * 销毁视图
         * @param container Viewpager
         * @param position 要销毁的位置
         * @param object 要销毁的页面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void initData() {
        imageViews = new ArrayList<>();
        for (int i=0;i<ids.length;i++){
            ImageView imageView = new ImageView(this);
            //注意要设置背景
            imageView.setBackgroundResource(ids[i]);

            //添加到集合中
            imageViews.add(imageView);
        }

    }

    @OnClick(R.id.btn_start_main)
    public void onViewClicked() {
    }
}
