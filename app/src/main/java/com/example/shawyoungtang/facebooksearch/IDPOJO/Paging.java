package com.example.shawyoungtang.facebooksearch.IDPOJO;


import android.os.Parcel;
import android.os.Parcelable;

public class Paging implements Parcelable {

    private Cursors cursors;
    private String next;

    public Cursors getCursors() {
        return cursors;
    }

    public void setCursors(Cursors cursors) {
        this.cursors = cursors;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.cursors, flags);
        dest.writeString(this.next);
    }

    public Paging() {
    }

    protected Paging(Parcel in) {
        this.cursors = in.readParcelable(Cursors.class.getClassLoader());
        this.next = in.readString();
    }

    public static final Parcelable.Creator<Paging> CREATOR = new Parcelable.Creator<Paging>() {
        @Override
        public Paging createFromParcel(Parcel source) {
            return new Paging(source);
        }

        @Override
        public Paging[] newArray(int size) {
            return new Paging[size];
        }
    };
}