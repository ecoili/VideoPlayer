package com.example.videoplayer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoplayer.Adapter.VideoAdapter;
import com.example.videoplayer.R;
import com.example.videoplayer.model.Video;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView videoRecyclerView;
    private VideoAdapter videoAdapter;
    private List<Video> videoList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
//        初始化RecyclerView
        videoRecyclerView = findViewById(R.id.video_recycler_view);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        addVideos();

//        为每个视频项之间添加分割线
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.divider));
        videoRecyclerView.addItemDecoration(divider);

//        设置适配器
        videoAdapter = new VideoAdapter(videoList,video -> {
            Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
            intent.putExtra("video_title",video.getTitle());
            intent.putExtra("video_url",video.getVideoUrl());
            startActivity(intent);
        });
        videoRecyclerView.setAdapter(videoAdapter);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
//    链接协议必须为https。http不行
//    private void addVideos(){
//        videoList.add(new Video(1,"关键词","https://i0.hdslb.com/bfs/archive/42ce53049fde1c7f48ac98547ff2ebf355e39bb8.jpg","https://www.bilibili.com/video/BV1AC4mePEyF/?spm_id_from=333.337.search-card.all.click&vd_source=12b62d7e9d8201d4051074379e184ac2"));
//        videoList.add(new Video(2,"你我","https://i0.hdslb.com/bfs/archive/953763044032117fda771f65d77b6a09c57588c6.jpg","https://www.bilibili.com/video/BV1q8411S7KJ/?spm_id_from=333.337.search-card.all.click&vd_source=12b62d7e9d8201d4051074379e184ac2"));
//        videoList.add(new Video(3,"行走的鱼","https://i0.hdslb.com/bfs/archive/338f0ca37675a672f7b5355234e8933e31e0f04e.jpg","https://www.bilibili.com/video/BV1At4y1R7Jn/?spm_id_from=333.337.search-card.all.click&vd_source=12b62d7e9d8201d4051074379e184ac2"));
//        videoList.add(new Video(4,"想见你","https://i0.hdslb.com/bfs/archive/bedfccd97e01bf3ca12c918873f9ea386737218f.jpg","https://www.bilibili.com/video/BV1oE41157cS/?spm_id_from=333.337.search-card.all.click&vd_source=12b62d7e9d8201d4051074379e184ac2"));
//        videoList.add(new Video(5,"兜圈","https://i0.hdslb.com/bfs/archive/ab89759509d9d2ad3c5544aadd56dc6f1c6dec2e.jpg","https://www.bilibili.com/video/BV1sd32zDEyZ/?spm_id_from=333.337.search-card.all.click&vd_source=12b62d7e9d8201d4051074379e184ac2"));
//        videoList.add(new Video(6,"愿与愁","https://i1.hdslb.com/bfs/archive/33f967d48cafb96bcedba8a6d6754c3eb83a4bd6.jpg","https://www.bilibili.com/video/BV11M41157mS/?spm_id_from=333.337.search-card.all.click&vd_source=12b62d7e9d8201d4051074379e184ac2"));
//        videoList.add(new Video(7,"STAR WALKING","https://i1.hdslb.com/bfs/archive/7b97703ee7d2e3943e24ddd7730983021c23accc.jpg","https://www.bilibili.com/video/BV1uP411p7nJ/?spm_id_from=333.337.search-card.all.click&vd_source=12b62d7e9d8201d4051074379e184ac2"));
//        videoList.add(new Video(8,"黑夜问白天","https://i2.hdslb.com/bfs/archive/1b99108adac3e2752f7e18ab45914a386a5691ff.jpg","https://www.bilibili.com/video/BV1q4knYPEvg?spm_id_from=333.788.recommend_more_video.-1&vd_source=12b62d7e9d8201d4051074379e184ac2"));
//    }
private void addVideos(){
    videoList.add(new Video(1,"《关键词》|动态歌词排版“落叶的位置，谱出一首诗。 ”","https://i0.hdslb.com/bfs/archive/42ce53049fde1c7f48ac98547ff2ebf355e39bb8.jpg","https://www.bilibili.com/video/BV1AC4mePEyF/?spm_id_from=333.337.search-card.all.click&vd_source=12b62d7e9d8201d4051074379e184ac2"));
    videoList.add(new Video(2,"【动态歌词排版】你我 | 命运反复颠簸 来回穿梭 失去了魂魄 更与谁人说 你我 | 故事感古风国风适合CP或者群像","https://i0.hdslb.com/bfs/archive/953763044032117fda771f65d77b6a09c57588c6.jpg","https://www.bilibili.com/video/BV1q8411S7KJ/?spm_id_from=333.337.search-card.all.click&vd_source=12b62d7e9d8201d4051074379e184ac2"));
    videoList.add(new Video(3,"【声生不息3】汪苏泷《行走的鱼》","https://i0.hdslb.com/bfs/archive/338f0ca37675a672f7b5355234e8933e31e0f04e.jpg","https://www.bilibili.com/video/BV1At4y1R7Jn/?spm_id_from=333.337.search-card.all.click&vd_source=12b62d7e9d8201d4051074379e184ac2"));
    videoList.add(new Video(4,"【李子维x黄雨萱】4分钟看完这段神仙爱情 | 千个万个时间线里，我只想见你","https://i0.hdslb.com/bfs/archive/bedfccd97e01bf3ca12c918873f9ea386737218f.jpg","https://www.bilibili.com/video/BV1oE41157cS/?spm_id_from=333.337.search-card.all.click&vd_source=12b62d7e9d8201d4051074379e184ac2"));
    videoList.add(new Video(5,"【林宥嘉】不是我说这个兜圈真的美得过分了丨20250705南通如东小洋口音乐节","https://i0.hdslb.com/bfs/archive/ab89759509d9d2ad3c5544aadd56dc6f1c6dec2e.jpg","https://www.bilibili.com/video/BV1sd32zDEyZ/?spm_id_from=333.337.search-card.all.click&vd_source=12b62d7e9d8201d4051074379e184ac2"));
    videoList.add(new Video(6,"【全球大首播】林俊杰《愿与愁》mv","https://i1.hdslb.com/bfs/archive/33f967d48cafb96bcedba8a6d6754c3eb83a4bd6.jpg","https://www.bilibili.com/video/BV11M41157mS/?spm_id_from=333.337.search-card.all.click&vd_source=12b62d7e9d8201d4051074379e184ac2"));
    videoList.add(new Video(7,"STAR WALKIN'(逐星)-Lil Nas X丨2022英雄联盟全球总决赛主题曲丨拳头游戏音乐","https://i1.hdslb.com/bfs/archive/7b97703ee7d2e3943e24ddd7730983021c23accc.jpg","https://www.bilibili.com/video/BV1uP411p7nJ/?spm_id_from=333.337.search-card.all.click&vd_source=12b62d7e9d8201d4051074379e184ac2"));
    videoList.add(new Video(8,"【4K60FPS】林俊杰、胡彦斌《黑夜问白天》神级现场！2024年炸裂现场","https://i2.hdslb.com/bfs/archive/1b99108adac3e2752f7e18ab45914a386a5691ff.jpg","https://www.bilibili.com/video/BV1q4knYPEvg?spm_id_from=333.788.recommend_more_video.-1&vd_source=12b62d7e9d8201d4051074379e184ac2"));
}
}