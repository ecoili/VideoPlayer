package com.example.videoplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Video implements Parcelable {
    private int id;
    private String title;
    private String picUrl;
    private String videoUrl;
    public Video(int id, String title, String picUrl, String videoUrl) {
        this.id = id;
        this.title = title;
        this.picUrl = picUrl;
        this.videoUrl = videoUrl;
    }
    protected Video(Parcel in){
        id = in.readInt();
        title = in.readString();
        picUrl = in.readString();
        videoUrl = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel parcel) {
            return new Video(parcel);
        }

        @Override
        public Video[] newArray(int i) {
            return new Video[i];
        }
    };
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(videoUrl);
        parcel.writeString(picUrl);
    }
}
