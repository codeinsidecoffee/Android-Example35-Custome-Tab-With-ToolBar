package com.mrlonewolfer.example79;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;




public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
  //  TextView txtPermission;
    ArrayList<Fragment> fragmentList;
    ArrayList<String> tabArrayList;
    static int MY_PERMISSION_REQUEST =1;
    public static Context contextOfApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar);
        viewPager=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tabs);
   //     txtPermission=findViewById(R.id.txtPermission);

        //add toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setupViewPager(viewPager);

            tabLayout.setupWithViewPager(viewPager);
            setTabIcons();


    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());


        viewPagerAdapter.addFrag(new AudioFragment(),"Audio");
        viewPagerAdapter.addFrag(new VideoFragment(),"Video");
        viewPagerAdapter.addFrag(new ImageFragment(),"Image");
        viewPager.setAdapter(viewPagerAdapter);


    }

    private void setTabIcons() {
        TextView audioTab= (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_layout,null);
        audioTab.setText("Audio");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            audioTab.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_audiotrack,0,0);
        }
        tabLayout.getTabAt(0).setCustomView(audioTab);


        TextView videoTab= (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_layout,null);
        videoTab.setText("Video");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            videoTab.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_video_library,0,0);
        }
        tabLayout.getTabAt(1).setCustomView(videoTab);

        TextView imageTab= (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_layout,null);
        imageTab.setText("Images");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            imageTab.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_image,0,0);
        }
        tabLayout.getTabAt(2).setCustomView(imageTab);


    }


}
