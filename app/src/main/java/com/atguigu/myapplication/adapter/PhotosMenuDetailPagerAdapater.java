package com.atguigu.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.beijingnewslibrary.utils.ConstantUtils;
import com.atguigu.myapplication.R;
import com.atguigu.myapplication.activity.PicassoSampleActivity;
import com.atguigu.myapplication.domain.PhotosMenuDetailPagerBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：杨光福 on 2017/6/6 14:29
 * QQ：541433511
 * 微信：yangguangfu520
 * 作用：
 */

public class PhotosMenuDetailPagerAdapater extends RecyclerView.Adapter<PhotosMenuDetailPagerAdapater.MyViewHolder> {


    private final List<PhotosMenuDetailPagerBean.DataBean.NewsBean> datas;
    private final Context context;


    public PhotosMenuDetailPagerAdapater(Context context, List<PhotosMenuDetailPagerBean.DataBean.NewsBean> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_photos, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //1.根据位置得到对应的数据
        PhotosMenuDetailPagerBean.DataBean.NewsBean newsBean = datas.get(position);
        //2.绑定数据
        holder.tvTitle.setText(newsBean.getTitle());
        //3.设置点击事件
        String imageUrl = ConstantUtils.BASE_URL + newsBean.getListimage();
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.pic_item_list_default)
                .error(R.drawable.pic_item_list_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivIcon);

    }

    /**
     * 返回总大小
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.tv_title)
        TextView tvTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            //设置RecyclerView的item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //跳转到显示图片的Activity页面，使用的PhotoView
                    String imageUrl = ConstantUtils.BASE_URL + datas.get(getLayoutPosition()).getListimage();
                    Intent intent = new Intent(context, PicassoSampleActivity.class);
                    intent.setData(Uri.parse(imageUrl));
                    context.startActivity(intent);

                }
            });
        }
    }
}
