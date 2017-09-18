package com.example.shawyoungtang.facebooksearch.EventPOJO;


import android.os.Parcel;
import android.os.Parcelable;

public class Picture implements Parcelable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
    }

    public Picture() {
    }

    protected Picture(Parcel in) {
        this.data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Parcelable.Creator<Picture> CREATOR = new Parcelable.Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel source) {
            return new Picture(source);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };
}