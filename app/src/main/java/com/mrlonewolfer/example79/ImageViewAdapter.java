package com.mrlonewolfer.example79;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageViewAdapter extends BaseAdapter{
    private ArrayList<ImageBean> imageArrayList;
    private OnImageClickListner listner;
    Context context;

    public ImageViewAdapter(ArrayList<ImageBean> imageArrayList, OnImageClickListner listner,Context context) {
        this.imageArrayList = imageArrayList;
        this.listner = listner;
        this.context=context;
    }

    public interface OnImageClickListner{
        public void OnImageClick(ImageBean imageBean,int position);
    }

    @Override
    public int getCount() {
        return imageArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(parent.getContext());
            convertView=inflater.inflate(R.layout.image_row_item,parent,false);
        }
        final ImageBean imageBean=imageArrayList.get(position);
        ImageView imageView=convertView.findViewById(R.id.myimage);
        TextView textView=convertView.findViewById(R.id.textImageName);

        Picasso.with(context).load("file://"+imageBean.imagePath).into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.OnImageClick(imageBean,position);
            }
        });


        textView.setText(imageBean.getImageName());

        return convertView;
    }
}
