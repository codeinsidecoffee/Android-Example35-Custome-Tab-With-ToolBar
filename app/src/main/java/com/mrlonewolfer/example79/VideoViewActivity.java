package com.mrlonewolfer.example79;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.widget.MediaController;
import android.widget.VideoView;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
public class VideoViewActivity extends AppCompatActivity {
    VideoView videoView;
    VideoBean videoBean;
    OrientationEventListener myOrientationEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_view);

        videoBean=new VideoBean();
        Intent intent=getIntent();
        videoBean=intent.getParcelableExtra("videobean");

        videoView=findViewById(R.id.videoView);
        videoView.setVideoPath(videoBean.getvPath());
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.start();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int width=videoBean.getWidth();
        int height=videoBean.getHeight();

        if(width>height){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }


}
