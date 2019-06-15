package com.mrlonewolfer.example79;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageBean implements Parcelable {
    Long imageId;
    String imageName,imagePath;
    int imageWidth,imageHeight;

    public ImageBean() {
    }

    protected ImageBean(Parcel in) {
        if (in.readByte() == 0) {
            imageId = null;
        } else {
            imageId = in.readLong();
        }
        imageName = in.readString();
        imagePath = in.readString();
        imageWidth = in.readInt();
        imageHeight = in.readInt();
    }

    public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>() {
        @Override
        public ImageBean createFromParcel(Parcel in) {
            return new ImageBean(in);
        }

        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (imageId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(imageId);
        }
        dest.writeString(imageName);
        dest.writeString(imagePath);
        dest.writeInt(imageWidth);
        dest.writeInt(imageHeight);
    }
}
