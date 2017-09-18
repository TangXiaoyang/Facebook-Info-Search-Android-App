package com.example.shawyoungtang.facebooksearch.IDPOJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Posts implements Parcelable {

    private List<Datum__> data = null;
    private Paging__ paging;

    public List<Datum__> getData() {
        return data;
    }

    public void setData(List<Datum__> data) {
        this.data = data;
    }

    public Paging__ getPaging() {
        return paging;
    }

    public void setPaging(Paging__ paging) {
        this.paging = paging;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.data);
        dest.writeParcelable(this.paging, flags);
    }

    public Posts() {
    }

    protected Posts(Parcel in) {
        this.data = in.createTypedArrayList(Datum__.CREATOR);
        this.paging = in.readParcelable(Paging__.class.getClassLoader());
    }

    public static final Parcelable.Creator<Posts> CREATOR = new Parcelable.Creator<Posts>() {
        @Override
        public Posts createFromParcel(Parcel source) {
            return new Posts(source);
        }

        @Override
        public Posts[] newArray(int size) {
            return new Posts[size];
        }
    };
}