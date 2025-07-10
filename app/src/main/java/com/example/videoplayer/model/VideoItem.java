package com.example.videoplayer.model;

public class VideoItem {
    private int id;
    private String title;
    private String picUrl;
    private String videoUrl;
    public VideoItem(int id, String title, String picUrl, String videoUrl) {
        this.id = id;
        this.title = title;
        this.picUrl = picUrl;
        this.videoUrl = videoUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
