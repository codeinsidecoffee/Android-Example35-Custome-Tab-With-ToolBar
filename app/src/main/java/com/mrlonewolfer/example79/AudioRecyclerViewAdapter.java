package com.mrlonewolfer.example79;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.mrlonewolfer.example79.AudioFragment.lastsongPosition;
import static com.mrlonewolfer.example79.AudioFragment.playing;

public class AudioRecyclerViewAdapter extends RecyclerView.Adapter<AudioRecyclerViewAdapter.audioViewHolder> {
    private ArrayList<AudioBean> audioArrayList;
    private onAudioClickListner listner;

    public AudioRecyclerViewAdapter(ArrayList<AudioBean> audioArrayList, onAudioClickListner listner) {
        this.audioArrayList = audioArrayList;
        this.listner = listner;
    }

    public interface onAudioClickListner{
        void OnAudioClick(AudioBean audioBean,int position);

    }


    @NonNull
    @Override
    public audioViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.audio_raw_item,viewGroup,false);
        audioViewHolder myViewHolder=new audioViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final audioViewHolder audioViewHolder, final int position) {
        final AudioBean audioBean=audioArrayList.get(position);
        audioViewHolder.txtSongName.setText(audioBean.aName);
        audioViewHolder.txtArtistName.setText(audioBean.aArtist);
        audioViewHolder.txtSongDuration.setText(audioBean.aDuration);
        audioViewHolder.audioPlayImage.setBackgroundResource(android.R.drawable.ic_media_play);
        audioViewHolder.audioPlayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.OnAudioClick(audioBean,position);
                if(playing==true){
                    audioViewHolder.audioPlayImage.setBackgroundResource(android.R.drawable.ic_media_pause);
                }else {

                    audioViewHolder.audioPlayImage.setBackgroundResource(android.R.drawable.ic_media_play);

                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return audioArrayList.size();
    }

    public class audioViewHolder extends RecyclerView.ViewHolder{

       TextView txtSongName,txtArtistName,txtSongDuration;
       ImageView audioPlayImage;
      public audioViewHolder(@NonNull View itemView) {
          super(itemView);

          txtArtistName=itemView.findViewById(R.id.txtArtistName);
          txtSongName=itemView.findViewById(R.id.txtSongName);
          audioPlayImage=itemView.findViewById(R.id.audioPlayImage);
          txtSongDuration=itemView.findViewById(R.id.txtSongDuration);


      }
  }
}
