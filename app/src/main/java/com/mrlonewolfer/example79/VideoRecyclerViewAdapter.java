package com.mrlonewolfer.example79;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.videoViewHolder> {

    private ArrayList<VideoBean> videoArrayList;
    private  onVideoClickListner listener;

    public VideoRecyclerViewAdapter(ArrayList<VideoBean> videoArrayList, onVideoClickListner listener) {
        this.videoArrayList = videoArrayList;
        this.listener = listener;
    }

    public interface onVideoClickListner{
        void onVideoClick(VideoBean videoBean,int position);
    }
    @NonNull
    @Override
    public videoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_raw_item,viewGroup,false);
        videoViewHolder myVideoHolder=new videoViewHolder(view);
        return myVideoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull videoViewHolder videoViewHolder, final int position) {
        final VideoBean videoBean=videoArrayList.get(position);
         videoViewHolder.imageView.setImageBitmap(videoBean.getVideoThumb());
        videoViewHolder.txtVideoName.setText(videoBean.getvName());
        videoViewHolder.txtVideoDuration.setText(videoBean.getvDuration());
        videoViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onVideoClick(videoBean,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    public class videoViewHolder extends RecyclerView.ViewHolder{

        TextView txtVideoName,txtVideoDuration;
        ImageView imageView;
        public videoViewHolder(@NonNull View itemView) {
            super(itemView);

            txtVideoName=itemView.findViewById(R.id.txtVideoName);
            txtVideoDuration=itemView.findViewById(R.id.txtVideoDuration);
            imageView=itemView.findViewById(R.id.imageView);

        }
    }
}
