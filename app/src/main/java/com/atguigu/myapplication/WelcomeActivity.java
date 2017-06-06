package com.atguigu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.atguigu.beijingnewslibrary.utils.CacheUtils;
import com.atguigu.myapplication.activity.GuideActivity;
import com.atguigu.myapplication.activity.MainActivity;

public class WelcomeActivity extends AppCompatActivity {

    private RelativeLayout rl_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //初始化控件
        rl_root = (RelativeLayout)findViewById(R.id.rl_root);

        //设置三个动画：渐变动画，缩放动画，旋转动画

        AlphaAnimation aa = new AlphaAnimation(0,1);
        //动画播放持续时间
        aa.setDuration(2000);
        //停留在播放后的状态
        aa.setFillAfter(true);

        ScaleAnimation sa = new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        sa.setDuration(2000);
        sa.setFillAfter(true);

        RotateAnimation ra = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(2000);
        ra.setFillAfter(true);


        //把动画加入到集合中
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(sa);
        set.addAnimation(ra);
        set.addAnimation(aa);


        //开始播放动画
        rl_root.startAnimation(set);

        //监听动画播放完成
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

               boolean isStartMain =  CacheUtils.getBoolean(WelcomeActivity.this,"start_main");
                if(isStartMain){
                    //直接进入主页面
                    Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    //进入引导页面
                    Intent intent = new Intent(WelcomeActivity.this,GuideActivity.class);
                    startActivity(intent);
                }
                //关闭欢迎页面
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
