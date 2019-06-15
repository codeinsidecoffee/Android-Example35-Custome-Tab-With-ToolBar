package com.mrlonewolfer.example79;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class VideoBean implements Parcelable {
    String vPath;
    String vName;
    String vAlbum;
    String vArtist;
    String vDuration;
    Bitmap videoThumb;
    Long videoId;
    int width,height;

    public VideoBean() {
    }


    protected VideoBean(Parcel in) {
        vPath = in.readString();
        vName = in.readString();
        vAlbum = in.readString();
        vArtist = in.readString();
        vDuration = in.readString();
        videoThumb = in.readParcelable(Bitmap.class.getClassLoader());
        if (in.readByte() == 0) {
            videoId = null;
        } else {
            videoId = in.readLong();
        }
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<VideoBean> CREATOR = new Creator<VideoBean>() {
        @Override
        public VideoBean createFromParcel(Parcel in) {
            return new VideoBean(in);
        }

        @Override
        public VideoBean[] newArray(int size) {
            return new VideoBean[size];
        }
    };

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Bitmap getVideoThumb() {
        return videoThumb;
    }

    public void setVideoThumb(Bitmap videoThumb) {
        this.videoThumb = videoThumb;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getvPath() {
        return vPath;
    }

    public void setvPath(String vPath) {
        this.vPath = vPath;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getvAlbum() {
        return vAlbum;
    }

    public void setvAlbum(String vAlbum) {
        this.vAlbum = vAlbum;
    }

    public String getvArtist() {
        return vArtist;
    }

    public void setvArtist(String vArtist) {
        this.vArtist = vArtist;
    }

    public String getvDuration() {
        return vDuration;
    }

    public void setvDuration(String vDuration) {
        this.vDuration = vDuration;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "vName='" + vName + '\'' +
                ", videoId=" + videoId +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vPath);
        dest.writeString(vName);
        dest.writeString(vAlbum);
        dest.writeString(vArtist);
        dest.writeString(vDuration);
        dest.writeParcelable(videoThumb, flags);
        if (videoId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(videoId);
        }
        dest.writeInt(width);
        dest.writeInt(height);
    }
}
