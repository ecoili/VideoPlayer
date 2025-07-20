package com.example.videoplayer.Activities;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.videoplayer.R;
import com.example.videoplayer.model.Video;

import java.util.List;


public class VideoPlayerActivity extends AppCompatActivity{
    private VideoView videoView;
    private ImageButton btnBack,btnPrev,btnPause,btnNext;
    private SeekBar seekBar;
    private TextView tvTitle;
    private Spinner spinnerSpeed;
    private List<Video> videoList;
    private Boolean isPlaying = true;
    private int currentPosition = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        initViews();
        currentPosition = getIntent().getIntExtra("current_position",0);
        videoList = getIntent().getParcelableArrayListExtra("video_list");
        setupVideoPlayer(videoList.get(currentPosition).getVideoUrl());
        tvTitle.setText(videoList.get(currentPosition).getTitle());
        setupSpeedSpinner();
    }
    private void initViews(){
        videoView = findViewById(R.id.video);
        btnBack = findViewById(R.id.btn_back);
        tvTitle = findViewById(R.id.tv_title);
        btnPause = findViewById(R.id.btn_pause);
        btnPrev = findViewById(R.id.btn_prev);
        btnNext = findViewById(R.id.btn_next);
        seekBar = findViewById(R.id.seek_bar);
        spinnerSpeed = findViewById(R.id.speed_ctl);
    }
    private void setupSpeedSpinner(){
        String[] speeds = {"0.5x","1.0x","1.5x","2.0x"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpeed.setAdapter(adapter);
        spinnerSpeed.setSelection(1);
        spinnerSpeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    float speed = 1.0f;
                    switch (i) {
                        case 0: speed = 0.5f; break;
                        case 1: speed = 1.0f; break;
                        case 2: speed = 1.5f; break;
                        case 3: speed = 2.0f; break;
                    }
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                        videoView.setPlaybackparams()
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void setupVideoPlayer(String videoUrl){
        videoView.setVideoPath(videoUrl);
        videoView.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.start();
            isPlaying = true;
            btnPause.setImageResource(R.drawable.ic_play);
            int duration = mediaPlayer.getDuration();
            seekBar.setMax(duration);
            updateSeekBar();
        });
        videoView.setOnCompletionListener(mediaPlayer -> {
            playNextVideo();
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b) videoView.seekTo(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(updateSeekBarRunnable);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.postDelayed(updateSeekBarRunnable,1000);
            }

    });
    }
    private void togglePlayPause(){
        if(isPlaying){
            videoView.pause();
            btnPause.setImageResource(R.drawable.ic_play);
        }
        else {
            videoView.pause();
            btnPause.setImageResource(R.drawable.ic_pause);
        }
        isPlaying = !isPlaying;
    }
    private void playVideoAtPosition(int position){
        if(position >=0 && position < videoList.size()){
            currentPosition = position;
            Video video = videoList.get(position);
            tvTitle.setText(video.getTitle());
            videoView.setVideoPath(video.getVideoUrl());
            videoView.start();
            isPlaying = true;
            btnPause.setImageResource(R.drawable.ic_play);
        }
    }
    private void playPrevVideo(){
        if(currentPosition > 0){
            currentPosition--;
            playVideoAtPosition(currentPosition);
        }
    }
    private void playNextVideo(){
        if(currentPosition < videoList.size()-1){
            currentPosition++;
            playVideoAtPosition(currentPosition);
        }
    }
    private void setupButtonListeners(){
        btnPause.setOnClickListener(view -> togglePlayPause());
        btnPrev.setOnClickListener(view -> playPrevVideo());
        btnNext.setOnClickListener(view -> playNextVideo());
        btnBack.setOnClickListener(view -> finish());
    }
    private void updateSeekBar(){
        if(isPlaying){
            int currentProgress = videoView.getCurrentPosition();
            seekBar.setProgress(currentProgress);
        }
        handler.postDelayed(updateSeekBarRunnable,1000);
    }
    private Runnable updateSeekBarRunnable = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
        }
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(updateSeekBarRunnable);
        videoView.suspend();
    }

}