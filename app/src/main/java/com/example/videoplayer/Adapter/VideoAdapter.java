package com.example.videoplayer.Adapter;
import com.bumptech.glide.Glide;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.videoplayer.R;
import com.example.videoplayer.model.Video;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<Video> videoList;
//    点击事件监听器
    private OnItemClickListener listener;
//    VideoViewHolder是静态内部类
//    缓存 item 布局中的视图引用，避免重复 findViewById
    static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView videoPic;
        TextView videoTitle;

        public VideoViewHolder(@NotNull View itemView){
            super(itemView);
//            通过对象view得到视频图片和标题
            videoPic = itemView.findViewById(R.id.video_pic);
            videoTitle = itemView.findViewById(R.id.video_title);
        }
    }
    //    VideoAdapter的构造方法，初始化时传入视频列表和监听器
    public VideoAdapter(List<Video> videoList, OnItemClickListener listener) {
        this.videoList = videoList;
        this.listener = listener;
    }
//    视频item点击回调接口，activity可以实现这个接口来响应点击事件
    public interface OnItemClickListener{
//        传入点击的视频作为参数
        void onItemClick(Video video);
    }



    @NotNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NotNull ViewGroup parent,int ViewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video,parent,false); //加载布局文件item.video，传递给VideoViewHolder的构造函数
//        onCreate方法返回创建的holder
        return new VideoViewHolder(view);
    }
//    将数据绑定到ViewHolder的ui组件上
//    使用glide异步加载缩略图
//    设置点击事件
    @Override
    public void onBindViewHolder(@NotNull VideoViewHolder holder,int position){
//        得到点击的对象
        Video video = videoList.get(position);
//        设置标题
        holder.videoTitle.setText(video.getTitle());
//        通过Glide 异步加载缩略图——也可选择不用，看后面怎么改
//        placeholder设置加载中的占位图
        Glide.with(holder.itemView.getContext())
                .load(video.getPicUrl())
                .placeholder(R.drawable.ph_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                        Log.e("Glide","Load failed",e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.videoPic);
        holder.itemView.setOnClickListener(v-> listener.onItemClick(video));
    }
    @Override
    public int getItemCount(){
        return videoList.size();
    }

}
