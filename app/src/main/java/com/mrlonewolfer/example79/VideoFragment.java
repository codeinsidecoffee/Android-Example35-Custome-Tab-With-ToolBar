package com.mrlonewolfer.example79;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment implements VideoRecyclerViewAdapter.onVideoClickListner{

    final static int MY_PERMISSION_REQUEST=1;
    ArrayList<VideoBean> videoArrayList;
    VideoBean videoBean;
    ArrayList<String> arrayList;
    Context context;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayAdapter<String> arrayAdapter;
    VideoView videoView;
    String sortOrder = "_display_name ASC";
    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_video, container, false);
        context=getContext();

        recyclerView=view.findViewById(R.id.videoRecyclerView);
        layoutManager= new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }else{
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }

        }else{
            dostuff();
        }

        return view;
    }

    private void dostuff() {
        arrayList=new ArrayList<>();
        videoArrayList=new ArrayList<>();
        getVideos();

        VideoRecyclerViewAdapter videoAdapter=new VideoRecyclerViewAdapter(videoArrayList,this);
        recyclerView.setAdapter(videoAdapter);


    }

    private void getVideos() {
        ContentResolver contentResolver=getActivity().getContentResolver();
        Uri videoUri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor videoCursor=null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            videoCursor=contentResolver.query(videoUri,null,null,null);
        }
        if(videoCursor!=null && videoCursor.moveToFirst()){


            do{
                videoBean=new VideoBean();
                int videoName=videoCursor.getColumnIndex(MediaStore.Video.Media.TITLE);
                long videoId=videoCursor.getLong(videoCursor.getColumnIndex(MediaStore.Video.Media._ID));
                int videolocation=videoCursor.getColumnIndex(MediaStore.Video.Media.DATA);
                int videoArtist=videoCursor.getColumnIndex(MediaStore.Video.Media.ARTIST);
                int videoAlbum=videoCursor.getColumnIndex(MediaStore.Video.Media.ALBUM);
                int videoDuration=videoCursor.getColumnIndex(MediaStore.Video.Media.DURATION);
                int videoWidth=videoCursor.getColumnIndex(MediaStore.Video.Media.WIDTH);
                int videoHeight=videoCursor.getColumnIndex(MediaStore.Video.Media.HEIGHT);


                String currentVideoName=videoCursor.getString(videoName);
                String currentVideoPath=videoCursor.getString(videolocation);
                String currentVideoArtist=videoCursor.getString(videoArtist);
                String currentVideoAlbum=videoCursor.getString(videoAlbum);
                int currentVideoDuration=videoCursor.getInt(videoDuration);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                Bitmap videoThumb = ThumbnailUtils.createVideoThumbnail(currentVideoPath,
                        MediaStore.Images.Thumbnails.MINI_KIND);

                videoBean.setvName(currentVideoName);
                videoBean.setVideoId(videoId);
                videoBean.setvPath(currentVideoPath);
                videoBean.setvArtist(currentVideoArtist);
                videoBean.setvAlbum(currentVideoAlbum);
                videoBean.setvDuration(getTimeFromMillis(currentVideoDuration));
                videoBean.setVideoThumb(videoThumb);
                videoBean.setWidth(videoWidth);
                videoBean.setHeight(videoHeight);

                videoArrayList.add(videoBean);
                Log.e("VideoArraylist", "\n getVideos: "+videoArrayList );
                videoThumb=null;
            }while (videoCursor.moveToNext());
        }

    }

    private String getTimeFromMillis(int totalDuration) {
        int hour=totalDuration/(1000*60*60);
        int minute=(totalDuration%(1000*60*60))/(1000*60);
        int seconds=((totalDuration%(1000*60*60))%(1000*60))/1000;

        return hour+" : "+minute+" : "+seconds;
    }

    @Override
    public void onVideoClick(VideoBean videoBean, int position) {
        int width=videoBean.getWidth();
        int height=videoBean.getHeight();
        if(width<height){
            Intent intent = new Intent(getActivity(), VideoViewPotraitActivity.class);
            intent.putExtra("videobean",videoBean);
            startActivity(intent);

        }else{
            Intent intent = new Intent(getActivity(), VideoViewLandscapeActivity.class);
            intent.putExtra("videobean",videoBean);
            startActivity(intent);
        }


    }
}
