package com.example.shawyoungtang.facebooksearch.IDPOJO;


import android.os.Parcel;
import android.os.Parcelable;

public class Cursors implements Parcelable {

    private String before;
    private String after;

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.before);
        dest.writeString(this.after);
    }

    public Cursors() {
    }

    protected Cursors(Parcel in) {
        this.before = in.readString();
        this.after = in.readString();
    }

    public static final Parcelable.Creator<Cursors> CREATOR = new Parcelable.Creator<Cursors>() {
        @Override
        public Cursors createFromParcel(Parcel source) {
            return new Cursors(source);
        }

        @Override
        public Cursors[] newArray(int size) {
            return new Cursors[size];
        }
    };
}