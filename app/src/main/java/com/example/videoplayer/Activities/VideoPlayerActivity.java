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
import com.example.videoplayer.R;
import com.example.videoplayer.model.Video;

//import com.google.android.exoplayer2.MediaItem;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import java.util.List;
import java.util.Map;


public class VideoPlayerActivity extends AppCompatActivity{
    private VideoView videoView;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;
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
        initializePlayer();
        currentPosition = getIntent().getIntExtra("current_position",0);
        videoList = getIntent().getParcelableArrayListExtra("video_list");
        setupVideoPlayer(videoList.get(currentPosition).getVideoUrl());
        tvTitle.setText(videoList.get(currentPosition).getTitle());
        setupSpeedSpinner();
        setupButtonListeners();
    }
    private void initViews(){
        playerView = findViewById(R.id.player_view);
        btnBack = findViewById(R.id.btn_back);
        tvTitle = findViewById(R.id.tv_title);
        btnPause = findViewById(R.id.btn_pause);
        btnPrev = findViewById(R.id.btn_prev);
        btnNext = findViewById(R.id.btn_next);
        seekBar = findViewById(R.id.seek_bar);
        spinnerSpeed = findViewById(R.id.speed_ctl);
    }
    private void initializePlayer(){
        exoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);

        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED) {
                    playNextVideo();
                }
            }
        });

    }
    private void setupSpeedSpinner(){
        String[] speeds = {"0.5x","1.0x","1.5x","2.0x"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,speeds);
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
                    exoPlayer.setPlaybackSpeed(speed);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
//    private void setupVideoPlayer(String videoUrl){
//        videoView.setVideoPath(videoUrl);
//        videoView.setOnPreparedListener(mediaPlayer -> {
//            mediaPlayer.start();
//            isPlaying = true;
//            btnPause.setImageResource(R.drawable.ic_play);
//            int duration = mediaPlayer.getDuration();
//            seekBar.setMax(duration);
//            updateSeekBar();
//        });
//        videoView.setOnCompletionListener(mediaPlayer -> {
//            playNextVideo();
//        });
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                if(b) videoView.seekTo(i);
//            }
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                handler.removeCallbacks(updateSeekBarRunnable);
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                handler.postDelayed(updateSeekBarRunnable,1000);
//            }
//
//    });
//    }

    private void setupVideoPlayer(String videoUrl) {
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.play();

        seekBar.setMax((int) exoPlayer.getDuration());
        updateSeekBar();
    }
    private void togglePlayPause(){
        if(exoPlayer.isPlaying()){
            exoPlayer.pause();
            btnPause.setImageResource(R.drawable.ic_pause);
        }
        else {
            exoPlayer.play();
            btnPause.setImageResource(R.drawable.ic_play);
        }
//        isPlaying = !isPlaying;
    }
    private void playVideoAtPosition(int position){
        if(position >=0 && position < videoList.size()){
            currentPosition = position;
            Video video = videoList.get(position);
            tvTitle.setText(video.getTitle());
//            videoView.setVideoPath(video.getVideoUrl());
//            videoView.start();
//            isPlaying = true;
            MediaItem mediaItem = MediaItem.fromUri(video.getVideoUrl());
            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.play();
            btnPause.setImageResource(R.drawable.ic_play);
            seekBar.setMax((int) exoPlayer.getDuration());
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
        if(exoPlayer != null){
            int currentProgress = (int)exoPlayer.getCurrentPosition();
            seekBar.setProgress(currentProgress);
        }
        handler.postDelayed(updateSeekBarRunnable,1000);
    }
    private final Runnable updateSeekBarRunnable = () -> updateSeekBar();

    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(updateSeekBarRunnable);
        if(exoPlayer != null){
            exoPlayer.release();
            exoPlayer = null;
        }
    }

}