package com.mrlonewolfer.example79;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AudioFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener,AudioRecyclerViewAdapter.onAudioClickListner {

    ImageView imgPlay,imgNext,imgPrev;
    TextView txtCurrentTime,txtTotalTime;
    ListView listView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MediaPlayer mediaPlayer,myplayer;
    RelativeLayout mycontrol;
    SeekBar seekTime;
    Handler handler=new Handler();
    List<AudioBean> audiolist;
    ArrayList<String> arrayList;
    ArrayList<AudioBean> audioArrayList;
    AudioBean audioBean;
    public static boolean playing=false;
    public static int lastsongPosition=0;
    Uri myUri;
    ArrayAdapter<String> arrayAdapter;

    int lastsongId=1400;

    boolean isStarted=false;
    final static int MY_PERMISSION_REQUEST=1;
    Context context;
    public AudioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_audio, container, false);
        context=getContext();

        seekTime=view.findViewById(R.id.seekTime);
        imgPlay=view.findViewById(R.id.imgPlay);
        imgPrev=view.findViewById(R.id.imgPrev);
        imgNext=view.findViewById(R.id.imgNext);


        recyclerView=view.findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
      //  listView=view.findViewById(R.id.listView);


        txtCurrentTime=view.findViewById(R.id.txtCurrentTime);
        txtTotalTime=view.findViewById(R.id.txtTotalTime);
        imgPlay.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        imgPrev.setOnClickListener(this);
        imgPlay.setVisibility(View.GONE);
        imgPrev.setVisibility(View.GONE);
        imgNext.setVisibility(View.GONE);
        mycontrol=view.findViewById(R.id.mycontrol);
        seekTime.setOnSeekBarChangeListener(this);
        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case MY_PERMISSION_REQUEST:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
                        Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
                        dostuff();
                    }
                }else {
                    Toast.makeText(context, "Please Go to Setting Menu and " +
                            "\n allow read write permission", Toast.LENGTH_SHORT).show();

                }
            }   return;
        }
    }

    private void dostuff() {
        arrayList=new ArrayList<>();
        audioArrayList=new ArrayList<>();
        getMusic();
        myplayer=new MediaPlayer();

        AudioRecyclerViewAdapter audioAdapter=new AudioRecyclerViewAdapter(audioArrayList,this);
        recyclerView.setAdapter(audioAdapter);

    }

    private void startMySong(Uri myUri, int position) {
        myplayer.stop();
        myplayer.reset();

        try {
            myplayer.setDataSource(getActivity(),myUri);
            myplayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myplayer.start();
        imgPlay.setVisibility(View.VISIBLE);
        imgNext.setVisibility(View.VISIBLE);
        imgPrev.setVisibility(View.VISIBLE);
        imgPlay.setImageResource(android.R.drawable.ic_media_pause);
        handler.postDelayed(runnable,1000);
        isStarted=true;
        playing=true;
        lastsongId=position;
        mycontrol.setVisibility(View.VISIBLE);
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {

//            int totalDuration=mediaPlayer.getDuration();
//            int currentPosition=mediaPlayer.getCurrentPosition();
            int totalDuration=myplayer.getDuration();
            int currentPosition=myplayer.getCurrentPosition();

            txtTotalTime.setText(getTimeFromMillis(totalDuration));
            txtCurrentTime.setText(getTimeFromMillis(currentPosition));

            int progress=(currentPosition*100)/totalDuration;
            seekTime.setProgress(progress);


            handler.postDelayed(runnable,1000);
        }
    };

    private String getTimeFromMillis(int totalDuration) {
        int hour=totalDuration/(1000*60*60);
        int minute=(totalDuration%(1000*60*60))/(1000*60);
        int seconds=((totalDuration%(1000*60*60))%(1000*60))/1000;

        return minute+" : "+seconds;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.imgPlay){
            if (isStarted==false ) {
                startMySong(myUri,lastsongId);
            }
            else{
                isStarted = false;
                playing=false;
                myplayer.pause();
                imgPlay.setImageResource(android.R.drawable.ic_media_play);
                mycontrol.setVisibility(View.GONE);
            }
        }
        if(v.getId()==R.id.imgNext){
            if(lastsongId<arrayList.size()-1){
                lastsongId=lastsongId+1;
                myUri= Uri.parse(arrayList.get(lastsongId));
                startMySong(myUri,lastsongId);
            }else{
                lastsongId=0;
                myUri= Uri.parse(arrayList.get(lastsongId));
                startMySong(myUri,lastsongId);
            }
        }
        if(v.getId()==R.id.imgPrev){
            if(lastsongId>0){
                lastsongId=lastsongId-1;
                myUri= Uri.parse(arrayList.get(lastsongId));
                startMySong(myUri,lastsongId);
            }else{
                lastsongId=arrayList.size()-1;
                myUri= Uri.parse(arrayList.get(lastsongId));
                startMySong(myUri,lastsongId);
            }

        }
    }

    private void getMusic() {
        ContentResolver contentResolver=getActivity().getContentResolver();
        Uri songUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor=null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            songCursor=contentResolver.query(songUri,null,null,null);
        }
        if(songCursor!=null && songCursor.moveToFirst()){
            int songTitle=songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist=songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songLocation=songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int songAlbum=songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int songDuration=songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);

            do{
                audioBean=new AudioBean();
                String currentTitle=songCursor.getString(songTitle);
                String currentArtist=songCursor.getString(songArtist);
                String currentLocation=songCursor.getString(songLocation);
                String currentAlbum=songCursor.getString(songAlbum);
                int currentSongDuration=songCursor.getInt(songDuration);


                audioBean.setaName(currentTitle);
                audioBean.setaArtist(currentArtist);
                audioBean.setaPath(currentLocation);
                audioBean.setaAlbum(currentAlbum);
                audioBean.setaDuration(getTimeFromMillis(currentSongDuration));
                audioBean.setSongStatus("Pause");
                audioArrayList.add(audioBean);
                arrayList.add(currentLocation);
            }while (songCursor.moveToNext());
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser){
            int totalDuration=myplayer.getDuration();
            int curretnPosition=(progress*totalDuration)/100;
            myplayer.seekTo(curretnPosition);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void OnAudioClick(AudioBean audioBean, int position) {

        myUri=Uri.parse(audioBean.aPath);
        myplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        if(isStarted==false){
            if(lastsongId!=position){
                lastsongPosition=position;
                startMySong(myUri,position);
            }else {
                isStarted = true;
                playing=true;
                myplayer.start();
                imgPlay.setImageResource(android.R.drawable.ic_media_play);
                mycontrol.setVisibility(View.VISIBLE);
            }
        }else{
            if(lastsongId==position) {
                isStarted = false;
                playing=false;
                myplayer.pause();
                imgPlay.setImageResource(android.R.drawable.ic_media_play);
                mycontrol.setVisibility(View.GONE);
            }else {
                lastsongPosition=position;
                startMySong(myUri,position);
            }

        }
    }
}
