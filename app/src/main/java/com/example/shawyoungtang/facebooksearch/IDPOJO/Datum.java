package com.example.shawyoungtang.facebooksearch.IDPOJO;


import android.os.Parcel;
import android.os.Parcelable;

public class Datum implements Parcelable {

    private String name;
    private Photos photos;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.photos, flags);
        dest.writeString(this.id);
    }

    public Datum() {
    }

    protected Datum(Parcel in) {
        this.name = in.readString();
        this.photos = in.readParcelable(Photos.class.getClassLoader());
        this.id = in.readString();
    }

    public static final Parcelable.Creator<Datum> CREATOR = new Parcelable.Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel source) {
            return new Datum(source);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };
}