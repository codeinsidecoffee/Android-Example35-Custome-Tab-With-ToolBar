package com.mrlonewolfer.example79;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewPotraitActivity extends AppCompatActivity {
    VideoView videoView;
    VideoBean videoBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view_potrait);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        videoBean=new VideoBean();
        Intent intent=getIntent();
        videoBean=intent.getParcelableExtra("videobean");

        videoView=findViewById(R.id.videoView);
        videoView.setVideoPath(videoBean.getvPath());
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.start();
    }

}
