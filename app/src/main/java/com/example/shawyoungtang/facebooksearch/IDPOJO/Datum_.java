package com.example.shawyoungtang.facebooksearch.IDPOJO;


import android.os.Parcel;
import android.os.Parcelable;

public class Datum_ implements Parcelable {

    private String name;
    private String picture;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
        dest.writeString(this.picture);
        dest.writeString(this.id);
    }

    public Datum_() {
    }

    protected Datum_(Parcel in) {
        this.name = in.readString();
        this.picture = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<Datum_> CREATOR = new Parcelable.Creator<Datum_>() {
        @Override
        public Datum_ createFromParcel(Parcel source) {
            return new Datum_(source);
        }

        @Override
        public Datum_[] newArray(int size) {
            return new Datum_[size];
        }
    };
}