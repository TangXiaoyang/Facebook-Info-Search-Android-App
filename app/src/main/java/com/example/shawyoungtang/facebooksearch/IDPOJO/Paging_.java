package com.example.shawyoungtang.facebooksearch.IDPOJO;


import android.os.Parcel;
import android.os.Parcelable;

public class Paging_ implements Parcelable {

    private Cursors_ cursors;
    private String next;

    public Cursors_ getCursors() {
        return cursors;
    }

    public void setCursors(Cursors_ cursors) {
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

    public Paging_() {
    }

    protected Paging_(Parcel in) {
        this.cursors = in.readParcelable(Cursors_.class.getClassLoader());
        this.next = in.readString();
    }

    public static final Parcelable.Creator<Paging_> CREATOR = new Parcelable.Creator<Paging_>() {
        @Override
        public Paging_ createFromParcel(Parcel source) {
            return new Paging_(source);
        }

        @Override
        public Paging_[] newArray(int size) {
            return new Paging_[size];
        }
    };
}