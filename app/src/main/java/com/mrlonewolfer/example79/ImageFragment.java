package com.mrlonewolfer.example79;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment implements ImageViewAdapter.OnImageClickListner{

    GridView gridView;
    final static int MY_PERMISSION_REQUEST=1;
    ArrayList<ImageBean> imageArrayList;
    ImageBean imageBean;
    Context context;
    public ImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_image, container, false);
        gridView=view.findViewById(R.id.gridView);
      context=getActivity();
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
        imageArrayList=new ArrayList<>();
        getImages();
        ImageViewAdapter adapter=new ImageViewAdapter(imageArrayList,this,context);
        gridView.setAdapter(adapter);
    }

    private void getImages() {
        ContentResolver contentResolver=getActivity().getContentResolver();
        Uri imageUri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor imageCursor=null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            imageCursor=contentResolver.query(imageUri,null,null,null);
        }if(imageCursor!=null && imageCursor.moveToFirst()){


            do{
                imageBean=new ImageBean();
                int imageName=imageCursor.getColumnIndex(MediaStore.Images.Media.TITLE);
                long imageID=imageCursor.getLong(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
                int imagePath=imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
                int imageWidth=imageCursor.getColumnIndex(MediaStore.Images.Media.WIDTH);
                int imageHeight=imageCursor.getColumnIndex(MediaStore.Images.Media.HEIGHT);


                String currentImageName=imageCursor.getString(imageName);
                String currentImagePath=imageCursor.getString(imagePath);


                imageBean.setImageName(currentImageName);
                imageBean.setImageId(imageID);
                imageBean.setImagePath(currentImagePath);
                imageBean.setImageWidth(imageWidth);
                imageBean.setImageHeight(imageHeight);

                imageArrayList.add(imageBean);
           //     Log.e("imageArrayList", "\n getImages: "+imageArrayList );

            }while (imageCursor.moveToNext());
        }

    }

    @Override
    public void OnImageClick(ImageBean imageBean, int position) {
        Toast.makeText(getContext(), "you succesfully clicked", Toast.LENGTH_SHORT).show();
    }
}
