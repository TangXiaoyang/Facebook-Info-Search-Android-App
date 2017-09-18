package com.example.shawyoungtang.facebooksearch.GroupPOJO;


import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {

    private int height;
    private boolean isSilhouette;
    private String url;
    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isIsSilhouette() {
        return isSilhouette;
    }

    public void setIsSilhouette(boolean isSilhouette) {
        this.isSilhouette = isSilhouette;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.height);
        dest.writeByte(this.isSilhouette ? (byte) 1 : (byte) 0);
        dest.writeString(this.url);
        dest.writeInt(this.width);
    }

    public Data() {
    }

    protected Data(Parcel in) {
        this.height = in.readInt();
        this.isSilhouette = in.readByte() != 0;
        this.url = in.readString();
        this.width = in.readInt();
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}