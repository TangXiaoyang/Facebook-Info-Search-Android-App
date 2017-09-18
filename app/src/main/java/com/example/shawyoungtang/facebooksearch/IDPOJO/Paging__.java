package com.example.shawyoungtang.facebooksearch.IDPOJO;


import android.os.Parcel;
import android.os.Parcelable;

public class Paging__ implements Parcelable {

    private String previous;
    private String next;

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Paging__() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.previous);
        dest.writeString(this.next);
    }

    protected Paging__(Parcel in) {
        this.previous = in.readString();
        this.next = in.readString();
    }

    public static final Creator<Paging__> CREATOR = new Creator<Paging__>() {
        @Override
        public Paging__ createFromParcel(Parcel source) {
            return new Paging__(source);
        }

        @Override
        public Paging__[] newArray(int size) {
            return new Paging__[size];
        }
    };
}