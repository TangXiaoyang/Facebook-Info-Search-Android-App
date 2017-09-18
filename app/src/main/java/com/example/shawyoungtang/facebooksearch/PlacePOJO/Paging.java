package com.example.shawyoungtang.facebooksearch.PlacePOJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.shawyoungtang.facebooksearch.PlacePOJO.Cursors;

public class Paging implements Parcelable {

    private Cursors cursors;
    private String next;
    private String previous;

    public Cursors getCursors() {
        return cursors;
    }

    public void setCursors(Cursors cursors) {
        this.cursors = cursors;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public Paging() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.cursors, flags);
        dest.writeString(this.next);
        dest.writeString(this.previous);
    }

    protected Paging(Parcel in) {
        this.cursors = in.readParcelable(Cursors.class.getClassLoader());
        this.next = in.readString();
        this.previous = in.readString();
    }

    public static final Creator<Paging> CREATOR = new Creator<Paging>() {
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