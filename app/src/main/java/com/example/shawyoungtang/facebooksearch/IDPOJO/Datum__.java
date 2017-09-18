package com.example.shawyoungtang.facebooksearch.IDPOJO;


import android.os.Parcel;
import android.os.Parcelable;

public class Datum__ implements Parcelable {

    private String created_time;
    private String id;
    private String message;

    public String getCreatedTime() {
        return created_time;
    }

    public void setCreatedTime(String created_time) {
        this.created_time = created_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Datum__() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.created_time);
        dest.writeString(this.id);
        dest.writeString(this.message);
    }

    protected Datum__(Parcel in) {
        this.created_time = in.readString();
        this.id = in.readString();
        this.message = in.readString();
    }

    public static final Creator<Datum__> CREATOR = new Creator<Datum__>() {
        @Override
        public Datum__ createFromParcel(Parcel source) {
            return new Datum__(source);
        }

        @Override
        public Datum__[] newArray(int size) {
            return new Datum__[size];
        }
    };
}