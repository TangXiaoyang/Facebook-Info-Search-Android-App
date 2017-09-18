package com.example.shawyoungtang.facebooksearch.IDPOJO;


import android.os.Parcel;
import android.os.Parcelable;

public class Cursors_ implements Parcelable {

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

    public Cursors_() {
    }

    protected Cursors_(Parcel in) {
        this.before = in.readString();
        this.after = in.readString();
    }

    public static final Parcelable.Creator<Cursors_> CREATOR = new Parcelable.Creator<Cursors_>() {
        @Override
        public Cursors_ createFromParcel(Parcel source) {
            return new Cursors_(source);
        }

        @Override
        public Cursors_[] newArray(int size) {
            return new Cursors_[size];
        }
    };
}